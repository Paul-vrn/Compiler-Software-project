package fr.ensimag.ima.pseudocode;

/**
 * Register operand (including special registers like SP).
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class RegisterIMA extends DVal {
    private String name;
    protected RegisterIMA(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toArmString() {
        return this.toString();
    }

    /**
     * Global Base register
     */
    public static final RegisterIMA GB = new RegisterIMA("GB");
    /**
     * Local Base register
     */
    public static final RegisterIMA LB = new RegisterIMA("LB");
    /**
     * Stack Pointer
     */
    public static final RegisterIMA SP = new RegisterIMA("SP");

    /**
     * General Purpose Registers. Array is private because Java arrays cannot be
     * made immutable, use getR(i) to access it.
     */
    private static final GPRegisterIMA[] R = initRegisters();
    /**
     * General Purpose Registers
     */
    public static GPRegisterIMA getR(int i) {
        return R[i];
    }
    /**
     * Convenience shortcut for R[0]
     */
    public static final GPRegisterIMA R0 = R[0];
    /**
     * Convenience shortcut for R[1]
     */
    public static final GPRegisterIMA R1 = R[1];

    public static int RMAX = 16;

    static private GPRegisterIMA[] initRegisters() {
        GPRegisterIMA[] res = new GPRegisterIMA[16];
        for (int i = 0; i <= 15; i++) {
            res[i] = new GPRegisterIMA("R" + i, i);
        }
        return res;
    }
}
