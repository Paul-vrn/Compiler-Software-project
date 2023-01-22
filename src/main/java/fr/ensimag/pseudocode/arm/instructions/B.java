package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BranchInstruction;
import fr.ensimag.pseudocode.Label;

/**
 * Branch instruction
 * @author gl21
 */
public class B extends BranchInstruction {

    public B(Label op) {
        super(op);
    }

}
