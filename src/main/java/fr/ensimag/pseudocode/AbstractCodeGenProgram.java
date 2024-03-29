package fr.ensimag.pseudocode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class AbstractCodeGenProgram {

    protected final LinkedList<AbstractLine> lines = new LinkedList<AbstractLine>();

    public void add(AbstractLine line) {
        lines.add(line);
    }

    public void addComment(String s) {
        lines.add(new Line(s));
    }

    public void addLabel(Label l) {
        lines.add(new Line(l));
    }

    public void addInstruction(Instruction i) {
        lines.add(new Line(i));
    }

    public void addInstruction(Instruction i, String s) {
        lines.add(new Line(null, i, s));
    }

    public void addData(AbstractLine l) {};
    /**
     * Add a line at the front of the program.
     */
    public void addFirst(Line l) {
        lines.addFirst(l);
    }

    public void addFirst(Label l) { lines.addFirst(new Line(l));}
    /**
     * Display the program in a textual form readable by IMA to stream s.
     */
    public void display(PrintStream s) {
        for (AbstractLine l: lines) {
            l.display(s, false);
        }
    }

    /**
     * Return the program in a textual form readable by IMA as a String.
     */
    public String display() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream s = new PrintStream(out);
        display(s);
        return out.toString();
    }

    public void addFirst(Instruction i) {
        addFirst(new Line(i));
    }

    public void addFirst(Instruction i, String comment) {
        addFirst(new Line(null, i, comment));
    }

    public int getLastIndex(){
        return lines.size();
    }

    public void addIndex(int i, Line l) {
        lines.add(i, l);
    }

    public void addIndex(int i, Instruction inst) {
        addIndex(i, new Line(inst));
    }

    public void append(AbstractCodeGenProgram p) {
        lines.addAll(p.lines);
    }


    public void addAllIndex(int i, List<Line> l) {
        lines.addAll(i, l);
    }
}
