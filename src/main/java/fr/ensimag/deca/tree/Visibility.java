package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

/**
 * Visibility of a field.
 *
 * @author gl21
 * @date 01/01/2023
 */

public enum Visibility {
    PUBLIC,
    PROTECTED;

    public void prettyPrintVisibility(PrintStream s, String prefix) {
        s.print(prefix);
        switch (this) {
            case PUBLIC:
                s.println("[visibility=PUBLIC]");
                break;
            case PROTECTED:
                s.println("[visibility=PROTECTED]");
                break;
        }
    }

    public void decompile(IndentPrintStream s) {
        switch (this) {
            case PUBLIC:
                s.print("public");
                break;
            case PROTECTED:
                s.print("protected");
                break;
        }
    }
}
