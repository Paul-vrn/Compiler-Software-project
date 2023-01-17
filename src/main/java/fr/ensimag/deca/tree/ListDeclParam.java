package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
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
            sig.add(current.verifyDeclParamPass2(compiler));
        }
        return sig;
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
