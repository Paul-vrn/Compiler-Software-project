package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class This extends AbstractExpr{

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        if(currentClass == null){
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": This on a null class impossible", this.getLocation());
        }

        this.setType(currentClass.getType());

        return this.getType();
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
