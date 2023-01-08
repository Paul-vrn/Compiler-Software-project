package fr.ensimag.ima.pseudocode;

/**
 * Register operand (including special registers like SP).
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class Register extends DVal {
    private String name;
    protected Register(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Global Base register
     */
    public static final Register GB = new Register("GB");
    /**
     * Local Base register
     */
    public static final Register LB = new Register("LB");
    /**
     * Stack Pointer
     */
    public static final Register SP = new Register("SP");

    /**
     * General Purpose Registers. Array is private because Java arrays cannot be
     * made immutable, use getR(i) to access it.
     */
    private static GPRegister[] R;
    /**
     * General Purpose Registers
     */
    public static GPRegister getR(int i) {
        return R[i];
    }
    /**
     * Convenience shortcut for R[0]
     */
    public static final GPRegister R0 = R[0];
    /**
     * Convenience shortcut for R[1]
     */
    public static final GPRegister R1 = R[1];

    public static int RMAX;

    public static void initRegisters() {
        R = new GPRegister[RMAX];
        for (int i = 0; i <= RMAX-1; i++) {
            R[i] = new GPRegister("R" + i, i);
        }
    }
}
