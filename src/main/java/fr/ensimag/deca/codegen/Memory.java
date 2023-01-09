package fr.ensimag.deca.codegen;

public class Memory {
    private int offset;
    private int nbIfThenElse;
    private int lastGRegister;
    private int TSTO;


    public Memory() {
        this.TSTO = 0;
        this.nbIfThenElse = 0;
        this.offset = 1;
        this.lastGRegister = 2;
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
