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
        String[] args = {"src/test/deca/codegen/valid/condition.deca"};
        generalTestValid(args, null);
    }




    void generalTestValid(String[] args, String input) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);

        String outputDecompiled = args[0].substring(0, args[0].length() - 5) + "Decompiled.deca";

        CompilerOptions compilerOpts = new CompilerOptions();
        compilerOpts.enableDecompilation();
        DecacCompiler compiler = new DecacCompiler(compilerOpts, new File(args[0]));
        try {
            compiler.compile();
        } catch (DecacFatalError e) {
            e.printStackTrace();
        }


        CompilerOptions compilerOpts2 = new CompilerOptions();
        File fileDecompiled = new File(outputDecompiled);
        DecacCompiler compiler2 = new DecacCompiler(compilerOpts2, fileDecompiled);

        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);

        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), new File(args[0]));
        final DecacCompiler decacCompiler2 = new DecacCompiler(new CompilerOptions(), fileDecompiled);

        parser.setDecacCompiler(decacCompiler);
        parser.setDecacCompiler(decacCompiler2);

        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        AbstractProgram prog2 = parser.parseProgramAndManageErrors(System.err);

        if (prog == null) {
            System.exit(1);
        } else {
            assertEquals(prog.prettyPrint(), prog2.prettyPrint());
        }
    }
}
