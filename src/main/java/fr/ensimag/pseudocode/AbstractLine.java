package fr.ensimag.pseudocode;

import java.io.PrintStream;

/**
 * Line of a program
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public abstract class AbstractLine {
    abstract void display(PrintStream s, boolean arm);
}
