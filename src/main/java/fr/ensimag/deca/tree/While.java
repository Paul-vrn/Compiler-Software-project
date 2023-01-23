package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Label;

import java.io.PrintStream;

import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.B;
import fr.ensimag.pseudocode.ima.instructions.BEQ;
import fr.ensimag.pseudocode.ima.instructions.BRA;
import fr.ensimag.pseudocode.ima.instructions.CMP;
import org.apache.commons.lang.Validate;

/**
 * Class for the while inst
 *
 * @author gl21
 * @date 01/01/2023
 */
public class While extends AbstractInst {
    private AbstractExpr condition;
    private ListInst body;

    public AbstractExpr getCondition() {
        return condition;
    }

    public ListInst getBody() {
        return body;
    }

    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.condition = condition;
        this.body = body;
    }

    /**
     * Page 225, 8.2
     *
     * @param compiler
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        int nbWhile = compiler.nbWhile();
        Label labelStart = new Label("WHILE_START_" + nbWhile);
        Label labelCond = new Label("WHILE_COND_" + nbWhile);
        compiler.addInstruction(new BRA(labelCond));
        compiler.addLabel(labelStart);
        body.codeGenListInst(compiler);
        compiler.addLabel(labelCond);
        condition.codeGenExpr(compiler, 2);
        compiler.addInstruction(new CMP(1, RegisterIMA.getR(2)));
        compiler.addInstruction(new BEQ(labelStart));
    }

    @Override
    protected void armCodeGenInst(DecacCompiler compiler) {
        int nbWhile = compiler.nbWhile();
        Label labelStart = new Label("WHILE_START_" + nbWhile);
        Label labelCond = new Label("WHILE_COND_" + nbWhile);
        compiler.addInstruction(new B(labelCond));
        compiler.addLabel(labelStart);
        body.armCodeGenListInst(compiler);
        compiler.addLabel(labelCond);
        condition.armCodeGenExpr(compiler, 4, 2);
        compiler.addInstruction(new CMP(1, RegisterIMA.getR(4)));
        compiler.addInstruction(new BEQ(labelStart));
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
                              ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.condition.verifyCondition(compiler, localEnv, currentClass);
        //Applies verifyInst on all instantiations of its body
        for (AbstractInst i : this.body.getList()) {
            i.verifyInst(compiler, localEnv, currentClass, returnType);
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getBody().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

}
