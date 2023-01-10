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

import static org.junit.jupiter.api.Assertions.*;

public class ContTest {
    @Test
    void test1() throws IOException {
        String[] args = {"src/test/deca/context/invalid/arith_modulo_op_01.deca"};
        String file2 = "src/test/deca/context/invalid/arith_modulo_op_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test2() throws IOException {
        String[] args = {"src/test/deca/context/invalid/arith_modulo_op_02.deca"};
        String file2 = "src/test/deca/context/invalid/arith_modulo_op_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test3() throws IOException {
        String[] args = {"src/test/deca/context/invalid/arith_op_type_mismatch_01.deca"};
        String file2 = "src/test/deca/context/invalid/arith_op_type_mismatch_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test4() throws IOException {
        String[] args = {"src/test/deca/context/invalid/arith_op_type_mismatch_02.deca"};
        String file2 = "src/test/deca/context/invalid/arith_op_type_mismatch_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test5() throws IOException {
        String[] args = {"src/test/deca/context/invalid/arith_op_type_mismatch_03.deca"};
        String file2 = "src/test/deca/context/invalid/arith_op_type_mismatch_03_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test6() throws IOException {
        String[] args = {"src/test/deca/context/invalid/assign_type_problem_01.deca"};
        String file2 = "src/test/deca/context/invalid/assign_type_problem_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test7() throws IOException {
        String[] args = {"src/test/deca/context/invalid/assign_type_problem_02.deca"};
        String file2 = "src/test/deca/context/invalid/assign_type_problem_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test8() throws IOException {
        String[] args = {"src/test/deca/context/invalid/bool_op_mismatch.deca"};
        String file2 = "src/test/deca/context/invalid/bool_op_mismatch_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test9() throws IOException {
        String[] args = {"src/test/deca/context/invalid/compare_op_mismatch_01.deca"};
        String file2 = "src/test/deca/context/invalid/compare_op_mismatch_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test10() throws IOException {
        String[] args = {"src/test/deca/context/invalid/compare_op_mismatch_02.deca"};
        String file2 = "src/test/deca/context/invalid/compare_op_mismatch_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test11() throws IOException {
        String[] args = {"src/test/deca/context/invalid/compare_op_mismatch_03.deca"};
        String file2 = "src/test/deca/context/invalid/compare_op_mismatch_03_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test12() throws IOException {
        String[] args = {"src/test/deca/context/invalid/ifelse_wrong_argument_01.deca"};
        String file2 = "src/test/deca/context/invalid/ifelse_wrong_argument_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test13() throws IOException {
        String[] args = {"src/test/deca/context/invalid/ifelse_wrong_argument_02.deca"};
        String file2 = "src/test/deca/context/invalid/ifelse_wrong_argument_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test14() throws IOException {
        String[] args = {"src/test/deca/context/invalid/init_type_01.deca"};
        String file2 = "src/test/deca/context/invalid/init_type_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test15() throws IOException {
        String[] args = {"src/test/deca/context/invalid/init_type_02.deca"};
        String file2 = "src/test/deca/context/invalid/init_type_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test16() throws IOException {
        String[] args = {"src/test/deca/context/invalid/init_type_03.deca"};
        String file2 = "src/test/deca/context/invalid/init_type_03_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test17() throws IOException {
        String[] args = {"src/test/deca/context/invalid/init_type_04.deca"};
        String file2 = "src/test/deca/context/invalid/init_type_04_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test18() throws IOException {
        String[] args = {"src/test/deca/context/invalid/missing_type_declaration.deca"};
        String file2 = "src/test/deca/context/invalid/missing_type_declaration_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test19() throws IOException {
        String[] args = {"src/test/deca/context/invalid/op_not_mismatch.deca"};
        String file2 = "src/test/deca/context/invalid/op_not_mismatch_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test20() throws IOException {
        String[] args = {"src/test/deca/context/invalid/op_unaryMinus_mismatch.deca"};
        String file2 = "src/test/deca/context/invalid/op_unaryMinus_mismatch_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test21() throws IOException {
        String[] args = {"src/test/deca/context/invalid/print_argument_type.deca"};
        String file2 = "src/test/deca/context/invalid/print_argument_type_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test22() throws IOException {
        String[] args = {"src/test/deca/context/invalid/type_void.deca"};
        String file2 = "src/test/deca/context/invalid/type_void_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test23() throws IOException {
        String[] args = {"src/test/deca/context/invalid/unknown_type.deca"};
        String file2 = "src/test/deca/context/invalid/unknown_type_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test24() throws IOException {
        String[] args = {"src/test/deca/context/invalid/var_already_defined.deca"};
        String file2 = "src/test/deca/context/invalid/var_already_defined_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test25() throws IOException {
        String[] args = {"src/test/deca/context/invalid/init_undef_value.deca"};
        String file2 = "src/test/deca/context/invalid/init_undef_value_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test26() throws IOException {
        String[] args = {"src/test/deca/context/invalid/assign_undef_value.deca"};
        String file2 = "src/test/deca/context/invalid/assign_undef_value_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test27() throws IOException {
        String[] args = {"src/test/deca/context/invalid/arith_undef_operand.deca"};
        String file2 = "src/test/deca/context/invalid/arith_undef_operand_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test28() throws IOException {
        String[] args = {"src/test/deca/context/invalid/while_wrong_argument.deca"};
        String file2 = "src/test/deca/context/invalid/while_wrong_argument_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test29() throws IOException {
        String[] args = {"src/test/deca/context/valid/assign_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/assign_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test30() throws IOException {
        String[] args = {"src/test/deca/context/valid/assign_mult_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/assign_mult_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test31() throws IOException {
        String[] args = {"src/test/deca/context/valid/bool_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/bool_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test32() throws IOException {
        String[] args = {"src/test/deca/context/valid/cond_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/cond_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test33() throws IOException {
        String[] args = {"src/test/deca/context/valid/condition_modulo_01.deca"};
        String file2 = "src/test/deca/context/valid/condition_modulo_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test34() throws IOException {
        String[] args = {"src/test/deca/context/valid/exemple_sans_objet.deca"};
        String file2 = "src/test/deca/context/valid/exemple_sans_objet_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test35() throws IOException {
        String[] args = {"src/test/deca/context/valid/expr_alone_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/expr_alone_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test36() throws IOException {
        String[] args = {"src/test/deca/context/valid/for_nested_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/for_nested_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test37() throws IOException {
        String[] args = {"src/test/deca/context/valid/hw_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/hw_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test38() throws IOException {
        String[] args = {"src/test/deca/context/valid/if_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/if_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test39() throws IOException {
        String[] args = {"src/test/deca/context/valid/if_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/if_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test40() throws IOException {
        String[] args = {"src/test/deca/context/valid/init_cont_val_01.deca"};
        String file2 = "src/test/deca/context/valid/init_cont_val_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test41() throws IOException {
        String[] args = {"src/test/deca/context/valid/init_cont_val_02.deca"};
        String file2 = "src/test/deca/context/valid/init_cont_val_02_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test42() throws IOException {
        String[] args = {"src/test/deca/context/valid/intint_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/intint_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test43() throws IOException {
        String[] args = {"src/test/deca/context/valid/minmin_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/minmin_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test44() throws IOException {
        String[] args = {"src/test/deca/context/valid/mod_cont_val_01.deca"};
        String file2 = "src/test/deca/context/valid/mod_cont_val_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test45() throws IOException {
        String[] args = {"src/test/deca/context/valid/name_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/name_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test46() throws IOException {
        String[] args = {"src/test/deca/context/valid/negative_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/negative_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test47() throws IOException {
        String[] args = {"src/test/deca/context/valid/negative_pars_02.deca"};
        String file2 = "src/test/deca/context/valid/negative_pars_02_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test48() throws IOException {
        String[] args = {"src/test/deca/context/valid/not_cont_val_01.deca"};
        String file2 = "src/test/deca/context/valid/not_cont_val_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test49() throws IOException {
        String[] args = {"src/test/deca/context/valid/op_arith_cont_val_01.deca"};
        String file2 = "src/test/deca/context/valid/op_arith_cont_val_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test50() throws IOException {
        String[] args = {"src/test/deca/context/valid/op_comp_valid_01.deca"};
        String file2 = "src/test/deca/context/valid/op_comp_valid_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test51() throws IOException {
        String[] args = {"src/test/deca/context/valid/print_assign_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/print_assign_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test52() throws IOException {
        String[] args = {"src/test/deca/context/valid/print_nest_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/print_nest_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test53() throws IOException {
        String[] args = {"src/test/deca/context/valid/read_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/read_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test54() throws IOException {
        String[] args = {"src/test/deca/context/valid/while_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/while_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }


    void generalTestValid(String[] args, String fileOracle) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
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
            e.display(System.err);
            System.exit(1);
        }
        String file2 = fileOracle;
        String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
        assertEquals(prog.prettyPrint(), toCompare);
    }

    void generalTestInvalid(String[] args, String fileOracle) throws IOException{
        Logger.getRootLogger().setLevel(Level.DEBUG);
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
        } catch (ContextualError e) {
            String file2 = fileOracle;
            String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
            assertEquals(e.getMessage(), toCompare);
        }
    }

}
