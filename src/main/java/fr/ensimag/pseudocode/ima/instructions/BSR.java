package fr.ensimag.pseudocode.ima.instructions;

import fr.ensimag.pseudocode.DVal;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.LabelOperand;
import fr.ensimag.pseudocode.UnaryInstruction;

/**
 * @author Ensimag
 * @date 01/01/2023
 */
public class BSR extends UnaryInstruction {

    public BSR(DVal operand) {
        super(operand);
    }
    
    public BSR(Label target) {
        super(new LabelOperand(target));
    }

}
