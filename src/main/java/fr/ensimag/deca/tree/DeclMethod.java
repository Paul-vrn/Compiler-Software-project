package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import org.apache.commons.lang.Validate;
//import sun.jvm.hotspot.opto.Block;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl21
 * @date 01/01/2023
 */
public class DeclMethod extends AbstractDeclMethod {
    private AbstractIdentifier type;
    private AbstractIdentifier varName;
    private ListParams listParams;
    private AbstractMethodBody methodBody;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier varName, ListParams listParams, AbstractMethodBody methodBody) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(listParams);
        Validate.notNull(methodBody);
        this.type = type;
        this.varName = varName;
        this.listParams = listParams;
        this.methodBody = methodBody;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        s.print("(");
        listParams.decompile(s);
        s.print(")");
        methodBody.decompile(s);
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, true);
        listParams.prettyPrint(s, prefix, true);
        methodBody.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        listParams.iter(f);
        methodBody.iter(f);
    }

    @Override
    protected void verifyDeclField(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

    }

    @Override
    public void codeGen(DecacCompiler compiler) {
        Label function = new Label(varName.getName().getName());
        compiler.addLabel(function);
        methodBody.codeGen(compiler);
    }
}
