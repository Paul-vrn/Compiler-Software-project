package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.pseudocode.*;
import fr.ensimag.pseudocode.arm.instructions.BL;
import fr.ensimag.pseudocode.arm.instructions.MOV;
import fr.ensimag.pseudocode.arm.instructions.POPARM;
import fr.ensimag.pseudocode.arm.instructions.PUSHARM;
import fr.ensimag.pseudocode.ima.instructions.*;

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
        DVal lit = oneLiteral(compiler, n);
        if (lit != null) {
            compiler.addInstruction(new REM(lit, RegisterIMA.getR(n)));
            return;
        }

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
        if (n < RegisterARM.RMAX) {
            getLeftOperand().armCodeGenExpr(compiler, n, m);
            getRightOperand().armCodeGenExpr(compiler, n+1, m+1);
            compiler.addInstruction(new MOV(RegisterARM.getR(n), RegisterARM.getR(0)));
            compiler.addInstruction(new MOV(RegisterARM.getR(n+1), RegisterARM.getR(1)));
            compiler.addInstruction(new BL(new Label("__aeabi_idivmod", true)));
            compiler.addInstruction(new MOV(RegisterARM.getR(1), RegisterARM.getR(n)));
        } else {
            getLeftOperand().armCodeGenExpr(compiler, n, m);
            compiler.addInstruction(new MOV(RegisterARM.getR(n), RegisterARM.getR(0)));
            compiler.addInstruction(new PUSHARM(RegisterARM.getR(0)));
            getRightOperand().armCodeGenExpr(compiler, n, m);
            compiler.addInstruction(new POPARM(RegisterARM.getR(0)));
            compiler.addInstruction(new MOV(RegisterARM.getR(n), RegisterARM.getR(1)));
            compiler.addInstruction(new BL(new Label("__aeabi_idivmod", true)));
            compiler.addInstruction(new MOV(RegisterARM.getR(1), RegisterARM.getR(n)));
        }
    }

    @Override
    protected String getOperatorName() {
        return "%";
    }

}
