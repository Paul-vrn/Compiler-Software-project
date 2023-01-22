package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.UnaryInstruction;

/**
 * .asciz tag
 * @author gl21
 */
public class ASCIZ extends UnaryInstruction {

    public ASCIZ(Operand operand) {
        super(operand);
    }

    @Override
    protected String getName() {
        return ".asciz";
    }
}
