package fr.ensimag.deca.syntax;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractProgram;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.DecacFatalError;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class OptionpTest {

    @Test
    void test1() throws IOException {
        String[] args = {"src/test/deca/syntax/optionp/condition2.deca"};
        generalTestValid(args, null);
    }




    void generalTestValid(String[] args, String input) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);

        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);

        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), new File(args[0]));


        parser.setDecacCompiler(decacCompiler);


        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);

        String outputDecompiled = args[0].substring(0, args[0].length() - 5) + "Decompiled.deca";
        PrintStream out = new PrintStream(new FileOutputStream(outputDecompiled));
        prog.decompile(out);

        File fileDecompiled = new File(outputDecompiled);
        String [] argsDecompiled = {outputDecompiled};

        DecaLexer lex2 = AbstractDecaLexer.createLexerFromArgs(argsDecompiled);
        CommonTokenStream tokens2 = new CommonTokenStream(lex2);
        DecaParser parser2 = new DecaParser(tokens2);
        final DecacCompiler decacCompiler2 = new DecacCompiler(new CompilerOptions(), fileDecompiled);
        parser2.setDecacCompiler(decacCompiler2);
        AbstractProgram prog2 = parser2.parseProgramAndManageErrors(System.err);

        if (prog == null) {
            System.exit(1);
        } else {
            assertEquals(prog.prettyPrint(), prog2.prettyPrint());
        }
    }
}
