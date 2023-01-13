package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {


    @Override
    public void decompile(IndentPrintStream s) {
        for ( AbstractDeclMethod current : this.getList()){
            current.decompile(s);
        }
    }
}
