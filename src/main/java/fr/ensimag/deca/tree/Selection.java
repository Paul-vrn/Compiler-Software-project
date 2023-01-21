package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;

public class Selection extends AbstractLValue{

    public AbstractExpr expr;
    AbstractIdentifier fieldIdentifier;

    public Selection(AbstractExpr expr, AbstractIdentifier fieldIdentifier){
        this.expr = expr;
        this.fieldIdentifier = fieldIdentifier;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp envExp, ClassDefinition classdef) throws ContextualError {
        Type type1 = this.expr.verifyExpr(compiler, envExp, classdef);

        if(!type1.isClass()){
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Identifier not a class instance", this.getLocation());
        }

        Definition fieldDefBefore = this.fieldIdentifier.verifyDefinition(compiler, ((ClassDefinition) compiler.environmentType.getEnvTypes().get(type1.getName())).getMembers());
        FieldDefinition fieldDef;
        if(!fieldDefBefore.isField()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Must be a field", this.getLocation());
        }else{
            fieldDef = (FieldDefinition) fieldDefBefore;
        }

        this.setType(fieldDef.getType());

        if(fieldDef.getVisibility() == Visibility.PUBLIC){
            return this.getType();
        }else if(fieldDef.getVisibility() == Visibility.PROTECTED){
            if(classdef == null){
                throw new ContextualError(compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Protected field cannot be accessed", this.getLocation());
            }
            if(!((type1.asClassType("Hopefully a class type", this.getLocation()).isSubClassOf(classdef.getType()))
                    && (classdef.getType().asClassType("Hopefully a class type", this.getLocation()).isSubClassOf(fieldDef.getContainingClass().getType())))){
                throw new ContextualError(compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Subtype problem", this.getLocation());
            }
        }

        return this.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.expr.prettyPrint(s, prefix, false);
        this.fieldIdentifier.prettyPrint(s, prefix, true);

    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }



    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        expr.codeGenExpr(compiler, n);
        if (expr.getType().isClass()) {
            compiler.addInstruction(new LOAD(new RegisterOffset(fieldIdentifier.getFieldDefinition().getIndex(), Register.getR(n)), Register.getR(n)));
        } else {
            compiler.addInstruction(new LOAD(Register.getR(n), Register.getR(n)));
        }
    }

    @Override
    public void codeGenStore(DecacCompiler compiler, int n) {
        if (n < Register.RMAX) {
            compiler.getMemory().setLastGRegister(n+1);
            expr.codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new STORE(Register.getR(n), new RegisterOffset(fieldIdentifier.getFieldDefinition().getIndex(),Register.getR(n + 1))));
        } else {
            compiler.addInstruction(new PUSH(Register.getR(n)));
            compiler.getMemory().setLastGRegister(n);
            expr.codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
            compiler.addInstruction(new POP(Register.getR(n)));
            compiler.addInstruction(new STORE(Register.getR(n), new RegisterOffset(fieldIdentifier.getFieldDefinition().getIndex(),Register.getR(0))));
        }
    }
}
