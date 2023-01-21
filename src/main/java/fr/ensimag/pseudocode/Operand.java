package fr.ensimag.pseudocode;

/**
 * Operand of an IMA Instruction.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public abstract class Operand {
    @Override
    public abstract String toString();

    public String toArmString() {
        return this.toString();
    }


}
