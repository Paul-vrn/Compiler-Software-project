package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class LValueIdent extends AbstractLValue{

    public AbstractIdentifier ident;

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Definition def = this.ident.verifyDefinition(compiler, localEnv);

        if(!def.isExpression()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Left value type error", this.getLocation());
        }

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


    @Override
    public void codeGenStore(DecacCompiler compiler, int n) {
        throw new UnsupportedOperationException("Error.");
    }
}
