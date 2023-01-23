package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.GPRegister;
import fr.ensimag.pseudocode.ListOperand;
import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.UnaryInstruction;


/**
 * vector pop
 * @author gl21
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
