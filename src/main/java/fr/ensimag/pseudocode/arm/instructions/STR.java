package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.GPRegister;


/**
 * Store Register
 * @author gl21
 */
public class STR extends BinaryInstruction {
    public STR(GPRegister op1, DAddr op2) {
        super(op2, op1);
    }

}
