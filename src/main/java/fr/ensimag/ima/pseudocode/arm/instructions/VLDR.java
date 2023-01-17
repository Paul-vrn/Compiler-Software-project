package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.*;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class VLDR extends BinaryInstruction {

    public VLDR(Operand op1, GPRegister op2) {
        super(op1, op2);
    }

    public VLDR(int i, GPRegister r) {
        this(new ImmediateInteger(i), r);
    }

}
