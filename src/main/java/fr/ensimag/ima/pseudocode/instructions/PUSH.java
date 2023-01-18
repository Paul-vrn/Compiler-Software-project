package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.*;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class PUSH extends UnaryInstruction {
    public PUSH(RegisterIMA op1) {
        super(op1);
    }

    public PUSH(GPRegister op1) {
        super(op1);
    }
    public PUSH(GPRegister op1, boolean arm){
        super(new ListOperand(op1));
    }
    public PUSH(Operand... operands) {
        super(new ListOperand(operands));
    }
    public PUSH(RegisterARM op1) {
        super(new ListOperand(op1));
    }

}
