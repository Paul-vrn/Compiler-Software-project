package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.LabelFactory;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.Register;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        getLeftOperand().codeGenExpr(compiler, n);
        if (n < Register.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new MUL(Register.getR(n+1), Register.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(Register.getR(n)));
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
            compiler.addInstruction(new POP(Register.getR(n)));
            compiler.addInstruction(new MUL(Register.R0, Register.getR(n)));
        }
        if (this.getType().isFloat())
            compiler.getLabelFactory().createTestOverflow(compiler);
    }

    @Override
    protected String getOperatorName() {
        return "*";
    }

}
