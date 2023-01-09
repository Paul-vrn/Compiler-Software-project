package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

import fr.ensimag.deca.DecacCompiler;
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
        if (n < Register.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            if(getLeftOperand().getType().isFloat() || getRightOperand().getType().isFloat()){
                compiler.addInstruction(new DIV(Register.getR(n+1), Register.getR(n)));
            }
            else{
                compiler.addInstruction(new QUO(Register.getR(n+1), Register.getR(n)));
            }

        } else {
            compiler.addInstruction(new PUSH(Register.getR(n)));
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
            compiler.addInstruction(new POP(Register.getR(n)));
            if(getLeftOperand().getType().isFloat() || getRightOperand().getType().isFloat()){
                compiler.addInstruction(new DIV(Register.R0, Register.getR(n)));
            }
            else{
                compiler.addInstruction(new QUO(Register.R0, Register.getR(n)));
            }
        }
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

}
