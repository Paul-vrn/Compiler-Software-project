package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.ima.instructions.RTS;
import fr.ensimag.pseudocode.ima.instructions.TSTO;
import fr.ensimag.pseudocode.ima.instructions.PUSH;
import fr.ensimag.pseudocode.ima.instructions.BSR;
import fr.ensimag.pseudocode.ima.instructions.LOAD;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclField extends TreeList<AbstractDeclField> {


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
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, RegisterIMA.LB), RegisterIMA.getR(2)));
        compiler.addInstruction(new PUSH(RegisterIMA.getR(2))); //On push l'objet courrant pour le faire passer dans l'initialisation de sa super classe
        compiler.addInstruction(new BSR(new Label("init." + "Nom de la super class")));
        compiler.addInstruction(new RTS());
    }
}
