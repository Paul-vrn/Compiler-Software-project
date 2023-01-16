package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.arm.instructions.BL;
import fr.ensimag.ima.pseudocode.arm.instructions.LDR;
import fr.ensimag.ima.pseudocode.arm.instructions.MOV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 * Integer literal
 *
 * @author gl21
 * @date 01/01/2023
 */
public class IntLiteral extends AbstractExpr {
    public int getValue() {
        return value;
    }

    private int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(new IntType(compiler.createSymbol("int")));
        return this.getType();
    }



    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.addInstruction(new LOAD(new ImmediateInteger(value), RegisterIMA.getR(n)));
    }

    @Override public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        compiler.addInstruction(new MOV(new ImmediateInteger(value), RegisterARM.getR(n)));
    }

    @Override
    public void armCodeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getlabelInt()), RegisterARM.getR(0)));
        compiler.addInstruction(new MOV(new ImmediateInteger(value), RegisterARM.getR(1)));
        compiler.addInstruction(new BL(compiler.getLabelFactory().getPrintfLabel()));
    }

    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(getValue()));
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
