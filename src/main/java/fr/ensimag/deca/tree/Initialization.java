package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.RegisterARM;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.arm.instructions.VSTR;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import org.apache.commons.lang.Validate;
import fr.ensimag.ima.pseudocode.arm.instructions.STR;

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
        if (expression.getType().isFloat()){
            compiler.addInstruction(new VSTR(RegisterARM.getS(2), varName.getExpDefinition().getOperand()));
        } else {
            // int || bool
            compiler.addInstruction(new STR(RegisterARM.getR(4), varName.getExpDefinition().getOperand()));
        }
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type2 = this.getExpression().verifyExpr(compiler, localEnv, currentClass);

        if(type2 == null){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.expression.getLocation().errorOutPut() + ": Initialization impossible with undefined value", this.expression.getLocation());
        }

        if(!(t.sameType(type2) || (t.isFloat() && type2.isInt()))){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.expression.getLocation().errorOutPut() + ": Initialization type error, " + type2 + " into " + t + " forbidden", this.expression.getLocation());
        }

        if(t.isFloat() && type2.isInt()){
            ConvFloat c = new ConvFloat(this.expression);
            c.verifyExpr(compiler, localEnv, currentClass);
            this.expression = c;
        }
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(" = ");
        this.getExpression().decompile(s);
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }
}
