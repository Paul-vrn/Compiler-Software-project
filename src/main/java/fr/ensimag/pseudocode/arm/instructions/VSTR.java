package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.GPRegister;

/**
 * Store vector register
 * @author gl21
 */
public class VSTR extends BinaryInstruction {
    public VSTR(GPRegister op1, DAddr op2) {
        super(op2, op1);
    }

}
