package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.RegisterARM;
import fr.ensimag.ima.pseudocode.arm.instructions.ADDS;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {

        getLeftOperand().codeGenExpr(compiler, n);
        if (n < RegisterIMA.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new ADD(RegisterIMA.getR(n+1), RegisterIMA.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
            compiler.getMemory().increaseTSTO();
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
            compiler.addInstruction(new POP(RegisterIMA.getR(n)));
            compiler.getMemory().decreaseTSTO();
            compiler.addInstruction(new ADD(RegisterIMA.R0, RegisterIMA.getR(n)));
        }
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        if(m < RegisterARM.RMAX) {
            getLeftOperand().armCodeGenExpr(compiler, m, m+1);
            getRightOperand().armCodeGenExpr(compiler, m+1, m+2);
            compiler.addInstruction(new ADDS(RegisterARM.getR(m), RegisterARM.getR(m+1), RegisterARM.getR(n)));
        } else {
            throw new UnsupportedOperationException("not yet implemented, arm register overflow");
        }
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }
}
