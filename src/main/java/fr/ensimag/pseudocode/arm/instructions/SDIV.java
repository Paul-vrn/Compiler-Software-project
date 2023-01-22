package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.TernaryInstruction;

/**
 * signed division (integer)
 * @author gl21
 */
public class SDIV extends TernaryInstruction {

        public SDIV(Operand op1, Operand op2, Operand op3) {
            super(op1, op2, op3);
        }

        public SDIV(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
}
