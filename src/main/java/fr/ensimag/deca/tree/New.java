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
public class New extends AbstractExpr {


    final private AbstractIdentifier className;

    public New(AbstractIdentifier c) {
        Validate.notNull(c);
        className = c;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        className.decompile(s);
        s.print("()");
    }

    public void codeGen(DecacCompiler compiler) {
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        className.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        className.prettyPrint(s, prefix, true);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        return null;
    }
}
