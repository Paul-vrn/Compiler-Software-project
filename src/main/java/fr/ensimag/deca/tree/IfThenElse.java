package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class IfThenElse extends AbstractInst {
    
    private final AbstractExpr condition; 
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.condition.verifyCondition(compiler, localEnv, currentClass);

        for (AbstractInst i : this.thenBranch.getList()) {
            i.verifyInst(compiler, localEnv, currentClass, returnType);
        }

        for (AbstractInst i : this.elseBranch.getList()) {
            i.verifyInst(compiler, localEnv, currentClass, returnType);
        }
    }

    /**
     * Generate assembly code for the instruction.
     *
     * @param compiler
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.codeGenIf(compiler, 0);
    }

    protected void codeGenIf(DecacCompiler compiler, int p) {
        int nbIf = compiler.getLabelFactory().getNbIfThenElse();
        Label labelElse = new Label("ELSE_" + nbIf + "_" + p);
        Label labelEnd = new Label("END_IF_" + nbIf);
        condition.codeGenExpr(compiler, 2);
        compiler.addInstruction(new CMP(0, Register.getR(2)));
        compiler.addInstruction(new BEQ(labelElse));
        thenBranch.codeGenListInst(compiler);
        compiler.addInstruction(new BRA(labelEnd));
        compiler.addLabel(labelElse);
        elseBranch.codeGenIf(compiler, p+1);
        if(p==0){
            compiler.addLabel(labelEnd);
            compiler.getLabelFactory().setNbIfThenElse(nbIf + 1);

        }

    }


    @Override
    public void decompile(IndentPrintStream s){
        s.print("if (");
        this.condition.decompile(s);
        s.println(") {");
        s.indent();
        this.thenBranch.decompile(s);
        s.println("}");
        s.unindent();
        s.println("else {");
        s.indent();
        this.elseBranch.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
