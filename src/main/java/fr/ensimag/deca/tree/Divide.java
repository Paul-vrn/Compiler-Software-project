package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.*;
import fr.ensimag.pseudocode.ima.instructions.*;


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
            if (getRightOperand() instanceof IntLiteral && ((IntLiteral) getRightOperand()).getValue() != 0) {

            } else {
                getRightOperand().codeGenExpr(compiler, n + 1);
                regRight = RegisterIMA.getR(n + 1);
            }
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
            if (regRight == null){
                int value = ((IntLiteral) getRightOperand()).getValue();
                if (value%2==0){
                    for (int i = 0; i < Math.log(value)/Math.log(2); i++){
                        compiler.addInstruction(new SHR(RegisterIMA.getR(n)));
                    }
                } else {
                    compiler.addInstruction(new QUO(new ImmediateInteger(value), RegisterIMA.getR(n)));
                }
            } else {
                compiler.getLabelFactory().createTestDiv0(compiler, regRight, true);
                compiler.addInstruction(new QUO(regRight, RegisterIMA.getR(n)));
            }
        }
    }


    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        if (getType().isFloat()) {
            if (m < RegisterARM.SMAX) {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                getRightOperand().armCodeGenExpr(compiler, n+1, m+1);
                compiler.addInstruction(new VDIV(RegisterARM.getS(m+1), RegisterARM.getS(m), RegisterARM.getS(m)));
            } else {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new VPUSH(RegisterARM.getS(m)));
                getRightOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new LDR(RegisterARM.getS(m), RegisterARM.getS(0)));
                compiler.addInstruction(new VPOP(RegisterARM.getS(m)));
                compiler.addInstruction(new VDIV(RegisterARM.getS(0), RegisterARM.getS(m), RegisterARM.getS(m)));
            }
        } else {
            if (n < RegisterARM.RMAX) {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                getRightOperand().armCodeGenExpr(compiler, n+1, m+1);
                compiler.addInstruction(new SDIV(RegisterARM.getR(n+1), RegisterARM.getR(n)));
            } else {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new PUSHARM(RegisterARM.getR(n)));
                getRightOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new LDR(RegisterARM.getR(n), RegisterARM.getR(12)));
                compiler.addInstruction(new POPARM(RegisterARM.getR(n)));
                compiler.addInstruction(new SDIV(RegisterARM.getR(12), RegisterARM.getR(n)));
            }
        }
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }
}
