package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.Register;

import static fr.ensimag.ima.pseudocode.Register.GB;

public class Memory {
    private int offset;
    private int nbRegister;
    private int nbIfThenElse;
    public Memory(int nbRegister) {
        this.nbRegister = nbRegister;
        this.nbIfThenElse = 0;
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

}
