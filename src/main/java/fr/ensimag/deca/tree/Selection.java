package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

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
                    + this.getLocation().errorOutPut() + ": No class instance", this.getLocation());
        }

        FieldDefinition fieldDef = (FieldDefinition) this.fieldIdentifier.verifyDefinition(compiler, ((ClassDefinition) compiler.environmentType.getEnvTypes().get(type1.getName())).getMembers());

        this.setType(fieldDef.getType());

        if(fieldDef.getVisibility() == Visibility.PUBLIC){
            return this.getType();
        }else if(fieldDef.getVisibility() == Visibility.PROTECTED){
            if(!((type1.asClassType("Hopefully a class type", this.getLocation()).isSubClassOf(classdef.getType()))
                    && (type1.asClassType("Hopefully a class type", this.getLocation()).isSubClassOf(fieldDef.getContainingClass().getType())))){
                throw new ContextualError(compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": No class instance", this.getLocation());
            }
        }

        return this.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {

    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }
}
