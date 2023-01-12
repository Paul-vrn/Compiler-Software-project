package fr.ensimag.ima.pseudocode;

/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class IMAProgram extends AbstractCodeGenProgram {

    /**
     * Append the content of program p to the current program. The new program
     * and p may or may not share content with this program, so p should not be
     * used anymore after calling this function.
     */
    public void append(IMAProgram p) {
        lines.addAll(p.lines);
    }
    
}
