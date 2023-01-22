package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.VSTR;
import fr.ensimag.pseudocode.ima.instructions.STORE;
import org.apache.commons.lang.Validate;
import fr.ensimag.pseudocode.arm.instructions.STR;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class Initialization extends AbstractInitialization {

    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void codeGenInit(DecacCompiler compiler, AbstractIdentifier varName) {
        expression.codeGenExpr(compiler, 2);
        compiler.addInstruction(new STORE(RegisterIMA.getR(2), varName.getExpDefinition().getOperand()));
    }

    @Override
    protected void armCodeGenInit(DecacCompiler compiler, AbstractIdentifier varName) {
        expression.armCodeGenExpr(compiler, 4, 2);
        if (expression.getType().isFloat()) {
            compiler.addInstruction(new VSTR(RegisterARM.getS(2), varName.getExpDefinition().getOperand()));
        } else {
            // int || bool
            compiler.addInstruction(new STR(RegisterARM.getR(4), varName.getExpDefinition().getOperand()));
        }
    }

    @Override
    public void codeGenInitField(DecacCompiler compiler, Type type, int n) {
        expression.codeGenExpr(compiler, 2);
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
                                        EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        this.setExpression(this.getExpression().verifyRValue(compiler, localEnv, currentClass, t));
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(" = ");
        this.getExpression().decompile(s);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }
}
