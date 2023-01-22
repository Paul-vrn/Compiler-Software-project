package fr.ensimag.pseudocode;

import org.apache.commons.lang.Validate;

/**
 * Representation of a label in IMA code. The same structure is used for label
 * declaration (e.g. foo: instruction) or use (e.g. BRA foo).
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class Label extends Operand {

    /**
     * Name of the label.
     * @return name of the label
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Name of the label for arm
     * @return name of the label
     */
    public String toArmString() {
        return name;
    }

    /**
     * Constructor for IMA label
     * @param name name of the label
     */
    public Label(String name) {
        super();
        Validate.isTrue(name.length() <= 1024, "Label name too long, not supported by IMA");
        Validate.isTrue(name.matches("^[a-zA-Z][a-zA-Z0-9_.]*$"), "Invalid label name " + name);
        this.name = name;
    }

    /**
     * Constructor for arm label
     * @param name name of the label
     * @param arm true if the label is for arm
     */
    public Label(String name, boolean arm) {
        super();
        this.name = name;
    }
    private String name;
}
