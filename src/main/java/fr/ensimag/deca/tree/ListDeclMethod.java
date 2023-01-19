package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;

import java.util.Map;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {

    protected EnvironmentExp verifyListDeclMethodPass2(DecacCompiler compiler, AbstractIdentifier superClass, AbstractIdentifier name)
            throws ContextualError {
        EnvironmentExp envExpR = new EnvironmentExp(superClass.getClassDefinition().getMembers());

        for (AbstractDeclMethod current : this.getList()){
            for(Map.Entry<SymbolTable.Symbol, ExpDefinition> entry :
                    current.verifyDeclMethodPass2(compiler, superClass, name).getDictionary().entrySet()){
                try {
                    envExpR.declare(entry.getKey(), entry.getValue());
                }catch (EnvironmentExp.DoubleDefException e){
                    throw new ContextualError( compiler.displaySourceFile() + ":"
                            + this.getLocation().errorOutPut() + ": Method already defined", this.getLocation());
                }
            }
        }

        return envExpR;
    }

    protected void verifyListDeclMethodPass3(DecacCompiler compiler, EnvironmentExp envExp, AbstractIdentifier name)
            throws ContextualError {
        for(AbstractDeclMethod current : this.getList()){
            current.verifyDeclMethodPass3(compiler, envExp, name);
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for ( AbstractDeclMethod current : this.getList()){
            current.decompile(s);
        }
    }

    public void codeGenDeclMethod(DecacCompiler compiler, String className, EnvironmentExp localEnvExp) {
        for (AbstractDeclMethod declMethod : getList()) {
            declMethod.codeGenDeclMethod(compiler, className, localEnvExp);
        }
    }
}
