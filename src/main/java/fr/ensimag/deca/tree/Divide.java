package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.instructions.*;


/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        getLeftOperand().codeGenExpr(compiler, n);
        GPRegister regRight = null;
        if (n < RegisterIMA.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            regRight = RegisterIMA.getR(n + 1);
        } else {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
            compiler.addInstruction(new POP(RegisterIMA.getR(n)));
            regRight = RegisterIMA.R0;
        }

        if(this.getType().isFloat()){
            compiler.getLabelFactory().createTestDiv0(compiler, regRight, false);
            compiler.addInstruction(new DIV(regRight, RegisterIMA.getR(n)));
            compiler.getLabelFactory().createTestOverflow(compiler);
        }
        else{
            compiler.getLabelFactory().createTestDiv0(compiler, regRight, true);
            compiler.addInstruction(new QUO(regRight, RegisterIMA.getR(n)));
        }
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
    public void armCodeGenInst(DecacCompiler compiler) {

    }
}
