package fr.ensimag.pseudocode;

/**
 * Represent a list of operands.
 *
 * @author gl21
 */
public class ListOperand extends Operand {

    private Operand[] operands;

    public ListOperand(Operand... operands) {
        this.operands = operands;
    }
    @Override
    public String toString() {
        String s = "{";
        for (int i = 0; i < operands.length; i++) {
            s += operands[i].toArmString();
            if (i < operands.length - 1) {
                s += ", ";
            }
        }
        s += "}";
        return s;
    }

    public String toArmString() {
        return this.toString();
    }
}
