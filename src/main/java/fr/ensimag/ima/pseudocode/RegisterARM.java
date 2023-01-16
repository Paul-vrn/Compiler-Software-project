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
    private static final GPRegisterIMA[] R = initRegisters();
    /**
     * General Purpose Registers
     */
    public static GPRegisterIMA getR(int i) {
        return R[i];
    }

    private static final GPRegisterIMA[] S = initFloatRegisters();

    public static GPRegisterIMA getS(int i) {
        return S[i];
    }
    /**
     * Convenience shortcut for R[7] Syscall number
     */
    public static final GPRegisterIMA R7 = R[7];

    public static int RMAX = 10;

    static private GPRegisterIMA[] initRegisters() {
        GPRegisterIMA[] res = new GPRegisterIMA[10];
        for (int i = 0; i <= 9; i++) {
            res[i] = new GPRegisterIMA("R" + i, i);
        }
        return res;
    }

    static private GPRegisterIMA[] initFloatRegisters() {
        GPRegisterIMA[] res = new GPRegisterIMA[32];
        for (int i = 0; i <= 31; i++) {
            res[i] = new GPRegisterIMA("S" + i, i);
        }
        return res;
    }
}
