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

/**
 * Class for the Return statement
 * example (in a method): return 1;
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Return extends AbstractInst{

    private final AbstractExpr rvalue;

    public Return(AbstractExpr rvalue) {
        Validate.notNull(rvalue);
        this.rvalue = rvalue;
    }

    /**
     * VerifyInst for return
     *
     * @param compiler contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass
     *          corresponds to the "class" attribute (null in the main bloc).
     * @param returnType
     *          corresponds to the "return" attribute (void in the main bloc).
     * @throws ContextualError
     */
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        //verifies that the type is not void
        if(returnType.isVoid()){
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": No return possible in void method", this.getLocation());
        }
        //calls the RValue function
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
