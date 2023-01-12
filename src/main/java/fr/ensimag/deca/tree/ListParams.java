package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

import java.util.Iterator;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListParams extends TreeList<DeclParam> {
    private static final Logger LOG = Logger.getLogger(ListParams.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        Iterator<DeclParam> iterator = getList().iterator();
        while (iterator.hasNext()) {
            iterator.next().decompile(s);
            if (iterator.hasNext()) {
                s.print(", ");
            }
        }
    }
}
