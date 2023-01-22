package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.pseudocode.ImmediateFloat;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.arm.instructions.RSB;
import fr.ensimag.pseudocode.ima.instructions.OPP;

/**
 * Class for the unary minus ( - )
 * exemple: int x = -5;
 *
 * @author gl21
 * @date 01/01/2023
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    /**
     * VerifyExpr for the UnaryMinus
     *
     * @param compiler     (contains the "env_types" attribute)
     * @param localEnv     Environment in which the expression should be checked
     *                     (corresponds to the "env_exp" attribute)
     * @param currentClass Definition of the class containing the expression
     *                     (corresponds to the "class" attribute)
     *                     is null in the main bloc.
     * @return
     * @throws ContextualError
     */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        this.setType(getOperand().verifyExpr(compiler, localEnv, currentClass));
        //Checks the type of the expression that the unary minus is applied on, throws error if it is not float or int
        if (!(this.getType().isFloat() || this.getType().isInt())) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
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
        getOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new OPP(RegisterIMA.getR(n), RegisterIMA.getR(n)));
    }

    protected void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        getOperand().armCodeGenExpr(compiler, n, m);
        if (this.getType().isFloat()) {
            compiler.addInstruction(new RSB(new ImmediateFloat(0), RegisterARM.getS(m)));
        } else {
            compiler.addInstruction(new RSB(new ImmediateInteger(0), RegisterARM.getR(n)));
        }
    }
}
