package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of declarations (e.g. int x; float y,z).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclVar current : this.getList()) {
            current.decompile(s);
        }
    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler     contains the "env_types" attribute
     * @param localEnv     its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *                     in precondition, its "current" dictionary corresponds to
     *                     the "env_exp" attribute
     *                     in postcondition, its "current" dictionary corresponds to
     *                     the "env_exp_r" attribute
     * @param currentClass corresponds to "class" attribute (null in the main bloc).
     */
    void verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
                                ClassDefinition currentClass) throws ContextualError {

        for (AbstractDeclVar current : this.getList()) {
            current.verifyDeclVar(compiler, localEnv, currentClass);
        }
    }

    protected void codeGenListDeclVar(DecacCompiler compiler) {
        for (AbstractDeclVar current : this.getList()) {
            current.codeGenVar(compiler);
            compiler.getMemory().increaseTSTO();
        }
    }

    public void codeGenListDeclField(DecacCompiler compiler, EnvironmentExp localEnvExpr) {
        for (AbstractDeclVar current : this.getList()) {
            current.codeGenField(compiler, localEnvExpr);
            compiler.getMemory().increaseTSTO();
        }
    }

    protected void armCodeGenListDeclVar(DecacCompiler compiler) {
        for (AbstractDeclVar current : this.getList()) {
            current.armCodeGen(compiler);
        }
    }
}
