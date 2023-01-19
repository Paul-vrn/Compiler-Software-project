package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;

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

        MethodDefinition def = (MethodDefinition) this.methodId.verifyDefinition(compiler, ((ClassDefinition) compiler.environmentType.getEnvTypes().get(type1.getName())).getMembers());

        Signature sig = def.getSignature();

        for(int c = 0; c < this.parameters.size(); c++){
            if(sig.size() > 0){
                this.parameters.getList().get(c).verifyRValue(compiler, localEnv, currentClass, sig.paramNumber(0));
                sig.popHead();
            }else{
                break;
            }
        }


        return def.getType();
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


    protected void codeGen(DecacCompiler compiler) {
        compiler.addInstruction(new ADDSP(parameters.getList().size()+1));
        DAddr addrMethod = methodId.getMethodDefinition().getOperand();
        compiler.addInstruction(new LOAD(addrMethod, Register.getR(2)));
        compiler.addInstruction(new STORE(Register.getR(2), new RegisterOffset(0, Register.SP)));
        int n = -1;
        for (AbstractExpr expr : parameters.getList()) {
            expr.codeGenExpr(compiler, 2);
            compiler.addInstruction(new STORE(Register.getR(2), new RegisterOffset(n, Register.SP)));
            n--;
        }

        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP), Register.getR(2)));
        compiler.getLabelFactory().createTestDeferencementNull(compiler, Register.getR(2));

        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.getR(2)), Register.getR(2)));
        compiler.addInstruction(new BSR(new RegisterOffset(1, Register.getR(2))));
        compiler.addInstruction(new SUBSP(parameters.getList().size()+1));

    }
}
