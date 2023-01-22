package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

/**
 * InstanceOf Operator.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class InstanceOf extends AbstractExpr{

    private AbstractExpr expr;
    private AbstractIdentifier type;

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
        if(!((type1 == null || type1.isClass()) && (type2.isClass()))){
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.type.getLocation().errorOutPut() + ": InstanceOf types invalid", this.type.getLocation());
        }

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
