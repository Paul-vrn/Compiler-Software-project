package fr.ensimag.pseudocode.ima.instructions;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.UnaryInstructionImmInt;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class TSTO extends UnaryInstructionImmInt {
    public TSTO(ImmediateInteger i) {
        super(i);
    }

    public TSTO(int i) {
        super(i);
    }
}
