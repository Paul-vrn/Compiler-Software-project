package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.LabelFactory;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }


    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        Label labelEnd = new Label("OR_" + compiler.nbOr());
        getLeftOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new CMP(1, Register.getR(n)));
        // Si expr 1 est vrai on va direct à la fin
        compiler.addInstruction(new BEQ(labelEnd));
        getRightOperand().codeGenExpr(compiler, n);
        compiler.addLabel(labelEnd);
    }
}
