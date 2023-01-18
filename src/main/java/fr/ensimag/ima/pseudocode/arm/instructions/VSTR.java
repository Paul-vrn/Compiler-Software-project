package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class VSTR extends BinaryInstruction {
    public VSTR(GPRegister op1, DAddr op2) {
        super(op2, op1);
    }

}
