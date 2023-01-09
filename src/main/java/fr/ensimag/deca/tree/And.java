package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.LabelIdentification;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        Label labelEnd = new Label("AND_END_" + LabelIdentification.nbAnd);
        LabelIdentification.nbAnd++;
        getLeftOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new CMP(0, Register.getR(n)));
        compiler.addInstruction(new BEQ(labelEnd)); // si expr1 est faux on va à la fin
        getRightOperand().codeGenExpr(compiler, n);
        compiler.addLabel(labelEnd);
    }
}
