package fr.ensimag.ima.pseudocode;

/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class ARMProgram extends AbstractCodeGenProgram {

    public void append(ARMProgram p) {
        lines.addAll(p.lines);
    }

}
