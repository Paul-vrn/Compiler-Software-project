package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListParams extends TreeList<Param> {
    private static final Logger LOG = Logger.getLogger(ListParams.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (Param c : getList()) {
            c.decompile(s);
            s.println();
        }
    }
}
