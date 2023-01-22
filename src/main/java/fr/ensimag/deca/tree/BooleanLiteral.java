package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.MOV;
import fr.ensimag.pseudocode.ima.instructions.LOAD;

import java.io.PrintStream;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(new BooleanType(compiler.createSymbol("boolean")));
        if (this.getType().isBoolean()){
            return this.getType();
        }
        throw new ContextualError( compiler.displaySourceFile() + ":"
                + this.getLocation().errorOutPut() + ": Boolean Type problem", this.getLocation());
    }

    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.addInstruction(new LOAD(value ? 1 : 0, RegisterIMA.getR(n)));
        compiler.getMemory().setLastGRegister(n);
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        compiler.addInstruction(new MOV(new ImmediateInteger(value ? 1 : 0), RegisterARM.getR(n)));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

}
