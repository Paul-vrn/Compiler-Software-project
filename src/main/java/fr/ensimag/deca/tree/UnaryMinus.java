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
            throw new ContextualError("Operator UnaryMinus type mismatch", this.getLocation());
        }
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) { // TODO : marche pas
        getOperand().codeGenExpr(compiler, 1);
        compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.getR(2)));
        compiler.addInstruction(new SUB(Register.getR(2), Register.getR(1)));
        compiler.addInstruction(new WINT());
    }
}
