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
            System.err.println("Error: in test file CodegenTest, more than 1 source files are not implemented (yet?)");
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
        assertEquals(oracle, output.toString());

    }
}
