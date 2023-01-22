package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.ImmediateFloat;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.ima.instructions.SUB;

/**
 * Arithmetic binary operations (+, -, /, ...)
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if (type1 == null || type2 == null) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Arithmetic operation impossible with undefined operand", this.getLocation());
        }

        if (type1.isInt() && type2.isInt()) {
            this.setType(compiler.environmentType.INT);
        } else if ((type1.isInt() || type1.isFloat()) && (type2.isInt() || type2.isFloat())) {
            this.setType(compiler.environmentType.FLOAT);
            if (type1.isInt() && type2.isFloat()) {
                this.setLeftOperand(new ConvFloat(this.getLeftOperand()));
                this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
            } else if (type1.isFloat() && type2.isInt()) {
                this.setRightOperand(new ConvFloat(this.getRightOperand()));
                this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
            }
        } else {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Arithmetic operation type mismatch", this.getLocation());
        }

        return this.getType();
    }

    public boolean checkBothLiteral() {
        return (getLeftOperand() instanceof IntLiteral && getRightOperand() instanceof IntLiteral)
                || (getLeftOperand() instanceof FloatLiteral && getRightOperand() instanceof FloatLiteral);
    }
    public DVal oneLiteral(DecacCompiler compiler, int n) {
         if (getRightOperand() instanceof IntLiteral) {
            getLeftOperand().codeGenExpr(compiler, n);
            return new ImmediateInteger(((IntLiteral) getRightOperand()).getValue());
        } else if (getRightOperand() instanceof FloatLiteral) {
            getLeftOperand().codeGenExpr(compiler, n);
            return new ImmediateFloat(((FloatLiteral) getRightOperand()).getValue());
        } else if (getLeftOperand() instanceof IntLiteral) {
            getRightOperand().codeGenExpr(compiler, n);
            return new ImmediateInteger(((IntLiteral) getLeftOperand()).getValue());
        } else if (getLeftOperand() instanceof FloatLiteral) {
            getRightOperand().codeGenExpr(compiler, n);
            return new ImmediateFloat(((FloatLiteral) getLeftOperand()).getValue());
        } else {
             return null;
         }
    }


}
