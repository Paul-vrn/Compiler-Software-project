package fr.ensimag.deca.codegen;

public class Memory {
    private int offset;
    private int lastGRegister;
    private int TSTO;
    private int currentTSTO;


    public Memory() {
        this.TSTO = 0;
        this.currentTSTO = 0;
        this.offset = 1;
        this.lastGRegister = 2;
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
        currentTSTO++;
        TSTO = Math.max(currentTSTO, TSTO);
    }

    public void decreaseTSTO() {
        currentTSTO--;
        TSTO = Math.max(currentTSTO, TSTO);
    }
}
