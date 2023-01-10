package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
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
        if (n < Register.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new SUB(Register.getR(n+1), Register.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(Register.getR(n)));
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
            compiler.addInstruction(new POP(Register.getR(n)));
            compiler.addInstruction(new SUB(Register.R0, Register.getR(n)));
        }
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }
    
}
