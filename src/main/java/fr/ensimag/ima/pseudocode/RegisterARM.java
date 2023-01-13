package fr.ensimag.ima.pseudocode;

/**
 * Register operand (including special registers like SP).
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class RegisterARM extends DVal {
    private String name;
    protected RegisterARM(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toArmString() {
        return this.toString();
    }

    /**
     * Global Base register
     */
    public static final RegisterARM FP = new RegisterARM("FP    ");
    /**
     * Local Base register
     */
    public static final RegisterARM IP = new RegisterARM("IP");
    /**
     * Stack Pointer
     */
    public static final RegisterARM SP = new RegisterARM("SP");

    /**
     * Link Register
     */
    public static final RegisterARM LR = new RegisterARM("LR");

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
    /**
     * Convenience shortcut for R[7] Syscall number
     */
    public static final GPRegister R7 = R[7];

    public static int RMAX = 10;

    static private GPRegister[] initRegisters() {
        GPRegister [] res = new GPRegister[10];
        for (int i = 0; i <= 9; i++) {
            res[i] = new GPRegister("R" + i, i);
        }
        return res;
    }

    static private GPRegister[] initFloatRegisters() {
        GPRegister [] res = new GPRegister[32];
        for (int i = 0; i <= 31; i++) {
            res[i] = new GPRegister("S" + i, i);
        }
        return res;
    }
}
