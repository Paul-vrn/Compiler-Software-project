package fr.ensimag.deca.codegen;

public class Memory {
    private int offset;
    private int armOffset;
    private int TSTO;
    private int currentTSTO;


    /**
     * Constructeur de la classe Memory
     */
    public Memory() {
        this.TSTO = 0;
        this.currentTSTO = 0;
        this.offset = 1;
        this.armOffset = -4;
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
     * return the current offset
     * @return the current offset
     */
    public int getArmOffset() {
        return armOffset;
    }

    /**
     * increase the arm offset by 4
     * @param i : offset to add
     */
    public void increaseArmOffset(int i) {
        armOffset -= i;
    }

    /**
     * @return the TSTO
     */
    public void increaseArmOffset() {
        increaseArmOffset(80);
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

    /**
     * Decrease TSTO value
     */
    public void decreaseTSTO() {
        currentTSTO--;
        TSTO = Math.max(currentTSTO, TSTO);
    }
}
