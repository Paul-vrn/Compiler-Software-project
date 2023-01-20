package fr.ensimag.pseudocode.arm.instructions;

import fr.ensimag.pseudocode.Operand;
import fr.ensimag.pseudocode.UnaryInstruction;

import java.io.PrintStream;

/**
 * @author Ensimag
 * @date 01/01/2023
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
