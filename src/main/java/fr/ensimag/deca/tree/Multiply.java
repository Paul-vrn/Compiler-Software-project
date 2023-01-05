package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    public void codeGenInst(DecacCompiler compiler) {
//        compiler.addInstruction(new MUL());
    }

    @Override
    protected String getOperatorName() {
        return "*";
    }

}
