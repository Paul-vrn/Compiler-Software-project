package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.instructions.SLE;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class LowerOrEqual extends AbstractOpIneq {
    public LowerOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<=";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        super.codeGenExpr(compiler, n);
        compiler.addInstruction(new SLE(RegisterIMA.getR(n)));
    }

}
