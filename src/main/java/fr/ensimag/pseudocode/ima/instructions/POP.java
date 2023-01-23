package fr.ensimag.pseudocode.ima.instructions;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ListOperand;
import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.UnaryInstruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class POP extends UnaryInstruction {
    public POP(GPRegister op) {
        super(op);
    }
}
