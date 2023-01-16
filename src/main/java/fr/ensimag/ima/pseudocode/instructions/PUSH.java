package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class PUSH extends UnaryInstruction {
    public PUSH(RegisterIMA op1) {
        super(op1);
    }
}
