package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ListOperand;
import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.UnaryInstruction;

/**
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class VPOP extends UnaryInstruction {

    public VPOP(GPRegister op) {
        super(new ListOperand(op));
    }

    public VPOP(Operand... ops) {
        super(new ListOperand(ops));
    }

    @Override protected String getName() {
        return "POP";
    }

}
