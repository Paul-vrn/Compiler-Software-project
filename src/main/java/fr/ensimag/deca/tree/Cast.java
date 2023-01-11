package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import org.apache.commons.lang.Validate;

public class Cast extends AbstractExpr{
    private AbstractIdentifier type;
    private AbstractExpr expr;

    public Cast(AbstractIdentifier type, AbstractExpr expr) {
        super();
        this.type = type;
        this.expr = expr;
    }

    public String getOperatorName() {return "";}

    public AbstractIdentifier getType1() {return type;}

    public AbstractExpr getExpr() {
        return expr;
    }

    public Type verifyExpr(DecacCompiler compiler,
                                    EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError{
        Type type1 = this.type.verifyType(compiler);
        Type type2 = this.getExpr().verifyExpr(compiler,localEnv,currentClass);

        if(!((type1.isInt() && type2.isFloat()) || (type2.isInt() && type1.isFloat()) || type1.sameType(type2))){
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.type.getLocation().errorOutPut() + ": cast type is invalid", this.type.getLocation());
        }
        this.setType(type1);
        return type1;
    }

    public void codeGen(){

    }
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        type.decompile(s);
        s.print(")");
        s.print("(");
        expr.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        expr.iter(f);
    }

    protected void prettyPrintChildren(PrintStream s, String prefix, boolean isLastChild) {
        type.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }
}
