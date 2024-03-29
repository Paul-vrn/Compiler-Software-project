package fr.ensimag.pseudocode;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Instruction with a single operand.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public abstract class UnaryInstruction extends Instruction {
    private Operand operand;

    @Override
    void displayOperands(PrintStream s) {
        s.print(" ");
        s.print(operand);
    }

    @Override
    protected void armDisplayOperands(PrintStream s) {
        s.print(" ");
        s.print(operand.toArmString());
    }
    protected UnaryInstruction(Operand operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }

    public Operand getOperand() {
        return operand;
    }

}
