package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Operand;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class LDR extends BinaryInstruction {

    public LDR(Operand op1, GPRegister op2) {
        super(op1, op2);
    }

    public LDR(int i, GPRegister r) {
        this(new ImmediateInteger(i), r);
    }

}
