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
import fr.ensimag.pseudocode.ima.instructions.RINT;

import java.io.PrintStream;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ReadInt extends AbstractReadExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(compiler.environmentType.INT);
        return this.getType();
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("readInt()");
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

        compiler.addInstruction(new RINT());
        compiler.addInstruction(new LOAD(RegisterIMA.R1, RegisterIMA.getR(n)));
        compiler.getLabelFactory().createTestIo(compiler);
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        compiler.addInstruction(new SUBS(new ImmediateInteger(4), RegisterARM.SP));
        compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getLabelInt()), RegisterARM.getR(0))); // LDR R0, =int
        compiler.addInstruction(new MOV(RegisterARM.SP, RegisterARM.getR(1))); // MOV R1, SP
        compiler.addInstruction(new BL(compiler.getLabelFactory().getScanfLabel())); // bl scanf
        compiler.addInstruction(new LDR(new RegisterOffset(0, RegisterARM.SP), RegisterARM.getR(n)));// ldr rn, [sp]
        compiler.addInstruction(new ADDS(new ImmediateInteger(4),RegisterARM.SP));

    }
}
