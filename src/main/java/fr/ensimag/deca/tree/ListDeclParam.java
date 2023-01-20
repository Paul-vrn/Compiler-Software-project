package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {
    private static final Logger LOG = Logger.getLogger(ListDeclParam.class);
    protected EnvironmentExp classEnvExp;

    protected Signature verifyListDeclParamPass2(DecacCompiler compiler)
            throws ContextualError {

        Signature sig = new Signature();

        for (AbstractDeclParam current : this.getList()) {
            sig.add(current.verifyDeclParamPass2(compiler));
        }
        return sig;
    }

    protected EnvironmentExp verifyListDeclParamPass3(DecacCompiler compiler)
            throws ContextualError {

        EnvironmentExp envExpR = new EnvironmentExp(this.classEnvExp);

        for (AbstractDeclParam current : this.getList()){
            for(Map.Entry<SymbolTable.Symbol, ExpDefinition> entry :
                    current.verifyDeclParamPass3(compiler).getDictionary().entrySet()){
                try {
                    envExpR.declare(entry.getKey(), entry.getValue());
                }catch (EnvironmentExp.DoubleDefException e){
                    throw new ContextualError( compiler.displaySourceFile() + ":"
                            + current.getLocation().errorOutPut() + ": Parameter already defined", current.getLocation());
                }
            }
        }

        return envExpR;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        Iterator<AbstractDeclParam> iterator = getList().iterator();
        while (iterator.hasNext()) {
            iterator.next().decompile(s);
            if (iterator.hasNext()) {
                s.print(", ");
            }
        }
    }

    public void codeGenListDeclParam(DecacCompiler compiler, EnvironmentExp env) {
        int i = -3;
        for (AbstractDeclParam current : this.getList()) {
            current.codeGenDeclParam(compiler, env, i);
            i--;
        }
    }
}
