package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.ima.instructions.POP;
import fr.ensimag.pseudocode.ima.instructions.PUSH;

/**
 * Variable declaration
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class AbstractMethodBody extends Tree {

    /**
     * Implements non-terminal "decl_var" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler     contains "env_types" attribute
     * @param localEnv     its "parentEnvironment" corresponds to the "env_exp_sup" attribute
     *                     in precondition, its "current" dictionary corresponds to
     *                     the "env_exp" attribute
     *                     in postcondition, its "current" dictionary corresponds to
     *                     the synthetized attribute
     * @param currentClass corresponds to the "class" attribute (null in the main bloc).
     */
    protected abstract void verifyMethodBody(DecacCompiler compiler,
                                          EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    public abstract void codeGen(DecacCompiler compiler);

    public static void contextSave(DecacCompiler compiler) {
        //on sauvegarde tout les registres sauf R0 qui contiendra le résultat, les registres SP et LB sont handled par les fonctiosn d'appel
        //i<15 peut être à changer en i<RMAX
        for(int i = 1; i<15; i++) {
            compiler.addInstruction(new PUSH(RegisterIMA.getR(i)));
        }
    }

    public static void contextRestore(DecacCompiler compiler) {
        //i<15 peut être à changer en i<RMAX
        for(int i = 14; i>0; i--) {
            compiler.addInstruction(new POP(RegisterIMA.getR(i)));
        }
    }

}