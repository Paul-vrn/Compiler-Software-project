package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.InlinePortion;

import java.io.PrintStream;

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
        //TODO
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, EnvironmentExp envExpParam,
                                    AbstractIdentifier name, Type returnType) throws ContextualError {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    public void codeGen(DecacCompiler compiler) {
        //TODO put label
        compiler.add(new InlinePortion(asm.getValue()));
        compiler.getLabelFactory().createTestReturn(compiler);
    }
}
