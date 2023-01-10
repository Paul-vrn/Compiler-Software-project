package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.LabelFactory;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
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
        if (n < Register.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            regRight = Register.getR(n + 1);
        } else {
            compiler.addInstruction(new PUSH(Register.getR(n)));
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
            compiler.addInstruction(new POP(Register.getR(n)));
            regRight = Register.R0;
        }

        if(this.getType().isFloat()){
            compiler.addInstruction(new CMP(new ImmediateFloat(0.0f), regRight));
            compiler.addInstruction(new BEQ(compiler.getLabelFactory().createDivByZeroErrorLabel()));
            compiler.addInstruction(new DIV(regRight, Register.getR(n)));
            compiler.addInstruction(new BOV(compiler.getLabelFactory().createOverflowErrorLabel()));
        }
        else{
            compiler.addInstruction(new CMP(new ImmediateInteger(0), regRight));
            compiler.addInstruction(new BEQ(compiler.getLabelFactory().createDivByZeroErrorLabel()));
            compiler.addInstruction(new QUO(regRight, Register.getR(n)));
        }
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

}
