package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.*;
import fr.ensimag.pseudocode.arm.instructions.*;
import fr.ensimag.pseudocode.ima.instructions.LOAD;
import fr.ensimag.pseudocode.ima.instructions.RFLOAT;

import java.io.PrintStream;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ReadFloat extends AbstractReadExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(compiler.environmentType.FLOAT);
        return this.getType();
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("readFloat()");
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
    public void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.addInstruction(new RFLOAT());
        compiler.addInstruction(new LOAD(RegisterIMA.R1, RegisterIMA.getR(n)));
        compiler.getLabelFactory().createTestIo(compiler);
        compiler.getMemory().setLastGRegister(n);
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        compiler.addInstruction(new SUBS(new ImmediateInteger(4), RegisterARM.SP));
        compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getLabelFloat()), RegisterARM.getR(0))); // LDR R0, =float
        compiler.addInstruction(new MOV(RegisterARM.SP, RegisterARM.getR(1))); // MOV R1, SP
        compiler.addInstruction(new BL(compiler.getLabelFactory().getScanfLabel())); // bl scanf
        compiler.addInstruction(new VLDR(new RegisterOffset(0, RegisterARM.SP), RegisterARM.getS(m)));// ldr sn, [sp]
        compiler.addInstruction(new ADDS(new ImmediateInteger(4),RegisterARM.SP));

    }
}
