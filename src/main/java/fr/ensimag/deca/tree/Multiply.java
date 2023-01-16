package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.RegisterIMA;

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
        if (n < RegisterIMA.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new MUL(RegisterIMA.getR(n+1), RegisterIMA.getR(n)));

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
    protected String getOperatorName() {
        return "*";
    }

}
