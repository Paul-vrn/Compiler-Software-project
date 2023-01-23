package fr.ensimag.pseudocode;

/**
 * Register ARM
 * 
 * @author gl21
 */
public class RegisterARM extends AbstractRegister {

    /**
     * Constructor for arm registers
     * @param name name of the register
     */
    protected RegisterARM(String name) {super(name);}

    @Override
    public String toString() {
        return name;
    }

    /**
     * General Purpose Registers. Array is private because Java arrays cannot be
     * made immutable, use getR(i) to access it.
     */
    private static final GPRegister[] R = initRegisters();

    /**
     * Getter for general purpose registers
     * @param i index of the register
     * @return general purpose register R[i]
     */
    public static GPRegister getR(int i) {
        return R[i];
    }

    /**
     * Float registers
     */
    private static final GPRegister[] S = initFloatRegisters();


    /**
     * Getter for float registers
     * @param i index of the register
     * @return float register S[i]
     */
    public static GPRegister getS(int i) {
        return S[i];
    }

    /**
     * Double registers
     */
    private static final GPRegister[] D = initDoubleRegisters();

    /**
     * Getter for double registers
     * @param i index of the register
     * @return double register D[i]
     */
    public static GPRegister getD(int i) {
        return D[i];
    }
    /**
     * Convenience shortcut for R[7] Syscall number
     */
    public static final GPRegister R7 = R[7];

    /**
     * Frame pointer register
     */
    public static final GPRegister FP = R[11];

    /**
     * Intra procedural register
     */
    public static final GPRegister IP = R[12];

    /**
     * Stack pointer register
     */
    public static final GPRegister SP = R[13];

    /**
     * Link register
     */
    public static final GPRegister LR = R[14];

    /**
     * Program counter register
     */
    public static final GPRegister PC = R[15];

    /**
     * Maximum number of general purpose registers
     */
    public static int RMAX = 10;

    /**
     * Maximum number of float registers
     */
    public static int SMAX = 31;

    /**
     * Initializer for general purpose registers
     * @return array of general purpose registers
     */
    static private GPRegister[] initRegisters() {
        GPRegister[] res = new GPRegister[16];
        for (int i = 0; i <= 10; i++) {
            res[i] = new GPRegister("R" + i, i);
        }
        res[11] = new GPRegister("FP", 11);
        res[12] = new GPRegister("IP", 12);
        res[13] = new GPRegister("SP", 13);
        res[14] = new GPRegister("LR", 14);
        res[15] = new GPRegister("PC", 15);
        return res;
    }

    /**
     * Initializer for float registers
     * @return array of float registers
     */
    static private GPRegister[] initFloatRegisters() {
        GPRegister[] res = new GPRegister[32];
        for (int i = 0; i <= 31; i++) {
            res[i] = new GPRegister("S" + i, i);
        }
        return res;
    }

    /**
     * Initializer for double registers
     * @return array of double registers
     */
    static private GPRegister[] initDoubleRegisters() {
        GPRegister[] res = new GPRegister[16];
        for (int i = 0; i <= 15; i++) {
            res[i] = new GPRegister("D" + i, i);
        }
        return res;
    }
}
