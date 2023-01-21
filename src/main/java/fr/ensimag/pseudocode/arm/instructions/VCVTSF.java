package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.GPRegister;

/**
 * int32 <-- float
 * @author Paul Vernin
 * @date 01/01/2023
 */
public class VCVTSF extends BinaryInstruction {

    /**
     * VCVT.S32.F32
     * @param op1 int32
     * @param op2 float
     */
    public VCVTSF(GPRegister op1, GPRegister op2) {
        super(op1, op2);
    }

    @Override
    protected String getName() {
        return "VCVT.S32.F32";
    }


}
