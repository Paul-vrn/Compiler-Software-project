package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class MethodCall extends AbstractExpr{

    public AbstractExpr expr;
    public AbstractIdentifier methodId;
    public ListExpr parameters;

    public MethodCall(AbstractExpr expr, AbstractIdentifier methodId, ListExpr parameters){
        super();
        this.expr = expr;
        this.methodId = methodId;
        this.parameters = parameters;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.expr.verifyExpr(compiler, localEnv, currentClass);

        if(!type1.isClass()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Call method of a non-class identifier", this.getLocation());
        }

        MethodDefinition def = (MethodDefinition) this.methodId.verifyDefinition(compiler, ((ClassDefinition) compiler.environmentType.getEnvTypes().get(type1.getName())).getMembers());

        Signature sig = def.getSignature();

        for(int c = 0; c < this.parameters.size(); c++){
            if(sig.size() > 0){
                this.parameters.getList().get(c).verifyRValue(compiler, localEnv, currentClass, sig.paramNumber(0));
                sig.popHead();
            }else{
                break;
            }
        }


        return def.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.expr.prettyPrint(s, prefix, false);
        this.methodId.prettyPrint(s, prefix, true);
        this.parameters.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }
}
