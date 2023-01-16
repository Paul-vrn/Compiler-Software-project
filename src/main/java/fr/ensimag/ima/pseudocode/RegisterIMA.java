package fr.ensimag.ima.pseudocode;

/**
 * Register operand (including special registers like SP).
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class RegisterIMA extends AbstractRegister {
    protected RegisterIMA(String name) {
        super(name);
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
    private static final GPRegister[] R = initRegisters();


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

    public static int RMAX = 16;

    static private GPRegister[] initRegisters() {
        GPRegister[] res = new GPRegister[16];
        for (int i = 0; i <= 15; i++) {
            res[i] = new GPRegister("R" + i, i);
        }
        return res;
    }

}
