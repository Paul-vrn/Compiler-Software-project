package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ListOperand;
import fr.ensimag.ima.pseudocode.Operand;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

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
