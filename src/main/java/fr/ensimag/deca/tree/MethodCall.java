package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.io.PrintStream;

/**
 * Empty main Deca program
 *
 * @author gl21
 * @date 01/01/2023
 */
public class MethodCall extends AbstractExpr {

    private AbstractExpr obj;
    private AbstractIdentifier meth;
    private ListExpr params;

    public MethodCall(AbstractExpr expr, AbstractIdentifier methodName, ListExpr params) {
        this.obj = expr;
        this.meth = methodName;
        this.params = params;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        obj.decompile(s);
        s.print(".");
        meth.decompile(s);
        s.print("(");
        params.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        obj.prettyPrint(s, prefix, true);
        meth.prettyPrint(s, prefix, true);
        params.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        obj.iter(f);
        meth.iter(f);
        params.iter(f);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        return null;
    }

    protected void codeGen(DecacCompiler compiler) {
        compiler.addInstruction(new ADDSP(params.getList().size()+1));
        DAddr addrMethod = meth.getMethodDefinition().getOperand();
        compiler.addInstruction(new LOAD(addrMethod, Register.getR(2)));
        compiler.addInstruction(new STORE(Register.getR(2), new RegisterOffset(0, Register.SP)));
        int n = -1;
        for (AbstractExpr expr : params.getList()) {
            expr.codeGenExpr(compiler, 2);
            compiler.addInstruction(new STORE(Register.getR(2), new RegisterOffset(n, Register.SP)));
            n--;
        }
        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP), Register.getR(2)));
        compiler.getLabelFactory().createTestDeferencementNull(compiler, Register.getR(2));


        //LOAD 0(R2), R2 ; On récupère l'adresse de la table des méthodes
        //BSR 1(R2) ; Appel de la méthode move (première méthode de la classe)

        compiler.addInstruction(new SUBSP(params.getList().size()+1));
        // Si le type de retour est void et qu'on a pas de return avant ce if --> erreur
        if (!meth.getMethodDefinition().getType().isVoid()) {
            compiler.getLabelFactory().createTestReturn(compiler);
        }
    }
}
