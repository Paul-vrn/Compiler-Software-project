package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

public class LabelFactory {

    private int nbNot;
    private int nbIfThenElse;
    private int nbWhile;
    private int nbAnd;
    private int nbOr;

    private boolean flagOverflowError;
    private boolean flagStackError;
    private boolean flagIOError;
    private boolean flagDivByZeroError;
    private static final Label overflowErrorLabel = new Label("overflow_error");
    private static final Label stackErrorLabel = new Label("stack_error");
    private static final Label ioErrorLabel = new Label("io_error");
    private static final Label DivByZeroErrorLabel = new Label("div_by_zero_error");

    public LabelFactory() {
        this.nbNot = 0;
        this.nbIfThenElse = 0;
        this.nbWhile = 0;
        this.nbAnd = 0;
        this.nbOr = 0;
        this.flagOverflowError = false;
        this.flagStackError = false;
        this.flagIOError = false;
        this.flagDivByZeroError = false;
    }
    public void createErrorSection(DecacCompiler compiler){
        if (flagOverflowError){
            compiler.addLabel(overflowErrorLabel);
            compiler.addInstruction(new WSTR("Error: Overflow during arithmetic operation"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagStackError){
            compiler.addLabel(stackErrorLabel);
            compiler.addInstruction(new WSTR("Error: Stack Overflow"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagIOError){
            compiler.addLabel(ioErrorLabel);
            compiler.addInstruction(new WSTR("Error: Input/Output error"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagDivByZeroError){
            compiler.addLabel(DivByZeroErrorLabel);
            compiler.addInstruction(new WSTR("Error: Division by zero"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }
    public Label createOverflowErrorLabel(){
        flagOverflowError = true;
        return overflowErrorLabel;
    }
    public Label createStackErrorLabel(){
        flagStackError = true;
        return stackErrorLabel;
    }
    public Label createIOErrorLabel(){
        flagIOError = true;
        return ioErrorLabel;
    }

    public Label createDivByZeroErrorLabel(){
        flagDivByZeroError = true;
        return DivByZeroErrorLabel;
    }

    public void createOverFlowErrorCode(DecacCompiler compiler){

    }

    public int nbNot(){
        int i = nbNot;
        nbNot++;
        return i;
    }
    public int nbIfThenElse(){
        int i = nbIfThenElse;
        nbIfThenElse++;
        return i;
    }

    public int getNbIfThenElse() {
        return nbIfThenElse;
    }

    public void setNbIfThenElse(int nbIfThenElse) {
        this.nbIfThenElse = nbIfThenElse;
    }

    public int nbWhile(){
        int i = nbWhile;
        nbWhile++;
        return i;
    }
    public int nbAnd(){
        int i = nbAnd;
        nbAnd++;
        return i;
    }
    public int nbOr(){
        int i = nbOr;
        nbOr++;
        return i;
    }
}
