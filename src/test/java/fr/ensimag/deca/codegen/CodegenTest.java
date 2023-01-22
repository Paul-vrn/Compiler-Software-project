package fr.ensimag.deca.codegen;

import fr.ensimag.deca.CLIException;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodegenTest {



    @Test
    void test01Condition() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition.deca"};
        String file2 = "src/test/deca/codegen/valid/condition_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test02Condition2() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition_2.deca"};
        String file2 = "src/test/deca/codegen/valid/condition_2_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test03Condition3() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition_3.deca"};
        String file2 = "src/test/deca/codegen/valid/condition_3_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test04DeclExpr() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_expr.deca"};
        String file2 = "src/test/deca/codegen/valid/decl_expr_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test05DeclNull() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_null.deca"};
        String file2 = "src/test/deca/codegen/valid/decl_null_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test06ExprArith() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/test_expression_arith.deca"};
        String file2 = "src/test/deca/codegen/valid/test_expression_arith_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test07ExprBool() throws IOException { //probleme label ifthenelse
        String[] args = {"src/test/deca/codegen/valid/test_expression_bool.deca"};
        String file2 = "src/test/deca/codegen/valid/test_expression_bool_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test08While() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/while.deca"};
        String file2 = "src/test/deca/codegen/valid/while_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test09DeclExprCondIfWhile() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_expr_cond_if_while.deca"};
        String file2 = "src/test/deca/codegen/valid/decl_expr_cond_if_while_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test10TestIOFloat() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_io_float_error.deca"};
        String file2 = "src/test/deca/codegen/error/test_io_float_error_oracle.txt";
        String input = "5";
        generalTestValid(args, file2, input);
    }

    @Test
    void test11TestIOInt() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_io_int_error.deca"};
        String file2 = "src/test/deca/codegen/error/test_io_int_error_oracle.txt";
        String input = "5.0";
        generalTestValid(args, file2, input);
    }

    @Test
    void test12TestIOInt() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_io_int_error.deca"};
        String file2 = "src/test/deca/codegen/error/test_io_int_error_oracle.txt";
        String input = "test";
        generalTestValid(args, file2, input);
    }

    @Test
    void test13DivFloat() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_div_0_float.deca"};
        String file2 = "src/test/deca/codegen/error/test_div_0_float_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test14Div0() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_div_0_int.deca"};
        String file2 = "src/test/deca/codegen/error/test_div_0_int_oracle.txt";
        generalTestValid(args, file2, null);
    }



    @Test
    void test15ExprArithR() throws IOException{
        //test 16 and 17 should produce the same output regardless of the -r option
        String[] args = {"src/test/deca/codegen/valid/test_expression_arith_r.deca"};
        String file2 = "src/test/deca/codegen/valid/test_expression_arith_r_oracle.txt";
        generalTestValid(args, file2, null);
    }
    @Test
    void test16ExprArithR4() throws IOException {
        String[] args = {"-r", "4", "src/test/deca/codegen/valid/test_expression_arith_r.deca"};
        String file2 = "src/test/deca/codegen/valid/test_expression_arith_r_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test17Equals() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/equals.deca"};
        String file2 = "src/test/deca/codegen/valid/equals_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test18NotEquals() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/not_equals.deca"};
        String file2 = "src/test/deca/codegen/valid/not_equals_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test19EmptyMain() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/empty_main.deca"};
        String file2 = "src/test/deca/codegen/valid/empty_main_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test20LowerOrEqual() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/lower_or_equal.deca"};
        String file2 = "src/test/deca/codegen/valid/lower_or_equal_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test21NoInit() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/no_init.deca"};
        String file2 = "src/test/deca/codegen/valid/no_init_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test22Cast() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/cast.deca"};
        String file2 = "src/test/deca/codegen/valid/cast_oracle.txt";
        generalTestValid(args, file2, null);
    }


    @Test
    void test23Include() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/include.deca"};
        String file2 = "src/test/deca/codegen/valid/include_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test24assign_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/assign_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/assign_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test24assign_mult_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/assign_mult_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/assign_mult_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test25bool_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/bool_pars_01.deca"};
        String file2 = "src/test/deca/codegen/valid/bool_pars_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test26cast_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cast_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/cast_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test27cast_cont_02() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cast_cont_02.deca"};
        String file2 = "src/test/deca/codegen/valid/cast_cont_02_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test29cond_pars_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/cond_pars_01.deca"};
        String file2 = "src/test/deca/codegen/valid/cond_pars_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test30condition_modulo_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/condition_modulo_01.deca"};
        String file2 = "src/test/deca/codegen/valid/condition_modulo_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test32if_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/if_pars_01.deca"};
        String file2 = "src/test/deca/codegen/valid/if_pars_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test33minmin_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/minmin_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/minmin_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test34mod_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/mod_cont_val_01.deca"};
        String file2 = "src/test/deca/codegen/valid/mod_cont_val_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test35name_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/name_pars_01.deca"};
        String file2 = "src/test/deca/codegen/valid/name_pars_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test36negative_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/negative_pars_01.deca"};
        String file2 = "src/test/deca/codegen/valid/negative_pars_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test37negative_pars_02() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/negative_pars_02.deca"};
        String file2 = "src/test/deca/codegen/valid/negative_pars_02_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test38not_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/not_cont_val_01.deca"};
        String file2 = "src/test/deca/codegen/valid/not_cont_val_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test39op_arith_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/op_arith_cont_val_01.deca"};
        String file2 = "src/test/deca/codegen/valid/op_arith_cont_val_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test40op_comp_valid_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/op_comp_valid_01.deca"};
        String file2 = "src/test/deca/codegen/valid/op_comp_valid_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test41print_assign_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/print_assign_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/print_assign_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test42print_nest_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/print_nest_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/print_nest_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test43while_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/while_pars_01.deca"};
        String file2 = "src/test/deca/codegen/valid/while_pars_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test44decl_anormal() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_anormal.deca"};
        String file2 = "src/test/deca/codegen/valid/decl_anormal_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test45print_wint() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/print_wint.deca"};
        String file2 = "src/test/deca/codegen/valid/print_wint_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test46print_wfloat() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/print_wfloat.deca"};
        String file2 = "src/test/deca/codegen/valid/print_wfloat_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test47class_extends_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_01.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test48class_extends_02() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_02.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_02_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test49class_extends_03() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_03.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_03_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test50class_extends_04() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_04.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_04_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test51class_extends_05() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_05.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_05_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test52class_extends_06() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_06.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_06_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test53class_extends_07() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_07.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_07_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test54class_extends_08() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_08.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_08_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test55class_extends_09() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_extends_09.deca"};
        String file2 = "src/test/deca/codegen/valid/class_extends_09_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test56class_object_equals_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_object_equals_01.deca"};
        String file2 = "src/test/deca/codegen/valid/class_object_equals_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test57class_object_listnode_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/test_class_listnode.deca"};
        String file2 = "src/test/deca/codegen/valid/test_class_listnode_oracle.txt";
            generalTestValid(args, file2, null);
    }

    @Test
    void test58class_object_heap_overflow() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_class_heap_overflow.deca"};
        String file2 = "src/test/deca/codegen/error/test_class_heap_overflow_oracle.txt";
            generalTestValid(args, file2, null);
    }

    @Test
    void test59class_object_stack_overflow() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_class_stack_overflow.deca"};
        String file2 = "src/test/deca/codegen/error/test_class_stack_overflow_oracle.txt";
            generalTestValid(args, file2, null);
    }

    @Test
    void test60class_no_operation() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/no_operation.deca"};
        String file2 = "src/test/deca/codegen/valid/no_operation_oracle.txt";
            generalTestValid(args, file2, null);
    }

    @Test
    void test61classSoupe() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/Soupe.deca"};
        String file2 = "src/test/deca/codegen/valid/Soupe_oracle.txt";
            generalTestValid(args, file2, null);
    }

    @Test
    void test62Math() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/Math.deca"};
        String file2 = "src/test/deca/codegen/valid/Math_oracle.txt";
            generalTestValid(args, file2, null);
    }

    @Test
    void test63_prog_vide() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/prog_vide.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/prog_vide_oracle.txt";
        generalTestValid(args, file2,null);
    }

    @Test
    void test63class_method_asm_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/class_method_asm_01.deca"};
        String file2 = "src/test/deca/codegen/valid/class_method_asm_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test64math_int_ov() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/math_int_ov.deca"};
        String file2 = "src/test/deca/codegen/valid/math_int_ov_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test65math_int_ov2() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/math_int_ov2.deca"};
        String file2 = "src/test/deca/codegen/valid/math_int_ov2_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test67math_float_ov_01() throws IOException {
        String[] args = {"src/test/deca/codegen/error/math_float_ov_01.deca"};
        String file2 = "src/test/deca/codegen/error/math_float_ov_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test68math_float_ov_02() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/math_float_ov_02.deca"};
        String file2 = "src/test/deca/codegen/valid/math_float_ov_02_oracle.txt";
        generalTestValid(args, file2, null);
    }

    void generalTestValid(String[] args, String fileOracle, String input) throws IOException {
        Logger.getRootLogger().setLevel(Level.OFF);
        //if to add option
        CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            System.exit(1);
        }
        if(options.getSourceFiles().size() != 1) {
            System.err.println("Error: in test file CodegenTest, more than 1 source files are not implemented");
            System.exit(1);
        }
        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(), options.getSourceFiles().get(0));
        try{
            compiler.compile();
        } catch (DecacFatalError e) {
            e.printStackTrace();
        }
        int fileNameLength = args[args.length-1].length();
        String AssemblerFileName = args[args.length-1].substring(0, fileNameLength - "deca".length()) + "ass";
        String[] imaCommand = (input == null) ?
                new String[] {"../global/bin/ima", AssemblerFileName} :
                new String[] {"/bin/sh", "-c", "echo " + input + " | ../global/bin/ima " + AssemblerFileName };

        Process imaProcess = Runtime.getRuntime().exec(imaCommand);
        BufferedReader reader = new BufferedReader(new InputStreamReader(imaProcess.getInputStream()));
        String line = "";
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line);
            output.append(System.getProperty("line.separator"));
        }
        String oracle = new String(Files.readAllBytes(Paths.get(fileOracle)));
        String[] outputT = output.toString().split("\n");
        String[] oracleT = oracle.split("\n");
        for (int i = 0; i < oracleT.length; i++) {
            assertEquals(oracleT[i].trim(), outputT[i].trim());
        }

    }
}
