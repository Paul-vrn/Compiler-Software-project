package fr.ensimag.pseudocode;

import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Base class for instructions with 3 operands.
 *
 * @author gl21
 */
public class TernaryInstruction extends Instruction {
    private Operand operand1, operand2, operand3;

    @Override
    void displayOperands(PrintStream s) {
        throw new IMAInternalError("TernaryInstruction.displayOperands() should not be called in IMA");
    }

    void armDisplayOperands(PrintStream s) {
        s.print(" ");
        if (operand3 != null) {
            s.print(operand3.toArmString());
            s.print(", ");
        }
        s.print(operand2.toArmString());
        s.print(", ");
        s.print(operand1.toArmString());
    }
    protected TernaryInstruction(Operand op1, Operand op2, Operand op3) {
        Validate.notNull(op1);
        Validate.notNull(op2);
        Validate.notNull(op3);
        this.operand1 = op1;
        this.operand2 = op2;
        this.operand3 = op3;
    }

    protected TernaryInstruction(Operand op1, Operand op2) {
        Validate.notNull(op1);
        Validate.notNull(op2);
        this.operand1 = op1;
        this.operand2 = op2;
    }
}
