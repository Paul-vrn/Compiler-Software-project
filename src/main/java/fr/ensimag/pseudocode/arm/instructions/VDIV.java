package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.TernaryInstruction;

/**
 * vector division
 * @author gl21
 */
public class VDIV extends TernaryInstruction {

    public VDIV(Operand op1, Operand op2, Operand op3) {
        super(op1, op2, op3);
    }

    @Override protected String getName() {
        return "VDIV.F32";
    }

}
