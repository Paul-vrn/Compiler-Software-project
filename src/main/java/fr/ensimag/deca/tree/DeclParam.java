package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class DeclParam extends AbstractDeclParam {


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
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
    }

    @Override
    protected Type verifyDeclParamPass2(DecacCompiler compiler) throws ContextualError {
        Type type1 = this.type.verifyType(compiler);
        if(this.type.getType().isVoid()) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Parameter type void forbidden", this.getLocation());
        }
        this.varName.setType(type1);
        this.varName.setDefinition(new ParamDefinition(type1, getLocation()));

        return type1;


    }
}
