package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Main block of a Deca program.
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class AbstractMain extends Tree {

    protected abstract void codeGenMain(DecacCompiler compiler);

    protected abstract void armCodeGenMain(DecacCompiler compiler);

    /**
     * Implements non-terminal "main" of [SyntaxeContextuelle] in pass 3
     */
    protected abstract void verifyMain(DecacCompiler compiler) throws ContextualError;
}
