package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Variable declaration
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class AbstractDeclMethod extends Tree {

    protected abstract EnvironmentExp verifyDeclMethodPass2(DecacCompiler compiler,
                                                           AbstractIdentifier superClass, AbstractIdentifier name)
            throws ContextualError;

    protected abstract void verifyDeclMethodPass3(DecacCompiler compiler,
                                                            EnvironmentExp envExp, AbstractIdentifier name)
            throws ContextualError;

    public abstract void codeGenDeclMethod(DecacCompiler compiler, String className, EnvironmentExp localEnvExp);
}