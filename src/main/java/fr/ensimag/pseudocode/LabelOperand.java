package fr.ensimag.pseudocode;

import org.apache.commons.lang.Validate;

/**
 * Label used as operand
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class LabelOperand extends DVal {
    public Label getLabel() {
        return label;
    }

    private Label label;
    
    public LabelOperand(Label label) {
        super();
        Validate.notNull(label);
        this.label = label;
    }

    @Override
    public String toString() {
        return label.toString();
    }

    @Override
    public String toArmString() {
        return "="+label.toArmString();
    }
}
