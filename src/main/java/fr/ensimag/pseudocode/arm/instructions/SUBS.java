package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.TernaryInstruction;

/**
 * subtraction with sign (integer)
 * @author gl21
 */
public class SUBS extends TernaryInstruction {
    public SUBS(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
    @Override protected String getName() {
            return "SUBS";
        }
}

