package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

public class Memory {
    private int offset;
    private int nbIfThenElse;
    private int lastGRegister;
    private int TSTO;
    private boolean flagOverflowError = false;
    private boolean flagStackError = false;
    private boolean flagIOError = false;
    private Label overflowErrorLabel;
    private Label stackErrorLabel;
    private Label ioErrorLabel;

    public Memory() {
        this.TSTO = 0;
        this.nbIfThenElse = 0;
        this.offset = 1;
        this.lastGRegister = 2;
        this.overflowErrorLabel = new Label("overflow_error");
        this.stackErrorLabel = new Label("stack_error");
        this.ioErrorLabel = new Label("io_error");
    }

    public int getNbIfThenElse() {
        return nbIfThenElse;
    }
    public void increaseNbIfThenElse() {
        nbIfThenElse++;
    }

    public int getOffset() {
        return offset;
    }
    public void increaseOffset(int i) {
        offset += i;
    }
    public void increaseOffset() {
        increaseOffset(1);
    }

    public int getLastGRegister() {
        return lastGRegister;
    }
    public void setLastGRegister(int lastGRegister) {
        this.lastGRegister = lastGRegister;
    }

    public void generateErrorCode(DecacCompiler compiler){
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
    }
    public Label getOverflowErrorLabel(){
        flagOverflowError = true;
        return overflowErrorLabel;
    }
    public Label getStackErrorLabel(){
        flagStackError = true;
        return stackErrorLabel;
    }
    public Label getIOErrorLabel(){
        flagIOError = true;
        return ioErrorLabel;
    }


    /**
     * Return TSTO value and reset it afterwards
     * @return int TSTO
     */
    public int TSTO() {
        int i = TSTO;
        TSTO = 0;
        return i;
    }

    /**
     * Increase TSTO value
     */
    public void increaseTSTO() {
        TSTO++;
    }
}
