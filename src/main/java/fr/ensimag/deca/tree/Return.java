package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class Return extends AbstractInst{

    private final AbstractExpr rvalue;

    public Return(AbstractExpr rvalue) {
        Validate.notNull(rvalue);
        this.rvalue = rvalue;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        if(returnType.isVoid()){
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Void return type is forbidden", this.getLocation());
        }

        this.rvalue.verifyRValue(compiler, localEnv, currentClass, returnType);
    }

    @Override
    public void decompile(IndentPrintStream s) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {

    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        this.rvalue.codeGenExpr(compiler, 2);
        compiler.addInstruction(new LOAD(Register.getR(2), Register.getR(0)));
        compiler.addInstruction(new BRA(new Label("fin." + compiler.getLabelFactory().getSuffixCurrentMethod())));
    }
}
