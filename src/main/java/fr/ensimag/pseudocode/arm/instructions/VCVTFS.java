package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.GPRegister;

/**
 * Convert a signed integer to a float
 * @author gl21
 */
public class VCVTFS extends BinaryInstruction {

    /**
     * VCVT.F32.S32
     * @param op1 float
     * @param op2 int32
     */
    public VCVTFS(GPRegister op1, GPRegister op2) {
        super(op1, op2);
    }

    @Override
    protected String getName() {
        return "VCVT.F32.S32";
    }


}
