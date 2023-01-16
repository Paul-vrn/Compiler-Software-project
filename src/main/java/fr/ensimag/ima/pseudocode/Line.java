package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;
import java.util.Objects;

/**
 * Line of code in an IMA program.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class Line extends AbstractLine {
    public Line(Label label, Instruction instruction, String comment) {
        super();
        checkComment(comment);
        this.label = label;
        this.instruction = instruction;
        this.comment = comment;
    }
    public Line(Label label, Instruction instruction) {
        this(label, instruction, null);
    }
    public Line(Instruction instruction) {
        super();
        this.instruction = instruction;
    }

    public Line(String comment) {
        super();
        checkComment(comment);
        this.comment = comment;
    }

    public Line(Label label) {
        super();
        this.label = label;
    }

    private void checkComment(final String s) {
        if (s == null) {
            return;

        }
        if (s.contains("\n")) {
            throw new IMAInternalError("Comment '" + s + "'contains newline character");
        }
        if (s.contains("\r")) {
            throw new IMAInternalError("Comment '" + s + "'contains carriage return character");
        }
    }
    private Instruction instruction;
    private String comment;
    private Label label;

    @Override
    void display(PrintStream s, boolean arm) {
        boolean tab = false;
        if (label != null) {
            s.print(label);
            s.print(":");
            tab = true;
        }
        if (instruction != null) {
            s.print("\t");
            instruction.display(s, arm);
            tab = true;
        }
        if (comment != null) {
            if (tab) {
                            s.print("\t");
                        }
            s.print(((arm) ? "@ " : "; " )+ comment);
        }
        s.println();
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return Objects.equals(label, line.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instruction, comment, label);
    }
}
