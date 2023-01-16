package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.RegisterIMA;
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
    protected String getOperatorName() {
        return "%";
    }

}
