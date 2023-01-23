package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.ima.instructions.*;

import java.io.PrintStream;

/**
 * Class for the selection lvalue
 * example: a.x (where x is a field of the class that a is an instance of)
 *
 * @author gl21
 * @date 10/01/2023
 */
public class Selection extends AbstractLValue {

    public AbstractExpr expr;
    AbstractIdentifier fieldIdentifier;

    public Selection(AbstractExpr expr, AbstractIdentifier fieldIdentifier) {
        this.expr = expr;
        this.fieldIdentifier = fieldIdentifier;
    }

    /**
     * VerifyExpr for Selection
     *
     * @param compiler (contains the "env_types" attribute)
     * @param envExp   Environment in which the expression should be checked
     *                 (corresponds to the "env_exp" attribute)
     * @param classdef Definition of the class containing the expression
     *                 (corresponds to the "class" attribute)
     *                 is null in the main bloc.
     * @return
     * @throws ContextualError
     */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp envExp, ClassDefinition classdef) throws ContextualError {
        Type type1 = this.expr.verifyExpr(compiler, envExp, classdef);

        // throws an error if the expression is not an instance of a class
        if (!type1.isClass()) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Identifier not a class instance", this.getLocation());
        }

        Definition fieldDefBefore = this.fieldIdentifier.verifyDefinition(compiler, ((ClassDefinition) compiler.environmentType.getEnvTypes().get(type1.getName())).getMembers());
        FieldDefinition fieldDef;

        // throws an error if the identifier on the right side of the selection is not the field of a class
        if (!fieldDefBefore.isField()) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Must be a field", this.getLocation());
        } else {
            fieldDef = (FieldDefinition) fieldDefBefore;
        }

        this.setType(fieldDef.getType());

        // checks the visibility of the field, if it is protected, check the three conditions of (3.66)
        if (fieldDef.getVisibility() == Visibility.PUBLIC) {
            return this.getType();
        } else if (fieldDef.getVisibility() == Visibility.PROTECTED) {
            if (classdef == null) {
                throw new ContextualError(compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Protected field cannot be accessed", this.getLocation());
            }
            if (!((type1.asClassType("Hopefully a class type", this.getLocation()).isSubClassOf(classdef.getType()))
                    && (classdef.getType().asClassType("Hopefully a class type", this.getLocation()).isSubClassOf(fieldDef.getContainingClass().getType())))) {
                throw new ContextualError(compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Protected access, subtype problem", this.getLocation());
            }
        }

        return this.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        fieldIdentifier.decompile(s);
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
        System.out.println(expr.getType());
        expr.codeGenExpr(compiler, n);
        compiler.getLabelFactory().createTestDeferencementNull(compiler, RegisterIMA.getR(n));
        if (expr.getType().isClass()) {
            compiler.addInstruction(new LOAD(new RegisterOffset(fieldIdentifier.getFieldDefinition().getIndex(), RegisterIMA.getR(n)), RegisterIMA.getR(n)));
        } else {
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.getR(n)));
        }
    }

    @Override
    public void codeGenStore(DecacCompiler compiler, int n) {
        if (n < RegisterIMA.RMAX) {
            compiler.getMemory().setLastGRegister(n + 1);
            expr.codeGenExpr(compiler, n + 1);
            compiler.getLabelFactory().createTestDeferencementNull(compiler, RegisterIMA.getR(n + 1));
            compiler.addInstruction(new STORE(RegisterIMA.getR(n), new RegisterOffset(fieldIdentifier.getFieldDefinition().getIndex(), RegisterIMA.getR(n + 1))));
        } else {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
            compiler.getMemory().setLastGRegister(n);
            expr.codeGenExpr(compiler, n);
            compiler.getLabelFactory().createTestDeferencementNull(compiler, RegisterIMA.getR(n));
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
            compiler.addInstruction(new POP(RegisterIMA.getR(n)));
            compiler.addInstruction(new STORE(RegisterIMA.getR(n), new RegisterOffset(fieldIdentifier.getFieldDefinition().getIndex(), RegisterIMA.getR(0))));
        }
    }
}
