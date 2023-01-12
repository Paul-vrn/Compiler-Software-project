package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl21
 * @date 01/01/2023
 */
public class DeclField extends AbstractDeclField {
    private Visibility visibility;
    private AbstractIdentifier type;
    private AbstractIdentifier fieldName;
    private AbstractInitialization initialization;

    public DeclField(Visibility visibility, AbstractIdentifier type, AbstractIdentifier fieldName, AbstractInitialization initialization) {
        Validate.notNull(visibility);
        Validate.notNull(type);
        Validate.notNull(fieldName);
        Validate.notNull(initialization);
        this.visibility = visibility;
        this.type = type;
        this.fieldName = fieldName;
        this.initialization = initialization;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        visibility.decompile(s);
        s.print(" ");
        type.decompile(s);
        s.print(" ");
        fieldName.decompile(s);
        initialization.decompile(s);
        s.print(";");
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void verifyDeclField(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

    }

    @Override
    public void codeGen(DecacCompiler compiler) {

    }
}
