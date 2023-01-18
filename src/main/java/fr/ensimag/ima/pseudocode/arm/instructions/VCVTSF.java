package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Operand;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class VCVTSF extends BinaryInstruction {

    public VCVTSF(GPRegister op1, GPRegister op2) {
        super(op1, op2);
    }

    @Override
    protected String getName() {
        return "VCVT.S32.F32";
    }


}
