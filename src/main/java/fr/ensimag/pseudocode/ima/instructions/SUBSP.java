package fr.ensimag.pseudocode.ima.instructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.UnaryInstructionImmInt;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class SUBSP extends UnaryInstructionImmInt {

    public SUBSP(ImmediateInteger operand) {
        super(operand);
    }

    public SUBSP(int i) {
        super(i);
    }

}
