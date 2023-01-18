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

    @Override
    public void decompile(IndentPrintStream s) {
        for ( AbstractDeclMethod current : this.getList()){
            current.decompile(s);
        }
    }
}
