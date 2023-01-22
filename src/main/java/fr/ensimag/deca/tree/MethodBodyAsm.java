package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.InlinePortion;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * Empty main Deca program
 *
 * @author gl21
 * @date 01/01/2023
 */
public class MethodBodyAsm extends AbstractMethodBody {

    private StringLiteral asm;

    public MethodBodyAsm(StringLiteral asm) {
        this.asm = asm;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("asm(");
        asm.decompile(s);
        s.print(");");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, EnvironmentExp envExpParam,
                                    AbstractIdentifier name, Type returnType) throws ContextualError {
    }

    @Override
    public int codeGenMethodBody(DecacCompiler compiler, EnvironmentExp localEnvExp) {
        Arrays.stream(asm.getValue()
                        .substring(1, asm.getValue().length() - 1)
                        .replace("\\\"", "\"")
                        .split("\n"))
                .forEach(line -> compiler.add(new InlinePortion(line)));
        return 0;
    }
}
