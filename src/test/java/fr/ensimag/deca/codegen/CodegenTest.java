package fr.ensimag.deca.codegen;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodegenTest {



    @Test
    void test1() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition.deca"};
        String file2 = "src/test/deca/codegen/valid/condition_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test2() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition_2.deca"};
        String file2 = "src/test/deca/codegen/valid/condition_2_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test3() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/condition_3.deca"};
        String file2 = "src/test/deca/codegen/valid/condition_3_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test4() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_expr.deca"};
        String file2 = "src/test/deca/codegen/valid/decl_expr_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test5() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_null.deca"};
        String file2 = "src/test/deca/codegen/valid/decl_null_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test6() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/test_expression_arith.deca"};
        String file2 = "src/test/deca/codegen/valid/test_expression_arith_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test7() throws IOException { //probleme label ifthenelse
        String[] args = {"src/test/deca/codegen/valid/test_expression_bool.deca"};
        String file2 = "src/test/deca/codegen/valid/test_expression_bool_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test8() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/while.deca"};
        String file2 = "src/test/deca/codegen/valid/while_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test9() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/decl_expr_cond_if_while.deca"};
        String file2 = "src/test/deca/codegen/valid/decl_expr_cond_if_while_oracle.txt";
        generalTestValid(args, file2);
    }


    void generalTestValid(String[] args, String fileOracle) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(), new File(args[0]));
        try{
            compiler.compile();
        } catch (DecacFatalError e) {
            e.printStackTrace();
        }
        String AssemblerFileName = args[0].substring(0, args[0].length() - "deca".length()) + "ass";
        String[] imaCommand = {"../global/bin/ima", AssemblerFileName};
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
