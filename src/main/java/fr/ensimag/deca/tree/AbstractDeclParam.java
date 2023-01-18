package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;

public abstract class AbstractDeclParam extends Tree{

    protected abstract Type verifyDeclParamPass2(DecacCompiler compiler)
            throws ContextualError;
}
