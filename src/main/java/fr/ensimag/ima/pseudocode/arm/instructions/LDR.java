package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class LDR extends BinaryInstructionDValToReg {

    public LDR(DVal op1, GPRegister op2) {
        super(op1, op2);
    }

    public LDR(int i, GPRegister r) {
        this(new ImmediateInteger(i), r);
    }

}
