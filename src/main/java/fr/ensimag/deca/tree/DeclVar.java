package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
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
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        Type varType = this.type.verifyType(compiler);

        varName.setType(varType);
        this.varName.verifyExpr(compiler, localEnv, currentClass);
        try{
            localEnv.declare(varName.getName(), (ExpDefinition) varName.getDefinition());
        }catch(EnvironmentExp.DoubleDefException e){
            throw new ContextualError("Already defined", this.getLocation());
        }

        this.initialization.verifyInitialization(compiler, varName.getType(), localEnv, currentClass);

    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        this.type.decompile(s);
        s.print(" ");
        this.varName.decompile(s);
        if (!(this.initialization instanceof NoInitialization)){
            this.initialization.decompile(s);
        }
        s.print(";");
        s.println();
    }

    public void codeGen(DecacCompiler compiler) {
        this.varName.codeGenDeclVar(compiler);
        // TODO Auto-generated method stub
        this.initialization.codeGenInit(compiler, this.varName);
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
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
