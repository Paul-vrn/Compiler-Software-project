package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
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
        if (n < Register.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new ADD(Register.getR(n+1), Register.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(Register.getR(n)));
            compiler.getMemory().increaseTSTO();
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
            compiler.addInstruction(new POP(Register.getR(n)));
            compiler.getMemory().decreaseTSTO();
            compiler.addInstruction(new ADD(Register.R0, Register.getR(n)));
        }
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }
}
