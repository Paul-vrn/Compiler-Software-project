package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.instructions.SGE;

/**
 * Operator "x >= y"
 * 
 * @author gl21
 * @date 01/01/2023
 */
public class GreaterOrEqual extends AbstractOpIneq {

    public GreaterOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return ">=";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        super.codeGenExpr(compiler, n);
        compiler.addInstruction(new SGE(RegisterIMA.getR(n)));
    }
}
