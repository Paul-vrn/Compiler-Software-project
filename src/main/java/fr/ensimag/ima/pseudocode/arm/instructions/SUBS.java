package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Operand;
import fr.ensimag.ima.pseudocode.TernaryInstruction;

public class SUBS extends TernaryInstruction {

        public SUBS(Operand op1, Operand op2, Operand op3) {
            super(op1, op2, op3);
        }

        public SUBS(DVal op1, GPRegister op2) {
        super(op1, op2);
    }


    @Override
        protected String getName() {
            return "SUBS";
        }

}

