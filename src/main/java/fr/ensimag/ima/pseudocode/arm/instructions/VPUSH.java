package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ListOperand;
import fr.ensimag.ima.pseudocode.Operand;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class VPUSH extends UnaryInstruction {

    public VPUSH(GPRegister op1) {
        super(new ListOperand(op1));
    }
    public VPUSH(Operand... operands) {
        super(new ListOperand(operands));
    }
}
