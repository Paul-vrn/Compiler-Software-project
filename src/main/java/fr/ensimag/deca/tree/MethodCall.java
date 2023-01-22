package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.ima.instructions.*;

import java.io.PrintStream;

public class MethodCall extends AbstractExpr{

    public AbstractExpr expr;
    public AbstractIdentifier methodId;
    public ListExpr parameters;

    public MethodCall(AbstractExpr expr, AbstractIdentifier methodId, ListExpr parameters){
        super();
        this.expr = expr;
        this.methodId = methodId;
        this.parameters = parameters;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.expr.verifyExpr(compiler, localEnv, currentClass);

        if(!type1.isClass()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Call method of a non-class identifier", this.getLocation());
        }

        Definition def = this.methodId.verifyDefinition(compiler, ((ClassDefinition) compiler.environmentType.getEnvTypes().get(type1.getName())).getMembers());
        if(!def.isMethod()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Must be a method", this.getLocation());
        }

        MethodDefinition defMethod = (MethodDefinition) def;

        this.setType(defMethod.getType());

        Signature sig = defMethod.getSignature();

        for(int c = 0; c < this.parameters.size(); c++){
            if(sig.size() > 0){
                this.parameters.getList().get(c).verifyRValue(compiler, localEnv, currentClass, sig.paramNumber(c));
            }else{
                break;
            }
        }

        return defMethod.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        methodId.decompile(s);
        s.print("(");
        parameters.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.expr.prettyPrint(s, prefix, false);
        this.methodId.prettyPrint(s, prefix, true);
        this.parameters.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        methodId.iter(f);
        parameters.iter(f);
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.addComment("method call at " + methodId.getLocation() + " : " +methodId.getName().getName());
        compiler.addInstruction(new ADDSP(parameters.getList().size()+1));
        compiler.getMemory().increaseTSTO(parameters.getList().size()+3); // +1 for this + 2 for BSR

        expr.codeGenExpr(compiler, n);

        compiler.addInstruction(new STORE(RegisterIMA.getR(n), new RegisterOffset(0, RegisterIMA.SP))); // empilement implicite
        int index = -1;
        for (AbstractExpr param : parameters.getList()) {
            param.codeGenExpr(compiler, n);
            compiler.addInstruction(new STORE(RegisterIMA.getR(n), new RegisterOffset(index, RegisterIMA.SP)));
            index--; // empilement des paramètres
        }

        compiler.addInstruction(new LOAD(new RegisterOffset(0, RegisterIMA.SP), RegisterIMA.getR(n)));
        compiler.getLabelFactory().createTestDeferencementNull(compiler, RegisterIMA.getR(n));

        compiler.addInstruction(new LOAD(new RegisterOffset(0, RegisterIMA.getR(n)), RegisterIMA.getR(n)));
        compiler.addInstruction(new BSR(new RegisterOffset(methodId.getMethodDefinition().getIndex(), RegisterIMA.getR(n))));
        compiler.addInstruction(new SUBSP(parameters.getList().size()+1));
        compiler.getMemory().decreaseTSTO(parameters.getList().size()+3); // +1 for this + 2 for BSR

        if (!getType().isVoid()){
            compiler.addInstruction(new LOAD(RegisterIMA.R0, RegisterIMA.getR(n)));
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.codeGenExpr(compiler, 2);
    }
}
