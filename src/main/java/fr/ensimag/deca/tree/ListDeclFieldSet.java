package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclFieldSet extends TreeList<ListDeclField> {


    @Override
    public void decompile(IndentPrintStream s) {
        for ( ListDeclField current : this.getList()){
            current.decompile(s);
        }
    }
}
