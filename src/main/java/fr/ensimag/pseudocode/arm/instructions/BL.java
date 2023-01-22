package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * Branch with link instruction
 * @author gl21
 */
public class BL extends BranchInstruction {

    public BL(Label op) {
        super(op);
    }

}
