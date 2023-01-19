package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.RTS;

import java.io.PrintStream;

/**
 * Empty main Deca program
 *
 * @author gl21
 * @date 01/01/2023
 */
public class MethodBody extends AbstractMethodBody {

    private ListDeclVar listDeclField = new ListDeclVar();
    private ListInst listInst = new ListInst();

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
        s.println("}");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        listDeclField.prettyPrint(s, prefix, false);
        listInst.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        listDeclField.iter(f);
        listInst.iter(f);
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    public void codeGen(DecacCompiler compiler) {
        listDeclField.codeGenListDeclVar(compiler);
        contextSave(compiler);
        listInst.codeGenListInst(compiler);
        contextRestore(compiler);
        compiler.addInstruction(new RTS());
    }
}
