package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import fr.ensimag.ima.pseudocode.instructions.MUL;
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
        //TODO code redondant avec les autres opérations arithmétiques (à factoriser si possible)
        getLeftOperand().codeGenExpr(compiler, n);
        getRightOperand().codeGenExpr(compiler, n + 1);
        compiler.addInstruction(new MUL(Register.getR(n + 1), Register.getR(n)));
    }

    @Override
    protected String getOperatorName() {
        return "*";
    }

}
