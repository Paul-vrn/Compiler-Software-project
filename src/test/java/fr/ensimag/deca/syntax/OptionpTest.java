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
    void test01() throws IOException {
        String[] args = {"src/test/deca/syntax/optionp/condition.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test02() throws IOException {
        String[] args = {"src/test/deca/syntax/optionp/condition_2.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test03() throws IOException {
        String[] args = {"src/test/deca/syntax/valid/commentaire_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test04() throws IOException {
        String[] args = {"src/test/deca/syntax/valid/include_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test05() throws IOException {
        String[] args = {"src/test/deca/syntax/valid/str_conc_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test06() throws IOException {
        String[] args = {"src/test/deca/context/valid/assign_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test07() throws IOException {
        String[] args = {"src/test/deca/context/valid/assign_mult_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test08() throws IOException {
        String[] args = {"src/test/deca/context/valid/bool_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test09() throws IOException {
        String[] args = {"src/test/deca/context/valid/cond_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test10() throws IOException {
        String[] args = {"src/test/deca/context/valid/condition_modulo_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test11() throws IOException {
        String[] args = {"src/test/deca/context/valid/exemple_sans_objet.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test12() throws IOException {
        String[] args = {"src/test/deca/context/valid/expr_alone_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test13() throws IOException {
        String[] args = {"src/test/deca/context/valid/for_nested_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test14() throws IOException {
        String[] args = {"src/test/deca/context/valid/hw_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test15() throws IOException {
        String[] args = {"src/test/deca/context/valid/if_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test16() throws IOException {
        String[] args = {"src/test/deca/context/valid/if_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test17() throws IOException {
        String[] args = {"src/test/deca/context/valid/init_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test18() throws IOException {
        String[] args = {"src/test/deca/context/valid/init_cont_val_02.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test19() throws IOException {
        String[] args = {"src/test/deca/context/valid/intint_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test20() throws IOException {
        String[] args = {"src/test/deca/context/valid/minmin_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test21() throws IOException {
        String[] args = {"src/test/deca/context/valid/mod_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test22() throws IOException {
        String[] args = {"src/test/deca/context/valid/name_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test23() throws IOException {
        String[] args = {"src/test/deca/context/valid/negative_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test24() throws IOException {
        String[] args = {"src/test/deca/context/valid/negative_pars_02.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test25() throws IOException {
        String[] args = {"src/test/deca/context/valid/not_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test26() throws IOException {
        String[] args = {"src/test/deca/context/valid/op_arith_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test27() throws IOException {
        String[] args = {"src/test/deca/context/valid/op_comp_valid_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test28() throws IOException {
        String[] args = {"src/test/deca/context/valid/print_assign_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test29() throws IOException {
        String[] args = {"src/test/deca/context/valid/print_nest_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test30() throws IOException {
        String[] args = {"src/test/deca/context/valid/read_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test31() throws IOException {
        String[] args = {"src/test/deca/context/valid/while_pars_01.deca"};
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
            String expected = prog.prettyPrint().replaceAll("\\[.*?\\]", "");
            String actual = prog2.prettyPrint().replaceAll("\\[.*?\\]", "");
            assertEquals(expected, actual);
        }
    }
}
