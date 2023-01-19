package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.tools.SymbolTable;

import java.util.Map;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclField extends TreeList<AbstractDeclField> {

    /**
     * Pass 2
     * @param compiler
     * @param superClass
     * @param name
     * @return
     * @throws ContextualError
     */
    protected EnvironmentExp verifyListDeclFieldPass2(DecacCompiler compiler, AbstractIdentifier superClass, AbstractIdentifier name)
            throws ContextualError {
        EnvironmentExp envExpR = new EnvironmentExp(superClass.getClassDefinition().getMembers());

        for (AbstractDeclField current : this.getList()){
            for(Map.Entry<SymbolTable.Symbol, ExpDefinition> entry :
                    current.verifyDeclFieldPass2(compiler, superClass, name).getDictionary().entrySet()){
                try {
                    envExpR.declare(entry.getKey(), entry.getValue());
                }catch (EnvironmentExp.DoubleDefException e){
                    throw new ContextualError( compiler.displaySourceFile() + ":"
                            + this.getLocation().errorOutPut() + ": Field already defined", this.getLocation());
                }
            }
        }

        return envExpR;
    }

    protected void verifyListDeclFieldPass3(DecacCompiler compiler, EnvironmentExp envExp, AbstractIdentifier name) throws ContextualError {
        for(AbstractDeclField current : this.getList()){
            current.verifyDeclFieldPass3(compiler, envExp, name);
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for ( AbstractDeclField current : this.getList()){
            current.decompile(s);
        }
    }

    public void codeGenClassInitFunctionGenCode(DecacCompiler compiler) {
        Label classInitFunctionName = new Label("init." + "Nom de la class");
        compiler.addLabel(classInitFunctionName);
        compiler.addInstruction(new TSTO(3)); //TSTO de 3 = 1 pour l'argument (this) + 2 pour un appel de fonction
        //ajouter l'instruciton BOV une fois que le code pour le message d'erreur de stack overflow existera
        for(AbstractDeclField current : this.getList()){
            current.getInitialization().codeGenInit(compiler, current.getFieldName());
        }
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.getR(2)));
        compiler.addInstruction(new PUSH(Register.getR(2))); //On push l'objet courrant pour le faire passer dans l'initialisation de sa super classe
        compiler.addInstruction(new BSR(new Label("init." + "Nom de la super class")));
        compiler.addInstruction(new RTS());
    }
}
