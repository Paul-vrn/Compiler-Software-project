package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

import fr.ensimag.pseudocode.*;
import fr.ensimag.pseudocode.arm.instructions.*;
import fr.ensimag.pseudocode.ima.instructions.LOAD;
import fr.ensimag.pseudocode.ima.instructions.WFLOAT;
import fr.ensimag.pseudocode.ima.instructions.WFLOATX;
import org.apache.commons.lang.Validate;

/**
 * Single precision, floating-point literal
 *
 * @author gl21
 * @date 01/01/2023
 */
public class FloatLiteral extends AbstractExpr {

    public float getValue() {
        return value;
    }

    private float value;

    public FloatLiteral(float value) {
        Validate.isTrue(!Float.isInfinite(value),
                "literal values cannot be infinite");
        Validate.isTrue(!Float.isNaN(value),
                "literal values cannot be NaN");
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        this.setType(new FloatType(compiler.createSymbol("float")));
        return this.getType();
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new LOAD(new ImmediateFloat(value), RegisterIMA.R1));
        compiler.addInstruction(printHex ? new WFLOATX() : new WFLOAT());
    }

    @Override
    protected void armCodeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getLabelFloat()), RegisterARM.getR(0)));
        this.armCodeGenExpr(compiler, 4, 2);
        compiler.addInstruction(new VCVTDS(RegisterARM.getS(2), RegisterARM.getD(0)));
        compiler.addInstruction(new VMOV(RegisterARM.getD(0), RegisterARM.getR(3), RegisterARM.getR(2)));
        compiler.addInstruction(new BL(compiler.getLabelFactory().getPrintfLabel()));
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.addInstruction(new LOAD(new ImmediateFloat(value), RegisterIMA.getR(n)));
        compiler.getMemory().setLastGRegister(n);
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        Label l = new Label("float_" + compiler.getLabelFactory().nbFloat());
        // float_X: .float value
        compiler.addFirst(new Line(l, new FLOAT(new ImmediateFloat(value))));
        // vldr sM, float_X
        compiler.addInstruction(new VLDR(l, RegisterARM.getS(m)));
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(java.lang.Float.toHexString(value));
    }

    @Override
    String prettyPrintNode() {
        return "Float (" + getValue() + ")";
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
