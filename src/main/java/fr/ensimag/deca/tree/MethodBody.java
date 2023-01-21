package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.instructions.ADDSP;
import fr.ensimag.pseudocode.instructions.RTS;
import fr.ensimag.pseudocode.instructions.TSTO;
import fr.ensimag.pseudocode.ima.instructions.RTS;

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
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp classEnv, EnvironmentExp envExpParam,
                                    AbstractIdentifier name, Type returnType) throws ContextualError {
        this.listDeclField.verifyListDeclVariable(compiler, envExpParam, name.getClassDefinition());
        this.listInst.verifyListInst(compiler, envExpParam, name.getClassDefinition(), returnType);
    }

    @Override
    public int codeGenMethodBody(DecacCompiler compiler, EnvironmentExp localEnvExp){
        listDeclField.codeGenListDeclField(compiler, localEnvExp);
        listInst.codeGenListInst(compiler);
        return listDeclField.size();
    }
}
