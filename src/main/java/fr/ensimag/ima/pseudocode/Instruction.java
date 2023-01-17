package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;

/**
 * IMA instruction.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public abstract class Instruction {
    protected String getName() {
        return this.getClass().getSimpleName();
    }
    abstract void displayOperands(PrintStream s);
    abstract void armDisplayOperands(PrintStream s);

    void display(PrintStream s, boolean arm) {
        s.print(getName());
        if (arm) {
            armDisplayOperands(s);
        } else {
            displayOperands(s);
        }
    }
}
