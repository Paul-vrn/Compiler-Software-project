package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;
import sun.jvm.hotspot.opto.Block;

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
    private ListDeclParam listParams;
    private AbstractMethodBody methodBody;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier varName, ListDeclParam listParams, AbstractMethodBody methodBody) {
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
    protected EnvironmentExp verifyDeclMethodPass2(DecacCompiler compiler, AbstractIdentifier superClass,
                                                   AbstractIdentifier name) throws ContextualError {
        Type type1 = this.type.verifyType(compiler);

        this.varName.setType(type1);
        Signature sig = this.listParams.
        this.varName.setDefinition(new MethodDefinition(this.type.getType(), getLocation(),
                this.listParams., 0));

        if(superClass.getClassDefinition().getMembers().get(name.getName()) != null){
            if(!superClass.getClassDefinition().getMembers().get(name.getName()).isField()){
                throw new ContextualError( compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Field name conflict in super class", this.getLocation());
            }
        }
    }

    @Override
    public void codeGen(DecacCompiler compiler) {

    }
}
