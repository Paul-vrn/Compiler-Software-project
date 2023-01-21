package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.ima.instructions.BRA;
import fr.ensimag.pseudocode.ima.instructions.LOAD;
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
        this.rvalue.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){
        this.rvalue.codeGenExpr(compiler, 2);
        compiler.addInstruction(new LOAD(RegisterIMA.getR(2), RegisterIMA.getR(0)));
        compiler.addInstruction(new BRA(new Label("fin." + compiler.getLabelFactory().getSuffixCurrentMethod())));
    }

    @Override
    protected void armCodeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
