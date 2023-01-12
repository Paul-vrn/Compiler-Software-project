package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class DeclParam extends Tree {


    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;

    public DeclParam(AbstractIdentifier type, AbstractIdentifier varName) {
        Validate.notNull(type);
        Validate.notNull(varName);
        this.type = type;
        this.varName = varName;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {

    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }
}
