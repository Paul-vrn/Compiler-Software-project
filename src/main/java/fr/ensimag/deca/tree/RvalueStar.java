package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;

public class RvalueStar{
    public AbstractExpr rvalue;
    public void verify(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition className, Signature sig) throws ContextualError {
        if(sig.size() > 0){
            this.rvalue.verifyRValue(compiler, localEnv, className, sig.paramNumber(0));
            sig.popHead();
            this.verify(compiler, localEnv, className, sig);
        }

    }
}
