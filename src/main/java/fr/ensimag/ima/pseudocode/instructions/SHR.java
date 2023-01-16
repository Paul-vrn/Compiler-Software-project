package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.GPRegisterIMA;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class SHR extends UnaryInstructionToReg {
    public SHR(GPRegisterIMA op1) {
        super(op1);
    }
}
