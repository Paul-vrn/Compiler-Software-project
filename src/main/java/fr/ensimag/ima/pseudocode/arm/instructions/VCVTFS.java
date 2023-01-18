package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * float <-- int32
 * @author Paul Vernin
 * @date 01/01/2023
 */
public class VCVTFS extends BinaryInstruction {

    public VCVTFS(GPRegister op1, GPRegister op2) {
        super(op1, op2);
    }

    @Override
    protected String getName() {
        return "VCVT.F32.S32";
    }


}
