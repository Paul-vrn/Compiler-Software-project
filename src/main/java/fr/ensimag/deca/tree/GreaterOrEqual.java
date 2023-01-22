package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.B;
import fr.ensimag.pseudocode.arm.instructions.MOV;
import fr.ensimag.pseudocode.ima.instructions.BGE;
import fr.ensimag.pseudocode.ima.instructions.SGE;

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

    @Override
    protected void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        super.armCodeGenExpr(compiler, n, m);
        int nbLabel = compiler.getLabelFactory().NbOpComp();
        Label lessThan = new Label("lessThan_" + nbLabel);
        Label end = new Label("end_" + nbLabel);
        compiler.addInstruction(new BGE(lessThan));
        compiler.addInstruction(new MOV(new ImmediateInteger(0), RegisterARM.getR(n)));
        compiler.addInstruction(new B(end));
        compiler.addLabel(lessThan);
        compiler.addInstruction(new MOV(new ImmediateInteger(1), RegisterARM.getR(n)));
        compiler.addLabel(end);
    }


}
