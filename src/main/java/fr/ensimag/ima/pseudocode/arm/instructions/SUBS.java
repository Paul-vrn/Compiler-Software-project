package fr.ensimag.ima.pseudocode.arm.instructions;

import fr.ensimag.ima.pseudocode.Operand;
import fr.ensimag.ima.pseudocode.TernaryInstruction;

public class SUBS extends TernaryInstruction {

        public SUBS(Operand op1, Operand op2, Operand op3) {
            super(op1, op2, op3);
        }

        @Override
        protected String getName() {
            return "SUBS";
        }

}

