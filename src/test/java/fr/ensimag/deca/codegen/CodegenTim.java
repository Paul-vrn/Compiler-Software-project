package fr.ensimag.deca.codegen;

import fr.ensimag.deca.CLIException;
import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.DecacFatalError;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodegenTim {

    @Test
    void test01field_class_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/field_class_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/field_class_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test02field_class_02() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/field_class_02.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/field_class_02_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test03get_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/get_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/get_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test04ident_class_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/ident_class_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/ident_class_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test04method_ovrlap_class_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/method_ovrlap_class_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/method_ovrlap_class_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test05nested_method_param_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/nested_method_param_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/nested_method_param_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test06nested_select_class01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/nested_select_class_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/nested_select_class_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test07new_cont_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/new_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/new_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test08object_cont_01_oracle() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/object_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/object_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test09op_comp_valid_class_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/op_comp_valid_class_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/op_comp_valid_class_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test10param_class_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/param_class_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/param_class_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test11protected_cont_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/protected_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/protected_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test12return_cont_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/return_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/return_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test13subclass_cont_01() throws IOException {
        String[] args = {"src/test/deca/codegen/valid/objet/subclass_cont_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/subclass_cont_01_oracle.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test14for_nested_pars_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/for_nested_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/for_nested_pars_01_oracle2.txt";
        generalTestValid(args, file2, null);
    }

    @Test
    void test15if_else_01() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/if_else_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/if_else_01_oracle2.txt";
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
