package fr.ensimag.deca.codegen;

/**
 * Memory class
 */
public class Memory {
    private int globalOffset;
    private int localOffset;
    private int lastGRegister;
    private int TSTO;
    private int currentTSTO;
    private int saveTSTO;
    private int saveCurrentTSTO;
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
        this.saveTSTO = 0;
        this.saveCurrentTSTO = 0;
    }

    /**
     * Return the number of the last GRegister
     * @return the number of the last GRegister
     */
    public int getLastGRegister() {
        return lastGRegister;
    }

    /**
     * Set the last GRegister
     * @param i : the last GRegister
     */
    public void setLastGRegister(int i) {
        this.lastGRegister = Math.max(i, lastGRegister);
    }

    /**
     * Reset the number of the last GRegister
     */
    public void resetLastGRegister() {
        this.lastGRegister = 1;
    }

    /**
     * Return the global offset
     * @return the global offset
     */
    public int getGlobalOffset() {
        return globalOffset;
    }

    /**
     * Increase global offset by i
     * @param i : offset to add
     */
    public void increaseGlobalOffset(int i) {
        globalOffset += i;
    }

    /**
     * Increase global offset by 1
     */
    public void increaseGlobalOffset() {
        increaseGlobalOffset(1);
    }

    /**
     * Return the current offset
     * @return the current offset
     */
    public int getLocalOffset() {
        return localOffset;
    }

    /**
     * Increase local offset by i
     * @param i : offset to add
     */
    public void increaseLocalOffset(int i) {
        localOffset += i;
    }

    /**
     * Increase local offset by 1
     */
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
     * Increase TSTO value by i
     * @param i : value to add
     */
    public void increaseTSTO(int i) {
        currentTSTO += i;
        TSTO = Math.max(currentTSTO, TSTO);
    }
    /**
     * Decrease TSTO value
     */
    public void decreaseTSTO() {
        currentTSTO--;
        TSTO = Math.max(currentTSTO, TSTO);
    }

    /**
     * Decrease TSTO value by i
     * @param i : value to add
     */
    public void decreaseTSTO(int i) {
        currentTSTO -= i;
        TSTO = Math.max(currentTSTO, TSTO);
    }

    /**
     * Save TSTO value
     */
    public void saveTSTO() {
        saveTSTO = TSTO;
        saveCurrentTSTO = currentTSTO;
    }

    /**
     * Restore TSTO value
     */
    public void restoreTSTO() {
        TSTO = saveTSTO;
        currentTSTO = saveCurrentTSTO;
    }
}
