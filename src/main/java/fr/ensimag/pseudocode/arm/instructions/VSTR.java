package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.GPRegister;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class VSTR extends BinaryInstruction {
    public VSTR(GPRegister op1, DAddr op2) {
        super(op2, op1);
    }

}
