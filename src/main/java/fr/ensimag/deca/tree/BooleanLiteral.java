package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import fr.ensimag.ima.pseudocode.instructions.WSTR;

import java.io.PrintStream;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(new BooleanType(compiler.createSymbol("boolean")));
        if (this.getType().isBoolean()){
            return this.getType();
        }
        throw new ContextualError("Boolean Type problem", this.getLocation());
    }

    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.addInstruction(new LOAD(value ? 1 : 0, Register.getR(n)));
    }


    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        System.out.println("BooleanLiteral");
        if (value) {
            compiler.addInstruction(new WSTR("true"));
        } else {
            compiler.addInstruction(new WSTR("false"));
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

}
