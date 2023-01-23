package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.BinaryInstruction;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Operand;

/**
 * Load vector register
 * @author gl21
 */
public class VLDR extends BinaryInstruction {

    public VLDR(Operand op1, GPRegister op2) {
        super(op1, op2);
    }

    public VLDR(int i, GPRegister r) {
        this(new ImmediateInteger(i), r);
    }

}
