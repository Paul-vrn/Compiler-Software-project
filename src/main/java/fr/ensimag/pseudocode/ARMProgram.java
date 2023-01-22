package fr.ensimag.pseudocode;

import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class ARMProgram extends AbstractCodeGenProgram {

    protected final LinkedList<AbstractLine> linesData = new LinkedList<AbstractLine>();

    @Override
    public void addData(AbstractLine line) {
        linesData.add(line);
    }

    @Override
    /**
     * Display the program in a textual form readable by IMA to stream s.
     */
    public void display(PrintStream s) {
        s.println(".data");
        for (AbstractLine l: linesData) {
            l.display(s, true);
        }
        s.println(".text");
        s.println(".global main");
        s.println("main:");
        for (AbstractLine l: lines) {
            l.display(s, true);
        }
    }
}
