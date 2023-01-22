package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

import java.util.Iterator;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListExpr extends TreeList<AbstractExpr> {


    @Override
    public void decompile(IndentPrintStream s) {
        Iterator<AbstractExpr> it = this.getList().iterator();
        while (it.hasNext()) {
            it.next().decompile(s);
            if (it.hasNext()) {
                s.print(", ");
            }
        }
    }
}
