package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.ima.instructions.BEQ;
import fr.ensimag.pseudocode.ima.instructions.CMP;

/**
 * And Operator.
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
        Label labelEnd = new Label("AND_END_" + compiler.nbAnd());
        getLeftOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new CMP(0, RegisterIMA.getR(n)));
        compiler.addInstruction(new BEQ(labelEnd)); // si expr1 est faux on va à la fin
        getRightOperand().codeGenExpr(compiler, n);
        compiler.addLabel(labelEnd);
    }

    @Override
    protected void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        Label labelEnd = new Label("AND_END_" + compiler.nbAnd());
        getLeftOperand().armCodeGenExpr(compiler, n, m);
        compiler.addInstruction(new CMP(0, RegisterARM.getR(n)));
        compiler.addInstruction(new BEQ(labelEnd)); // si expr1 est faux on va à la fin
        getRightOperand().armCodeGenExpr(compiler, n, m);
        compiler.addLabel(labelEnd);
    }

}
