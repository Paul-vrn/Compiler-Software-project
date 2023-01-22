package fr.ensimag.pseudocode.ima.instructions;

import fr.ensimag.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ImmediateInteger;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class NEW extends BinaryInstructionDValToReg {

    public NEW(DVal size, GPRegister destination) {
        super(size, destination);
    }

    public NEW(int size, GPRegister op2) {
        super(new ImmediateInteger(size), op2);
    }

}
