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
public abstract class AbstractDeclField extends Tree {

    protected abstract EnvironmentExp verifyDeclFieldPass2(DecacCompiler compiler,
                                                 AbstractIdentifier superClass, AbstractIdentifier name)
            throws ContextualError;

    protected abstract void verifyDeclFieldPass3(DecacCompiler compiler, EnvironmentExp envExp, AbstractIdentifier name)
            throws ContextualError;

    public abstract AbstractInitialization getInitialization();

    public abstract AbstractIdentifier getFieldName();


    public abstract void codeGenDeclField(DecacCompiler compiler);
}