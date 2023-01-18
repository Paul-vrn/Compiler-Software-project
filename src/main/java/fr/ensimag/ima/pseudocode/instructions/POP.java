package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.*;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class POP extends UnaryInstruction {

    public POP(GPRegister op) {
        super(op);
    }

    public POP(Operand... ops) {
        super(new ListOperand(ops));
    }
}
