package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.VCVTFS;
import fr.ensimag.pseudocode.arm.instructions.VCVTSF;
import fr.ensimag.pseudocode.arm.instructions.VMOV;
import fr.ensimag.pseudocode.ima.instructions.FLOAT;
import fr.ensimag.pseudocode.ima.instructions.INT;

/**
 * Cast Operator : (type) (expr)
 * Example : (int) (x)
 */
public class Cast extends AbstractExpr {
    private AbstractIdentifier type;
    private AbstractExpr expr;

    public Cast(AbstractIdentifier type, AbstractExpr expr) {
        super();
        this.type = type;
        this.expr = expr;
    }

    /**
     * Throws an error if the type and the castType are incompatible.
     *
     * @param compiler     (contains the "env_types" attribute)
     * @param localEnv     Environment in which the expression should be checked
     *                     (corresponds to the "env_exp" attribute)
     * @param currentClass Definition of the class containing the expression
     *                     (corresponds to the "class" attribute)
     *                     is null in the main bloc.
     * @return
     * @throws ContextualError
     */
    @Override
    public Type verifyExpr(DecacCompiler compiler,
                           EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type1 = this.type.verifyType(compiler);
        Type type2 = this.expr.verifyExpr(compiler, localEnv, currentClass);

        /* Verification of the compatibility between the type and the castType */
        if (!((type1.isInt() && type2.isFloat()) || (type2.isInt() && type1.isFloat()) || type1.sameType(type2)
                || (type2.isClass() && type1.isClass() && type2.asClassType("", this.getLocation()).isSubClassOf(type1.asClassType("", this.getLocation()))))) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.type.getLocation().errorOutPut() + ": Cast type is invalid", this.type.getLocation());
        }
        this.setType(type1);
        return type1;
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        expr.codeGenExpr(compiler, n);
        if (getType().isInt()) {
            compiler.addInstruction(new INT(RegisterIMA.getR(n), RegisterIMA.getR(n)));
        } else if (getType().isFloat()) {
            compiler.addInstruction(new FLOAT(RegisterIMA.getR(n), RegisterIMA.getR(n)));
        }
    }

    @Override
    protected void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        expr.armCodeGenExpr(compiler, n, m);
        if (type.getType().isInt() && expr.getType().isFloat()) {
            // int <-- float
            compiler.addInstruction(new VCVTSF(RegisterARM.getS(m), RegisterARM.getS(m)));
            compiler.addInstruction(new VMOV(RegisterARM.getS(m), RegisterARM.getR(n)));
        } else if (type.getType().isFloat() && expr.getType().isInt()) {
            // float <-- int
            compiler.addInstruction(new VMOV(RegisterARM.getR(n), RegisterARM.getS(m)));
            compiler.addInstruction(new VCVTFS(RegisterARM.getS(m), RegisterARM.getS(m)));
        } else {
            throw new ClassCastException("Cast type is invalid");
        }
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        type.decompile(s);
        s.print(")");
        s.print("(");
        expr.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        expr.iter(f);
    }

    protected void prettyPrintChildren(PrintStream s, String prefix, boolean isLastChild) {
        type.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }
}
