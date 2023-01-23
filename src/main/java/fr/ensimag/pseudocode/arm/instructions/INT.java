package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.UnaryInstruction;

import java.io.PrintStream;

/**
 * .int tag
 * @author gl21
 */
public class INT extends UnaryInstruction {

    public INT(Operand operand) {
        super(operand);
    }

    @Override
    protected String getName() {
        return ".int";
    }

    @Override
    protected void armDisplayOperands(PrintStream s) {
        s.print(" ");
        s.print(getOperand().toArmString().split("#")[1]);
    }
}
