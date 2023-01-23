package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ListOperand;
import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.UnaryInstruction;

/**
 * vector push
 * @author gl21
 */
public class VPUSH extends UnaryInstruction {
    public VPUSH(GPRegister op1) {
        super(new ListOperand(op1));
    }
}
