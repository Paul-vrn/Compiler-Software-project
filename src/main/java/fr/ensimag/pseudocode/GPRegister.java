package fr.ensimag.pseudocode;

/**
 * General Purpose Register operand (R0, R1, ... R15).
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class GPRegister extends AbstractRegister {
    /**
     * @return the number of the register, e.g. 12 for R12.
     */
    public int getNumber() {
        return number;
    }

    private int number;

    public GPRegister(String name, int number) {
        super(name);
        this.number = number;
    }
}
