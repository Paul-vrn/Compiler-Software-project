package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.Signature;
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

    protected Signature verifyListDeclParamPass2(DecacCompiler compiler)
            throws ContextualError {

        Signature sig = new Signature();

        for (AbstractDeclParam current : this.getList()) {


        }
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
}
