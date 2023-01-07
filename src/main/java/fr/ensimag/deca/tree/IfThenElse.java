package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
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
    }

    /**
     * Generate assembly code for the instruction.
     *
     * @param compiler
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {

    }

    protected void codeGenIf(DecacCompiler compiler, int p) {

        condition.codeGenInst(compiler); // met le résultat dans un registre

        compiler.addInstruction(new BNE(new Label(""))); // saute à la fin du if
        thenBranch.codeGenListInst(compiler);
        compiler.addInstruction(new BRA(new Label("E_FIN" + compiler.getNbIf()))); // on va à la fin
        compiler.addLabel(new Label("E_ELSE" + compiler.getNbIf() + "." + p));
        compiler.addLabel(new Label("E_Sinon"+compiler.getNbIf() + "." + p));
        elseBranch.codeGenListInst(compiler);
        compiler.addLabel(new Label("E_Fin"+ compiler.getNbIf()));
        compiler.getMemory().increaseNbIfThenElse();
    }

    protected void codeGenElseIf(DecacCompiler compiler){

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
