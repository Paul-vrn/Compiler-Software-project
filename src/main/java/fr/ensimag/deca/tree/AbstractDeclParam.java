package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;

/**
 * Declaration of a parameter
 *
 * @author gl21
 * @date 10/01/2023
 */

public abstract class AbstractDeclParam extends Tree {

    /**
     * Implements Decl param of pass 2
     *
     * @param compiler contains "env_types" attribute
     * @return
     * @throws ContextualError if an error has been caught (namely, if a parameter is of type void)
     */

    protected abstract Type verifyDeclParamPass2(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Implements Decl param for pass 3
     *
     * @param compiler contains "env_types" attribute
     * @return
     * @throws ContextualError if an error has been caught (if there exists in the EnvironmentExp the same name)
     */
    protected abstract EnvironmentExp verifyDeclParamPass3(DecacCompiler compiler)
            throws ContextualError;

    public abstract void codeGenDeclParam(DecacCompiler compiler, EnvironmentExp env, int index);
}
