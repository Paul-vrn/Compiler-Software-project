package fr.ensimag.ima.pseudocode;

import fr.ensimag.deca.codegen.LabelFactory;

/**
 * Immediate operand representing a string.
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class ImmediateString extends Operand {
    private String value;

    public ImmediateString(String value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    @Override
    public String toArmString() {
        return this.toString();
    }

    public String getValue() {
        return value;
    }
}
