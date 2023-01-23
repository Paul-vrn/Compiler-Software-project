package fr.ensimag.pseudocode;

import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Program for ARM architecture.
 *
 * @author gl21
 */
public class ARMProgram extends AbstractCodeGenProgram {

    /**
     * Data section
     */
    protected final LinkedList<AbstractLine> linesData = new LinkedList<AbstractLine>();

    /**
     * add line to the data section
     * @param line
     */
    @Override
    public void addData(AbstractLine line) {
        linesData.add(line);
    }

    /**
     * Display the program in a textual form readable by IMA to stream s.
     */
    @Override
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
