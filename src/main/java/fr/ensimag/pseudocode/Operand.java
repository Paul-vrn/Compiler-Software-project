package fr.ensimag.pseudocode;

/**
 * Operand of an Instruction.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public abstract class Operand {
    /**
     * Return the string representation of the operand.
     * @return the string representation of the operand.
     */
    @Override
    public abstract String toString();

    /**
     * Return the string representation of the operand in ARM assembly.
     * @return the string representation of the operand in ARM assembly.
     */
    public String toArmString() {
        return this.toString();
    }


}
