package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

public class LabelFactory {

    private boolean noCheck;
    private int nbNot;
    private int nbIfThenElse;
    private int nbWhile;
    private int nbAnd;
    private int nbOr;
    private int nbString;
    private boolean flagOverflowError;
    private boolean flagStackError;
    private boolean flagIOError;
    private boolean flagDivByZeroError;
    private static final Label overflowErrorLabel = new Label("overflow_error");
    private static final Label stackErrorLabel = new Label("stack_error");
    private static final Label ioErrorLabel = new Label("io_error");
    private static final Label divByZeroErrorLabel = new Label("div_by_zero_error");

    private static final Label printfLabel = new Label("printf");


    public LabelFactory() {
        this.noCheck = false;
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
    public void setNoCheck(boolean noCheck) {
        this.noCheck = noCheck;
    }
    public void createErrorSection(DecacCompiler compiler){
        if (noCheck)
            return;
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
            compiler.addLabel(divByZeroErrorLabel);
            compiler.addInstruction(new WSTR("Error: Division by zero"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public int nbNot(){
        int i = nbNot;
        nbNot++;
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

    public int nbString(){
        int i = nbString;
        nbString++;
        return i;
    }
    public void createTestDiv0(DecacCompiler compiler, GPRegister r, boolean isInt) {
        if (noCheck)
            return;
        flagDivByZeroError = true;
        compiler.addInstruction(new CMP(
                isInt ? new ImmediateInteger(0) : new ImmediateFloat(0.0f),
                r
        ));
        compiler.addInstruction(new BEQ(divByZeroErrorLabel));
    }

    public void createTestOverflow(DecacCompiler compiler) {
        if (noCheck)
            return;
        flagOverflowError = true;
        compiler.addInstruction(new BOV(overflowErrorLabel));
    }

    public void createTestIo(DecacCompiler compiler){
        if (noCheck)
            return;
        flagIOError = true;
        compiler.addInstruction(new BOV(ioErrorLabel));
    }

    public Label getPrintfLabel() {
        return printfLabel;
    }

}
