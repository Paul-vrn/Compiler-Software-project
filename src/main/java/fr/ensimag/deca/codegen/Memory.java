package fr.ensimag.deca.codegen;

public class Memory {
    private int globalOffset;
    private int localOffset;
    private int lastGRegister;
    private int TSTO;
    private int currentTSTO;
    private int topOfMethodTable = 0;
    private int armOffset;

    /**
     * Constructeur de la classe Memory
     */
    public Memory() {
        this.TSTO = 0;
        this.currentTSTO = 0;
        this.globalOffset = 1;
        this.localOffset = 0;
        this.lastGRegister = 1;
        this.armOffset = 0;
    }

    public int getLastGRegister() {
        return lastGRegister;
    }

    public void setLastGRegister(int i) {
        this.lastGRegister = Math.max(i, lastGRegister);
    }
    public void resetLastGRegister() {
        this.lastGRegister = 1;
    }
    public int getGlobalOffset() {
        return globalOffset;
    }
    public void increaseGlobalOffset(int i) {
        globalOffset += i;
    }
    public void increaseGlobalOffset() {
        increaseGlobalOffset(1);
    }
    public int getLocalOffset() {
        return localOffset;
    }
    public void increaseLocalOffset(int i) {
        localOffset += i;
    }
    public void increaseLocalOffset() {
        increaseLocalOffset(1);
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
        currentTSTO = 0;
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


    public void increaseTopOfMethodTable() {
        topOfMethodTable++;
    }

    public int getTopOfMethodTable() {
        return topOfMethodTable;
    }


}
