package fr.ensimag.pseudocode;

/**
 * Register operand (including special registers like SP).
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class RegisterARM extends AbstractRegister {

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
     * General Purpose Registers
     */
    public static GPRegister getR(int i) {
        return R[i];
    }

    private static final GPRegister[] S = initFloatRegisters();

    public static GPRegister getS(int i) {
        return S[i];
    }

    private static final GPRegister[] D = initDoubleRegisters();

    public static GPRegister getD(int i) {
        return D[i];
    }
    /**
     * Convenience shortcut for R[7] Syscall number
     */
    public static final GPRegister R7 = R[7];

    /**
     * équivalent de LB
     */
    public static final GPRegister FP = R[11];

    public static final GPRegister IP = R[12];

    public static final GPRegister SP = R[13];

    public static final GPRegister LR = R[14];

    public static final GPRegister PC = R[15];

    public static int RMAX = 10;

    public static int SMAX = 31;

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

    static private GPRegister[] initFloatRegisters() {
        GPRegister[] res = new GPRegister[32];
        for (int i = 0; i <= 31; i++) {
            res[i] = new GPRegister("S" + i, i);
        }
        return res;
    }

    static private GPRegister[] initDoubleRegisters() {
        GPRegister[] res = new GPRegister[16];
        for (int i = 0; i <= 15; i++) {
            res[i] = new GPRegister("D" + i, i);
        }
        return res;
    }
}
