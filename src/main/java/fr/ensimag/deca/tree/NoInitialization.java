package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.*;
import fr.ensimag.pseudocode.ima.instructions.LOAD;


import java.io.PrintStream;

/**
 * Absence of initialization (e.g. "int x;" as opposed to "int x =
 * 42;").
 *
 * @author gl21
 * @date 01/01/2023
 */
public class  NoInitialization extends AbstractInitialization {

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
    }

    @Override
    protected void codeGenInit(DecacCompiler compiler, AbstractIdentifier varName) {
            // nothing to do
    }
    @Override
    protected void armCodeGenInit(DecacCompiler compiler, AbstractIdentifier varName) {
        // nothing to do
    }
    @Override
    public void codeGenInitField(DecacCompiler compiler, Type type, int n){
        if (type.isInt()) {
            compiler.addInstruction(new LOAD(0, RegisterIMA.getR(n)));
        } else if (type.isFloat()) {
            compiler.addInstruction(new LOAD(new ImmediateFloat(0.0f), RegisterIMA.getR(n)));
        } else if (type.isBoolean()) {
            compiler.addInstruction(new LOAD(0, RegisterIMA.getR(n)));
        } else {
            compiler.addInstruction(new LOAD(new NullOperand(), RegisterIMA.getR(n)));
        }
    }


    /**
     * Node contains no real information, nothing to check.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // nothing
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

}
