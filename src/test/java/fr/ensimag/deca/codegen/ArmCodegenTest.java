package fr.ensimag.deca.codegen;

import fr.ensimag.deca.CLIException;
import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.DecacFatalError;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ArmCodegenTest {

    @Test
    void test01Condition() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test02Condition2() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition_2.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test03Condition3() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition_3.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test04DeclExpr() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_expr.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test05DeclNull() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_null.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test06ExprArith() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/test_expression_arith.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test07ExprBool() throws IOException { //probleme label ifthenelse
        String[] args = {"src/test/deca/codegen/valid/test_expression_bool.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test08While() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/while.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test09DeclExprCondIfWhile() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_expr_cond_if_while.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test10TestIOFloat() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_io_float_error.deca"};
        String input = "5";
        generalTestValid(args, input);
    }

    @Test
    void test11TestIOInt() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_io_int_error.deca"};
        String input = "5.0";
        generalTestValid(args, input);
    }

    @Test
    void test12TestIOInt() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_io_int_error.deca"};
        String input = "test";
        generalTestValid(args, input);
    }

    @Test
    void test13DivFloat() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_div_0_float.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test14Div0() throws IOException {
        String[] args = {"src/test/deca/codegen/error/test_div_0_int.deca"};
        generalTestValid(args, null);
    }



    @Test
    void test15ExprArithR() throws IOException{
        //test 16 and 17 should produce the same output regardless of the -r option
        String[] args = {"src/test/deca/codegen/valid/test_expression_arith_r.deca"};
        generalTestValid(args, null);
    }
    @Test
    void test16ExprArithR4() throws IOException {
        String[] args = {"-r", "4", "src/test/deca/codegen/valid/test_expression_arith_r.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test17Equals() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/equals.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test18NotEquals() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/not_equals.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test19EmptyMain() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/empty_main.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test20LowerOrEqual() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/lower_or_equal.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test21NoInit() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/no_init.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test22Cast() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/cast.deca"};
        generalTestValid(args, null);
    }


    @Test
    void test23Include() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/include.deca"};
        generalTestValid(args, null);
    }


    void test24assign_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/assign_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test24assign_mult_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/assign_mult_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test25bool_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/bool_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test26cast_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cast_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test27cast_cont_02() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cast_cont_02.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test29cond_pars_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/cond_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test30condition_modulo_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/condition_modulo_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test32if_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/if_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test33minmin_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/minmin_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test34mod_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/mod_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test35name_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/name_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test36negative_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/negative_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test37negative_pars_02() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/negative_pars_02.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test38not_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/not_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test39op_arith_cont_val_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/op_arith_cont_val_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test40op_comp_valid_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/op_comp_valid_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test41print_assign_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/print_assign_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test42print_nest_cont_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/print_nest_cont_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test43while_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/while_pars_01.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test44decl_anormal() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_anormal.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test45print_wint() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/print_wint.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test46print_wfloat() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/print_wfloat.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test47no_operation() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/no_operation.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test48_prog_vide() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/prog_vide.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test49_expression_arith() throws IOException {
        String[] args = {"-r", "10", "src/test/deca/codegen/arm/expression_arith.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test50_expression_arith() throws IOException {
        String[] args = {"-r", "4", "src/test/deca/codegen/arm/expression_arith_register_limited.deca"};
        generalTestValid(args, null);
    }

    @Test
    void test51_expression_arith() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/print_float.deca"};
        generalTestValid(args, null);
    }


    void generalTestValid(String[] args, String input) throws IOException {
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
        options.enableARM();
        DecacCompiler compiler = new DecacCompiler(options, options.getSourceFiles().get(0));
        try{
            assert(!compiler.compile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
