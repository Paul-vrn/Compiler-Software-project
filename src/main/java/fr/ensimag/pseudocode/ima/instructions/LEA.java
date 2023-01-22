package fr.ensimag.pseudocode.ima.instructions;

import fr.ensimag.pseudocode.BinaryInstructionDAddrToReg;
import fr.ensimag.pseudocode.DAddr;
import fr.ensimag.pseudocode.GPRegister;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class LEA extends BinaryInstructionDAddrToReg {

    public LEA(DAddr op1, GPRegister op2) {
        super(op1, op2);
    }

}
