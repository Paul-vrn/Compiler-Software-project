package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.TernaryInstruction;


/**
 * reverse subtract
 * @author gl21
 */
public class RSB extends TernaryInstruction {

    public RSB(DVal op1, GPRegister op2) {
        super(op1, op2);
    }

}
