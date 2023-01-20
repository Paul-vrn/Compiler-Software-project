package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * 
 * @author gl21
 * @date 01/01/2023
 */
public class ListInst extends TreeList<AbstractInst> {

    /**
     * Implements non-terminal "list_inst" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains "env_types" attribute
     * @param localEnv corresponds to "env_exp" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     * @param returnType
     *          corresponds to "return" attribute (void in the main bloc).
     */    
    public void verifyListInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {

        for (AbstractInst i : getList()) {
            i.verifyInst(compiler, localEnv, currentClass, returnType);
        }

        //TODO : not finished ?
    }

    public void codeGenListInst(DecacCompiler compiler) {
        for (AbstractInst i : getList()) {
            i.codeGenInst(compiler);
        }
    }
    public void armCodeGenListInst(DecacCompiler compiler) {
        for (AbstractInst i : getList()) {
            i.armCodeGenInst(compiler);
        }
    }

    public void codeGenIf(DecacCompiler compiler,int p){
        for (AbstractInst i : getList()) {
            if(i instanceof IfThenElse){
                ((IfThenElse)i).codeGenIf(compiler,p);}
            else{
                i.codeGenInst(compiler);
            }
        }
    }
    public void armCodeGenIf(DecacCompiler compiler, int p) {
        for (AbstractInst i : getList()) {
            if(i instanceof IfThenElse){
                ((IfThenElse)i).armCodeGenIf(compiler,p);}
            else{
                i.armCodeGenInst(compiler);
            }
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractInst i : getList()) {
            i.decompileInst(s);
            s.println();
        }
    }
}
