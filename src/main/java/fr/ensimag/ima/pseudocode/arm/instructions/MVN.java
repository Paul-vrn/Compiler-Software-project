package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class MVN extends BinaryInstructionDValToReg {

    public MVN(DVal op1, GPRegister op2) {
        super(op1, op2);
    }

}
