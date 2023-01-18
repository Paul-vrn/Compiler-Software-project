package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.RegisterARM;
import fr.ensimag.ima.pseudocode.arm.instructions.*;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        getLeftOperand().codeGenExpr(compiler, n);
        if (n < RegisterIMA.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new SUB(RegisterIMA.getR(n+1), RegisterIMA.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
            compiler.getMemory().increaseTSTO();
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
            compiler.addInstruction(new POP(RegisterIMA.getR(n)));
            compiler.getMemory().decreaseTSTO();
            compiler.addInstruction(new SUB(RegisterIMA.R0, RegisterIMA.getR(n)));
        }
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        if (getType().isFloat()) {
            if (m < RegisterARM.SMAX) {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                getRightOperand().armCodeGenExpr(compiler, n+1, m+1);
                compiler.addInstruction(new VSUB(RegisterARM.getS(m+1), RegisterARM.getS(m)));
            } else {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new SUB(new ImmediateInteger(4), RegisterARM.SP));
                compiler.addInstruction(new PUSHARM(RegisterARM.getS(m)));
                getRightOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new LDR(RegisterARM.getS(m), RegisterARM.getS(0)));
                compiler.addInstruction(new POPARM(RegisterARM.getS(m)));
                compiler.addInstruction(new ADD(new ImmediateInteger(4), RegisterARM.SP));
                compiler.addInstruction(new VSUB(RegisterARM.getS(0), RegisterARM.getS(m)));
            }
        } else {
            if (n < RegisterARM.RMAX) {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                getRightOperand().armCodeGenExpr(compiler, n+1, m+1);
                compiler.addInstruction(new SUBS(RegisterARM.getR(n+1), RegisterARM.getR(n)));
            } else {
                getLeftOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new SUB(new ImmediateInteger(4), RegisterARM.SP));
                compiler.addInstruction(new PUSHARM(RegisterARM.getR(n)));
                getRightOperand().armCodeGenExpr(compiler, n, m);
                compiler.addInstruction(new LDR(RegisterARM.getR(n), RegisterARM.getR(12)));
                compiler.addInstruction(new POPARM(RegisterARM.getR(n)));
                compiler.addInstruction(new ADD(new ImmediateInteger(4), RegisterARM.SP));
                compiler.addInstruction(new SUBS(RegisterARM.getR(12), RegisterARM.getR(n)));
            }
        }
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }
    
}
