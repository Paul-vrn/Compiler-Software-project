package fr.ensimag.pseudocode;

/**
 * Operand representing a register indirection with offset, e.g. 42(R3).
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class RegisterOffset extends DAddr {
    public int getOffset() {
        return offset;
    }
    public AbstractRegister getRegister() {
        return register;
    }
    private final int offset;
    private final AbstractRegister register;
    public RegisterOffset(int offset, AbstractRegister register) {
        super();
        this.offset = offset;
        this.register = register;
    }
    @Override
    public String toString() {
        return offset + "(" + register + ")";
    }

    @Override
    public String toArmString() {
        return "[" + register + ", #" + offset + "]";
    }

}
