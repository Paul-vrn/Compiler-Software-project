package fr.ensimag.pseudocode;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Base class for instructions with 2 operands.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class BinaryInstruction extends Instruction {
    private Operand operand1, operand2;

    @Override
    void displayOperands(PrintStream s) {
        s.print(" ");
        s.print(operand1);
        s.print(", ");
        s.print(operand2);
    }

    @Override
    void armDisplayOperands(PrintStream s) {
        s.print(" ");
        s.print(operand2.toArmString());
        s.print(", ");
        s.print(operand1.toArmString());
    }

    protected BinaryInstruction(Operand op1, Operand op2) {
        Validate.notNull(op1);
        Validate.notNull(op2);
        this.operand1 = op1;
        this.operand2 = op2;
    }
}
