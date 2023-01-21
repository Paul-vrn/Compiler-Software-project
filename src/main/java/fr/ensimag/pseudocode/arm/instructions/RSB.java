package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.TernaryInstruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class RSB extends TernaryInstruction {

    public RSB(DVal op1, GPRegister op2) {
        super(op1, op2);
    }

}
