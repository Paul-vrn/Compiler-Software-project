package fr.ensimag.deca.tree;


/**
 * Class for the exact comparison operations ( == or != ) $
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class AbstractOpExactCmp extends AbstractOpCmp {


    public AbstractOpExactCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


}
