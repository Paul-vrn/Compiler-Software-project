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
    void test01class_object_stack_overflow() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/field_class_01.deca"};
        String file2 = "src/test/deca/codegen/valid/objet/field_class_01_oracle.txt";
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
