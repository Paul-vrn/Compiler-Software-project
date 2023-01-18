package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.AbstractRegister;
import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.RegisterIMA;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class STORE extends BinaryInstruction {
    public STORE(AbstractRegister op1, DAddr op2) {
        super(op1, op2);
    }
}
