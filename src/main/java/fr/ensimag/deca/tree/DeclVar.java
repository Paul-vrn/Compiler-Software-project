package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 * Variable declaration
 *
 * @author gl21
 * @date 01/01/2023
 */
public class DeclVar extends AbstractDeclVar {
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
                                 EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

        Type varType = this.type.verifyType(compiler);

        /* Verification : type void is forbidden */
        if (this.type.getType().isVoid()) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Type void forbidden", this.getLocation());
        }

        this.varName.setType(varType);
        this.varName.setDefinition(new VariableDefinition(varType, getLocation()));

        this.initialization.verifyInitialization(compiler, varName.getType(), localEnv, currentClass);

        /* Verifies that the variable is not already defined */
        try {
            localEnv.declare(varName.getName(), (ExpDefinition) varName.getDefinition());
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Variable already defined", this.getLocation());
        }

    }


    @Override
    public void decompile(IndentPrintStream s) {
        this.type.decompile(s);
        s.print(" ");
        this.varName.decompile(s);
        if (!(this.initialization instanceof NoInitialization)) {
            this.initialization.decompile(s);
        }
        s.print(";");
        s.println();
    }

    public void codeGenVar(DecacCompiler compiler) {
        this.varName.codeGenDeclVar(compiler);
        this.initialization.codeGenInit(compiler, this.varName);
    }

    public void codeGenField(DecacCompiler compiler, EnvironmentExp localEnvExpr) {
        this.varName.codeGenDeclField(compiler, localEnvExpr);
        this.initialization.codeGenInit(compiler, this.varName);
    }

    public void armCodeGen(DecacCompiler compiler) {
        this.varName.armCodeGenDeclVar(compiler);
        this.initialization.armCodeGenInit(compiler, this.varName);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
