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
    void test01_condition() throws IOException {
        String[] args = {"src/test/deca/syntax/optionp/condition.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test02_condition_2() throws IOException {
        String[] args = {"src/test/deca/syntax/optionp/condition_2.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test03_commentaire_pars_01() throws IOException {
        String[] args = {"src/test/deca/syntax/valid/commentaire_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test04_include_pars_01() throws IOException {
        String[] args = {"src/test/deca/syntax/valid/include_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test05_str_conc_pars_01() throws IOException {
        String[] args = {"src/test/deca/syntax/valid/str_conc_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test06_assign_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/assign_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test07_assign_mult_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/assign_mult_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test08_bool_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/bool_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test09_cond_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cond_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test10_condition_modulo_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/condition_modulo_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test11_exemple_sans_objet() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/exemple_sans_objet.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test12_expr_alone_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/expr_alone_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test13for_nested_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/for_nested_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test14_hw_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/hw_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test15_if_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/if_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test16_if_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/if_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test17_init_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/init_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test18_init_cont_val_02() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/init_cont_val_02.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test19_intint_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/intint_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test20_minmin_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/minmin_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test21_mod_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/mod_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test22_name_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/name_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test23_negative_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/negative_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test24_negative_pars_02() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/negative_pars_02.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test25_not_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/not_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test26_op_arith_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/op_arith_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test27_op_comp_valid_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/op_comp_valid_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test28_print_assign_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/print_assign_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test29_print_nest_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/print_nest_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test30_read_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/read_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test31_while_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/while_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test32_cast_int() throws IOException {
        String[] args = {"src/test/deca/syntax/optionp/cast_int.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test33_Soupe() throws IOException {
        String[] args = {"src/test/deca/syntax/optionp/Soupe.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test34_field_class_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/field_class_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test35_get_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/get_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test36_method_ovrlap_class_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/method_ovrlap_class_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test37_new_cont_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/new_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test39_param_class_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/param_class_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test40_return_cont_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/return_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test41_this_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/this_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test42_instanceof_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/instanceof_cont_01.deca"};
        generalTestValid(args, null);
    }

    /*@Test
    void test43_class_method_asm_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_method_asm_01.deca"};
        generalTestValid(args, null);
    }*/

    @Test
    void test34_no_operation() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/no_operation.deca"};
        generalTestValid(args, null);
    }


    void generalTestValid(String[] args, String input) throws IOException {
        Logger.getRootLogger().setLevel(Level.OFF);

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
