package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.LabelIdentification;
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

public class Cast extends AbstractInst{
    private AbstractIdentifier type;
    private AbstractExpr expr;

    public Cast(AbstractIdentifier type, AbstractExpr expr) {
        this.type = type;
        this.expr = expr;
    }

    public AbstractIdentifier getType() {
        return type;
    }

    public AbstractExpr getExpr() {
        return expr;
    }

    @Override
    public void VerifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass, Type returnType) throws ContextualError {
        Type type1 = this.type.verifyType(compiler);
        if(!type1.isInt()){
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.type.getLocation().errorOutPut() + ": Float can be casted only with Int", this.type.getLocation());
        }
        this.expr.verifyExpr(compiler, localEnv, currentClass);
    }


    @Override
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

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix, boolean isLastChild) {
        type.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }
}
