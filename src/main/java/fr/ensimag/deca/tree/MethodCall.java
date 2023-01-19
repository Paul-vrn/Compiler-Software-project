package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class MethodCall extends AbstractExpr{

    public AbstractExpr expr;
    public AbstractIdentifier methodId;
    public RvalueStar rvalueS;

    public MethodCall(AbstractExpr expr, AbstractIdentifier methodId, RvalueStar rvalueS){
        super();
        this.expr = expr;
        this.methodId = methodId;
        this.rvalueS = rvalueS;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.expr.verifyExpr(compiler, localEnv, currentClass);

        if(compiler.environmentType.getEnvTypes().get(type1.getName()).isClass())

        if(!type1.isClass()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Call method of a non-class identifier", this.getLocation());
        }

        MethodDefinition def = (MethodDefinition) this.methodId.verifyDefinition(compiler, ((ClassDefinition) compiler.environmentType.getEnvTypes().get(type1.getName())).getMembers());

        this.rvalueS.verify(compiler, localEnv, currentClass, def.getSignature());

        return def.getType();
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
}
