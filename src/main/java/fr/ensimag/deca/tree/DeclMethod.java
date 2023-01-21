package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.tools.SymbolTable;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private EnvironmentExp localEnv;
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

        int index_fin = 0;
        int if_taken = 0;

        if (superClass.getClassDefinition().getMembers().get(varName.getName()) != null) {
            if_taken = 1;
            index_fin = superClass.getClassDefinition().getMembers().get(varName.getName()).asMethodDefinition("bruh",getLocation()).getIndex();
            if (superClass.getClassDefinition().getMembers().get(varName.getName()).isMethod()
                    && superClass.getClassDefinition().getMembers().get(varName.getName()).asMethodDefinition("conversion en MethodeDef impossible", getLocation()).getSignature().equals(sig)) {
                try {
                    if (superClass.getClassDefinition().getMembers().get(varName.getName()).getType().sameType(type1)
                            || type1.asClassType("Type not class type", getLocation()).isSubClassOf(superClass.getClassDefinition().getMembers().get(varName.getName()).getType().asClassType("bruh", getLocation())))
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
                        + this.getLocation().errorOutPut() + ": Signature method conflict in super class", this.getLocation());
            }
        } else{
            name.getClassDefinition().incNumberOfMethods();

        }
        if(if_taken == 0) {
            this.varName.setDefinition(new MethodDefinition(this.type.getType(), getLocation(),
                    sig, name.getClassDefinition().getNumberOfMethods() + 1));
        } else {
            this.varName.setDefinition(new MethodDefinition(this.type.getType(), getLocation(),
                    sig, index_fin));
        }
        this.varName.getMethodDefinition().setLabel(new Label(this.varName.getName().getName()));
        EnvironmentExp envToReturn = new EnvironmentExp(superClass.getClassDefinition().getMembers());

        try {
            envToReturn.declare(this.varName.getName(), this.varName.getMethodDefinition());
        } catch (EnvironmentExp.DoubleDefException ignored) {
        }

        return envToReturn;
    }

    @Override
    protected void verifyDeclMethodPass3(DecacCompiler compiler, EnvironmentExp envExp, AbstractIdentifier name) throws ContextualError {
        Type returnType = this.type.verifyType(compiler);
        this.listParams.classEnvExp = envExp;
        localEnv = this.listParams.verifyListDeclParamPass3(compiler);
        localEnv.setParentEnvironment(envExp);

        this.methodBody.verifyMethodBody(compiler, envExp, localEnv, name, returnType);
    }

    @Override
    public void codeGenDeclMethod(DecacCompiler compiler, String className) {
        List<Line> preInit = new ArrayList<>();
        compiler.getMemory().resetLastGRegister();
        compiler.getLabelFactory().setSuffixCurrentMethod(className+"."+this.varName.getName().getName());
        compiler.addLabel(new Label("code." + compiler.getLabelFactory().getSuffixCurrentMethod()));
        int indexTSTO = compiler.getLineIndex();
        this.listParams.codeGenListDeclParam(compiler, localEnv);

        this.methodBody.codeGenMethodBody(compiler, localEnv);

        if (!type.getType().isVoid()) {
            compiler.getLabelFactory().createTestReturn(compiler);
        }

        compiler.addLabel(new Label("fin." + compiler.getLabelFactory().getSuffixCurrentMethod()));

        if (compiler.getMemory().getLastGRegister() > 1) {
            for (int i = 2; i < compiler.getMemory().getLastGRegister()+1; i++) {
                preInit.add(new Line(new PUSH(Register.getR(i))));
                compiler.getMemory().increaseTSTO();
                compiler.addInstruction(new POP(Register.getR(compiler.getMemory().getLastGRegister()-(i-2))));
            }
        }
        preInit.add(0, new Line(new TSTO(compiler.getMemory().TSTO())));
        preInit.add(1, new Line(new BOV(compiler.getLabelFactory().getStackErrorLabel())));
        compiler.addAllIndex(indexTSTO, preInit);
        compiler.addInstruction(new RTS());
        compiler.getMemory().resetLastGRegister();

    }

}
