package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.UnaryInstruction;

/**
 * .float tag
 * @author gl21
 */
public class FLOAT extends UnaryInstruction {

    public FLOAT(Operand operand) {
        super(operand);
    }

    @Override
    protected String getName() {
        return ".float";
    }
}
