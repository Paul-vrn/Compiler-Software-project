package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.*;

import java.io.PrintStream;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class STR extends BinaryInstruction {
    public STR(GPRegister op1, DAddr op2) {
        super(op2, op1);
    }

}
