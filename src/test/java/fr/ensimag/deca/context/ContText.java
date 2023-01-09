package fr.ensimag.deca.context;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.syntax.AbstractDecaLexer;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.LocationException;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class ContText {
    @Test
    void testInit1() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        String[] args = {"src/test/deca/context/invalid/init_cont_inval_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = new File(args[0]);
        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(compiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
            return; // Unreachable, but silents a warning.
        }
        try {
            prog.verifyProgram(compiler);
        } catch (LocationException e) {
            String file2 = "src/test/deca/context/invalid/init_cont_inval_01_oracle.txt";
            String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
            assertEquals(e.getMessage(), toCompare);
        }

    }
    @Test
    void testDecl1() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        String[] args = {"src/test/deca/context/invalid/decl_comp_inval_02.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = new File(args[0]);
        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(compiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
            return; // Unreachable, but silents a warning.
        }
        try {
            prog.verifyProgram(compiler);
        } catch (LocationException e) {
            String file2 = "src/test/deca/context/invalid/decl_comp_inval_oracle.txt";
            String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
            assertEquals(e.getMessage(), toCompare);
        }

    }
}
