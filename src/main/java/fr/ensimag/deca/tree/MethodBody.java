package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

/**
 * Empty main Deca program
 *
 * @author gl21
 * @date 01/01/2023
 */
public class MethodBody extends AbstractMethodBody {

    private ListDeclVar listDeclField;
    private ListInst listInst;

    public MethodBody(ListDeclVar listDeclVar, ListInst listInst) {
        this.listDeclField = listDeclVar;
        this.listInst = listInst;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        listDeclField.decompile(s);
        listInst.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    public void codeGen(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Not yet supported");
    }
}
