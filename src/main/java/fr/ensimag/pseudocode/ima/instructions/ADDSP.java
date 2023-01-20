package fr.ensimag.pseudocode.ima.instructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.UnaryInstructionImmInt;

/**
 * Add a value to stack pointer.
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class ADDSP extends UnaryInstructionImmInt {

    public ADDSP(ImmediateInteger operand) {
        super(operand);
    }

    public ADDSP(int i) {
        super(i);
    }

}
