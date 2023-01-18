package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
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
    private ListParams listParams;

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
    protected EnvironmentExp verifyDeclMethodPass2(DecacCompiler compiler, AbstractIdentifier superClass,
                                                   AbstractIdentifier name) throws ContextualError {
        Type type1 = this.type.verifyType(compiler);

        this.varName.setType(type1);
        Signature sig = this.listParams.verifyListDeclParamPass2(compiler);
        this.varName.setDefinition(new MethodDefinition(this.type.getType(), getLocation(),
                sig, 0));

        if (superClass.getClassDefinition().getMembers().get(name.getName()) != null) {
            if (superClass.getClassDefinition().getMembers().get(name.getName()).isMethod()
                    && superClass.getClassDefinition().getMembers().get(name.getName()).asMethodDefinition("conversion en MethodeDef impossible", getLocation()).getSignature().equals(sig)) {
                try {
                    if (superClass.getClassDefinition().getMembers().get(name.getName()).getType().sameType(type1)
                            || type1.asClassType("bruh", getLocation()).isSubClassOf(superClass.getClassDefinition().getMembers().get(name.getName()).getType().asClassType("bruh", getLocation())))
                    {}
                    else{
                        throw new ContextualError(compiler.displaySourceFile() + ":"
                                + this.getLocation().errorOutPut() + ": Method name conflict in super class", this.getLocation());
                    }
                } catch (ContextualError e) {
                    throw new ContextualError(compiler.displaySourceFile() + ":"
                            + this.getLocation().errorOutPut() + ": Subtype condition not respected", this.getLocation());
                }
            }
            else{
                throw new ContextualError(compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Method name conflict in super class", this.getLocation());
            }
        }

        EnvironmentExp envToReturn = new EnvironmentExp(superClass.getClassDefinition().getMembers());
        try {
            envToReturn.declare(this.varName.getName(), this.varName.getMethodDefinition());
        } catch (EnvironmentExp.DoubleDefException ignored) {
        }

        return envToReturn;
    }

    @Override
    public void codeGen(DecacCompiler compiler) {
        Label function = new Label(varName.getName().getName());
        compiler.addLabel(function);
        methodBody.codeGen(compiler);
    }
}
