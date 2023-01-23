package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.pseudocode.*;
import fr.ensimag.pseudocode.arm.instructions.*;
import fr.ensimag.pseudocode.ima.instructions.*;

/**
 * Multiply Operator.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        if (getType().isInt()) {
            int val = 0;
            if (getLeftOperand() instanceof IntLiteral && (val=((IntLiteral) getLeftOperand()).getValue())%2 == 0) {
                getRightOperand().codeGenExpr(compiler, n);
                for (int i = 0; i < Math.log(val)/Math.log(2); i++) {
                    compiler.addInstruction(new SHL(RegisterIMA.getR(n)));
                }
                return;
            } else if (getRightOperand() instanceof IntLiteral && (val=((IntLiteral) getRightOperand()).getValue())%2 == 0) {
                getLeftOperand().codeGenExpr(compiler, n);
                for (int i = 0; i < Math.log(val)/Math.log(2); i++) {
                    compiler.addInstruction(new SHL(RegisterIMA.getR(n)));
                }
                return;
            }
        }
        DVal lit = oneLiteral(compiler, n);
        if (lit != null) {
            compiler.addInstruction(new MUL(lit, RegisterIMA.getR(n)));
            return;
        }

        getLeftOperand().codeGenExpr(compiler, n);
        if (n < RegisterIMA.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new MUL(RegisterIMA.getR(n + 1), RegisterIMA.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
            compiler.getMemory().increaseTSTO();
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
            compiler.addInstruction(new POP(RegisterIMA.getR(n)));
            compiler.getMemory().decreaseTSTO();
            compiler.addInstruction(new MUL(RegisterIMA.R0, RegisterIMA.getR(n)));
        }
        if (this.getType().isFloat())
            compiler.getLabelFactory().createTestOverflow(compiler);
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        if (getType().isFloat()) {
            if (m < RegisterARM.SMAX) {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                getRightOperand().armCodeGenExpr(compiler, n + 1, m + 1);
                compiler.addInstruction(new VMUL(RegisterARM.getS(m + 1), RegisterARM.getS(m)));
            } else {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new VPUSH(RegisterARM.getS(m)));
                getRightOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new LDR(RegisterARM.getS(m), RegisterARM.getS(0)));
                compiler.addInstruction(new VPOP(RegisterARM.getS(m)));
                compiler.addInstruction(new VMUL(RegisterARM.getS(0), RegisterARM.getS(m)));
            }
        } else {
            if (n < RegisterARM.RMAX) {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                getRightOperand().armCodeGenExpr(compiler, n + 1, m + 1);
                compiler.addInstruction(new MULS(RegisterARM.getR(n + 1), RegisterARM.getR(n)));
            } else {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new PUSHARM(RegisterARM.getR(n)));
                getRightOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new LDR(RegisterARM.getR(n), RegisterARM.getR(12)));
                compiler.addInstruction(new POPARM(RegisterARM.getR(n)));
                compiler.addInstruction(new MULS(RegisterARM.getR(12), RegisterARM.getR(n)));
            }
        }
    }

    @Override
    protected String getOperatorName() {
        return "*";
    }

}
