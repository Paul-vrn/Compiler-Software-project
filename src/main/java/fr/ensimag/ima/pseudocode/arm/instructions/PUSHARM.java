package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.*;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class PUSHARM extends UnaryInstruction {

    public PUSHARM(GPRegister op1) {
        super(new ListOperand(op1));
    }
    public PUSHARM(Operand... operands) {
        super(new ListOperand(operands));
    }
    @Override protected String getName() {
        return "PUSH";
    }


}
