package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.LabelFactory;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(getOperand().verifyExpr(compiler, localEnv, currentClass));
        if(!this.getType().isBoolean()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
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
        Label label = new Label("NOT_" + LabelFactory.nbLabelNot);
        Label labelEnd = new Label("NOT_END_" + LabelFactory.nbLabelNot);
        LabelFactory.nbLabelNot++;
        getOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new CMP(0, Register.getR(n)));
        compiler.addInstruction(new BNE(label));
        compiler.addInstruction(new LOAD(1, Register.getR(n)));
        compiler.addInstruction(new BRA(labelEnd));
        compiler.addLabel(label);
        compiler.addInstruction(new LOAD(0, Register.getR(n)));
        compiler.addLabel(labelEnd);
    }
}
