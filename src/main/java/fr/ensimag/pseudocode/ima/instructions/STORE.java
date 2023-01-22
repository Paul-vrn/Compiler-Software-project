package fr.ensimag.pseudocode.ima.instructions;

import fr.ensimag.pseudocode.AbstractRegister;
import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.DAddr;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class STORE extends BinaryInstruction {
    public STORE(AbstractRegister op1, DAddr op2) {
        super(op1, op2);
    }
}
