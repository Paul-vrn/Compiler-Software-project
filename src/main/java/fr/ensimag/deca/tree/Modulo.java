package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.RegisterARM;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.arm.instructions.*;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if(type1.isInt() && type2.isInt()){
                this.setType(compiler.environmentType.INT);
                return this.getType();
        }else{
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Arithmetic modulo operation type mismatch", this.getLocation());
        }
    }

    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        getLeftOperand().codeGenExpr(compiler, n);
        if (n < RegisterIMA.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new REM(RegisterIMA.getR(n+1), RegisterIMA.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
            compiler.getMemory().increaseTSTO();
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
            compiler.addInstruction(new POP(RegisterIMA.getR(n)));
            compiler.getMemory().decreaseTSTO();
            compiler.addInstruction(new REM(RegisterIMA.R0, RegisterIMA.getR(n)));
        }
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        if (m < RegisterARM.RMAX) {
            getLeftOperand().armCodeGenExpr(compiler, n, m);
            getRightOperand().armCodeGenExpr(compiler, n+1, m+1);
            compiler.addInstruction(new MOV(RegisterARM.getR(n), RegisterARM.getR(0)));
            compiler.addInstruction(new MOV(RegisterARM.getR(n+1), RegisterARM.getR(1)));
            compiler.addInstruction(new BL(new Label("__aeabi_idivmod", true)));
            compiler.addInstruction(new MOV(RegisterARM.getR(1), RegisterARM.getR(n)));
        } else {
            getLeftOperand().armCodeGenExpr(compiler, n, m);
            compiler.addInstruction(new SUB(new ImmediateInteger(4), RegisterARM.SP));
            compiler.addInstruction(new PUSH(RegisterARM.getS(n)));
            getRightOperand().armCodeGenExpr(compiler, n, m);
            compiler.addInstruction(new LDR(RegisterARM.getR(n), RegisterARM.getR(12)));
            compiler.addInstruction(new POP(RegisterARM.getR(n)));
            compiler.addInstruction(new ADD(new ImmediateInteger(4), RegisterARM.SP));
            compiler.addInstruction(new MULS(RegisterARM.getR(12), RegisterARM.getR(n)));
        }
    }

    @Override
    protected String getOperatorName() {
        return "%";
    }

}
