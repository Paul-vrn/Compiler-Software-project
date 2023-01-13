package fr.ensimag.ima.pseudocode;

/**
 * The #null operand.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class NullOperand extends DVal {

    @Override
    public String toString() {
        return "#null";
    }

    @Override
    public String toArmString() {
        return new ImmediateInteger(0).toArmString();
    }
}
