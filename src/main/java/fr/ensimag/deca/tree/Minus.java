package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.arm.instructions.*;
import fr.ensimag.pseudocode.ima.instructions.LOAD;
import fr.ensimag.pseudocode.ima.instructions.POP;
import fr.ensimag.pseudocode.ima.instructions.PUSH;
import fr.ensimag.pseudocode.ima.instructions.SUB;

/**
 * Minus Operator.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        getLeftOperand().codeGenExpr(compiler, n);
        if (n < RegisterIMA.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new SUB(RegisterIMA.getR(n+1), RegisterIMA.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
            compiler.getMemory().increaseTSTO();
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
            compiler.addInstruction(new POP(RegisterIMA.getR(n)));
            compiler.getMemory().decreaseTSTO();
            compiler.addInstruction(new SUB(RegisterIMA.R0, RegisterIMA.getR(n)));
        }
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        if (getType().isFloat()) {
            if (m < RegisterARM.SMAX) {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                getRightOperand().armCodeGenExpr(compiler, n+1, m+1);
                compiler.addInstruction(new VSUB(RegisterARM.getS(m+1), RegisterARM.getS(m)));
            } else {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new VPUSH(RegisterARM.getS(m)));
                getRightOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new MOV(RegisterARM.getS(m), RegisterARM.getS(0)));
                compiler.addInstruction(new VPOP(RegisterARM.getS(m)));
                compiler.addInstruction(new VSUB(RegisterARM.getS(0), RegisterARM.getS(m)));
            }
        } else {
            if (n < RegisterARM.RMAX) {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                getRightOperand().armCodeGenExpr(compiler, n+1, m+1);
                compiler.addInstruction(new SUBS(RegisterARM.getR(n+1), RegisterARM.getR(n)));
            } else {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new PUSHARM(RegisterARM.getR(n)));
                getRightOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new MOV(RegisterARM.getR(n), RegisterARM.getR(12)));
                compiler.addInstruction(new POPARM(RegisterARM.getR(n)));
                compiler.addInstruction(new SUBS(RegisterARM.getR(12), RegisterARM.getR(n)));
            }
        }
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }
    
}
