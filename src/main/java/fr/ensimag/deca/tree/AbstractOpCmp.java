package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.MOV;
import fr.ensimag.pseudocode.arm.instructions.POPARM;
import fr.ensimag.pseudocode.arm.instructions.PUSHARM;
import fr.ensimag.pseudocode.ima.instructions.CMP;
import fr.ensimag.pseudocode.ima.instructions.LOAD;
import fr.ensimag.pseudocode.ima.instructions.POP;
import fr.ensimag.pseudocode.ima.instructions.PUSH;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //TODO : class objects.

        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        this.setType(compiler.environmentType.BOOLEAN);
        if(this.getOperatorName().equals("==") || this.getOperatorName().equals("!=")) {
            if (!(((type1 == null || type1.isClass()) && (type2 == null || type2.isClass()))
                || ((type1.isInt() || type1.isFloat()) && (type2.isInt() || type2.isFloat()))
                || (type1.isBoolean() && type2.isBoolean()))) {
                throw new ContextualError(compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Compare operation type mismatch", this.getLocation());
            }
        }else {
            if (!((type1.isInt() || type1.isFloat()) && (type2.isInt() || type2.isFloat()))) {
                throw new ContextualError(compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Compare operation type mismatch", this.getLocation());
            }
        }

        return this.getType();
    }

    /**
     * Generates assembly code to evaluate the expression and leave the result in the register n
     * @param compiler compiler where the code will be generated
     * @param n number of the register where the result is stored
     */
    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        getLeftOperand().codeGenExpr(compiler, n);
        if (n < RegisterIMA.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new CMP(RegisterIMA.getR(n+1), RegisterIMA.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
            compiler.getMemory().increaseTSTO();
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
            compiler.addInstruction(new POP(RegisterIMA.getR(n)));
            compiler.getMemory().decreaseTSTO();
            compiler.addInstruction(new CMP(RegisterIMA.getR(n), RegisterIMA.R0));
        }
    }

    /**
     * Generates assembly code to evaluate the expression and leave the result in the register n
     * @param compiler compiler where the code will be generated
     * @param n number of the register R (for integer)
     * @param m number of the register S (for float)
     */
    @Override
    protected void armCodeGenExpr(DecacCompiler compiler, int n, int m){
        getLeftOperand().armCodeGenExpr(compiler, n, m);
        if (n < RegisterARM.RMAX){
            getRightOperand().armCodeGenExpr(compiler, n+1, m);
            compiler.addInstruction(new CMP(RegisterARM.getR(n+1), RegisterARM.getR(n)));
        } else {
            compiler.addInstruction(new PUSHARM(RegisterARM.getR(n)));
            getRightOperand().armCodeGenExpr(compiler, n, m);
            compiler.addInstruction(new MOV(RegisterARM.getR(n), RegisterARM.getR(12)));
            compiler.addInstruction(new POPARM(RegisterARM.getR(n)));
            compiler.addInstruction(new CMP(RegisterARM.getR(12), RegisterARM.getR(n)));
        }
    }
}
