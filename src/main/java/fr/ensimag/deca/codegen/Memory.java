package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

import static fr.ensimag.ima.pseudocode.Register.GB;

public class Memory {
    private int offset;
    private int nbRegister;
    private int nbIfThenElse;
    private int lastGRegister;
    private int nbNot;
    private boolean flagOverflowError = false;
    private boolean flagStackError = false;
    private boolean flagIOError = false;

    public Memory(int nbRegister) {
        this.nbRegister = nbRegister;
        this.nbIfThenElse = 0;
        this.offset = 1;
        this.lastGRegister = 2;
    }
    public Memory(){
        this(15);
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
            compiler.addLabel(new Label("overflow_error"));
            compiler.addInstruction(new WSTR("Error: Overflow during arithmetic operation"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagStackError){
            compiler.addLabel(new Label("stack_error"));
            compiler.addInstruction(new WSTR("Error: Stack Overflow"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagIOError){
            compiler.addLabel(new Label("io_error"));
            compiler.addInstruction(new WSTR("Error: Input/Output error"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }
}
