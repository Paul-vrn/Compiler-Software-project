package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Operand;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class VCVTDS extends BinaryInstruction {

    public VCVTDS(Operand op1, GPRegister op2) {
        super(op1, op2);
    }

    @Override
    protected String getName() {
        return "VCVT.F64.F32";
    }


}
