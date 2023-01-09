package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(getOperand().verifyExpr(compiler, localEnv, currentClass));
        if(!(this.getType().isFloat() || this.getType().isInt())){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Operator UnaryMinus type mismatch", this.getLocation());

        }
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        if (n < Register.RMAX) {
            getOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new OPP(Register.getR(n+1), Register.getR(n)));

        } else {
            compiler.addInstruction(new PUSH(Register.getR(n)));
            getOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
            compiler.addInstruction(new POP(Register.getR(n)));
            compiler.addInstruction(new OPP(Register.R0, Register.getR(n)));
        }
    }
}
