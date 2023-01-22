package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.B;
import fr.ensimag.pseudocode.arm.instructions.MOV;
import fr.ensimag.pseudocode.ima.instructions.BNE;
import fr.ensimag.pseudocode.ima.instructions.BRA;
import fr.ensimag.pseudocode.ima.instructions.CMP;
import fr.ensimag.pseudocode.ima.instructions.LOAD;

/**
 * Class for a Not operand
 * example: !true;
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    /**
     * VerifyExpr for the not operand
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
        //Throws an error if the expression is not boolean
        if (!this.getType().isBoolean()) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Operator Not type mismatch", this.getLocation());
        }
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        int nbNot = compiler.nbNot();
        Label label = new Label("NOT_" + nbNot);
        Label labelEnd = new Label("NOT_END_" + nbNot);
        getOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new CMP(0, RegisterIMA.getR(n)));
        compiler.addInstruction(new BNE(label));
        compiler.addInstruction(new LOAD(1, RegisterIMA.getR(n)));
        compiler.addInstruction(new BRA(labelEnd));
        compiler.addLabel(label);
        compiler.addInstruction(new LOAD(0, RegisterIMA.getR(n)));
        compiler.addLabel(labelEnd);
    }

    @Override
    protected void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        int nbNot = compiler.nbNot();
        Label label = new Label("NOT_" + nbNot);
        Label labelEnd = new Label("NOT_END_" + nbNot);
        getOperand().armCodeGenExpr(compiler, n, m);
        compiler.addInstruction(new CMP(0, RegisterIMA.getR(n)));
        compiler.addInstruction(new BNE(label));
        compiler.addInstruction(new MOV(new ImmediateInteger(1), RegisterIMA.getR(n)));
        compiler.addInstruction(new B(labelEnd));
        compiler.addLabel(label);
        compiler.addInstruction(new MOV(new ImmediateInteger(0), RegisterIMA.getR(n)));
        compiler.addLabel(labelEnd);
    }
}
