package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.TernaryInstruction;

/**
 * vector addition
 * @author gl21
 */
public class VADD extends TernaryInstruction {
    public VADD(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
    @Override protected String getName() {
        return "VADD.F32";
    }

}
