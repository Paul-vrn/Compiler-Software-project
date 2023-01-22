package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.*;
import fr.ensimag.pseudocode.ima.instructions.*;

import java.io.PrintStream;

/**
 * InstanceOf Operator.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class InstanceOf extends AbstractExpr {

    private AbstractExpr expr;
    private AbstractIdentifier type;

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        int nb = compiler.getLabelFactory().nbInstanceOf();
        Label labelTrue = new Label("true_instance_of_" + nb);
        Label labelFalse = new Label("false_instance_of" + nb);
        Label labelStart = new Label("instance_of" + nb);
        Label labelEnd = new Label("end_instance_of" + nb);
        expr.codeGenExpr(compiler, n);
        compiler.addInstruction(new LOAD(new RegisterOffset(0, RegisterIMA.getR(n)), RegisterIMA.getR(n)));
        compiler.addInstruction(new LEA(type.getClassDefinition().getOperand(), RegisterIMA.getR(0)));


        compiler.addLabel(labelStart);
        compiler.addInstruction(new CMP(RegisterIMA.getR(0), RegisterIMA.getR(n)));
        compiler.addInstruction(new BEQ(labelTrue));
        compiler.addInstruction(new LOAD(new RegisterOffset(0, RegisterIMA.getR(n)), RegisterIMA.getR(n)));
        compiler.addInstruction(new CMP(new NullOperand(), RegisterIMA.getR(n)));
        compiler.addInstruction(new BEQ(labelFalse));
        compiler.addInstruction(new BRA(labelStart));
        compiler.addLabel(labelTrue);
        compiler.addInstruction(new LOAD(new ImmediateInteger(1), RegisterIMA.getR(n)));
        compiler.addInstruction(new BRA(labelEnd));
        compiler.addLabel(labelFalse);
        compiler.addInstruction(new LOAD(new ImmediateInteger(0), RegisterIMA.getR(n)));
        compiler.addLabel(labelEnd);
    }

    public InstanceOf(AbstractIdentifier type, AbstractExpr expr) {
        super();
        this.type = type;
        this.expr = expr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.expr.verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.type.verifyType(compiler);

        /* Verifies if the instanceOf fits with the types */
        if (!((type1 == null || type1.isClass()) && (type2.isClass()))) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.type.getLocation().errorOutPut() + ": InstanceOf types invalid", this.type.getLocation());
        }

        this.setType(compiler.environmentType.BOOLEAN);

        return compiler.environmentType.BOOLEAN;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        expr.decompile(s);
        s.print(" instanceof ");
        type.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, true);
        type.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        type.iter(f);
    }
}
