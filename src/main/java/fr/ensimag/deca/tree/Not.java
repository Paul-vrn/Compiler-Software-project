package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.LabelIdentification;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

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
        Label label = new Label("NOT_" + LabelIdentification.nbLabelNot);
        LabelIdentification.nbLabelNot++;
        getOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new CMP(0, Register.getR(n)));
        compiler.addInstruction(new BNE(label));
        compiler.addInstruction(new LOAD(1, Register.getR(n)));
        compiler.addLabel(label);
        compiler.addInstruction(new LOAD(0, Register.getR(n)));
    }
}
