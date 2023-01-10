package fr.ensimag.deca.codegen;

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
        String[] args = {"src/test/deca/codegen/valid/test_expression_arith.deca"};
        String file2 = "src/test/deca/codegen/valid/test_expression_arith_oracle.txt";
        generalTestValid(args, file2);
    }






    void generalTestValid(String[] args, String fileOracle) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        try {
            String[] decacCommand = {"/bin/bash", "decac", args[0]};
            Process decacProcess = Runtime.getRuntime().exec(decacCommand);
            int exitStatus;
            try {
                exitStatus = decacProcess.waitFor();
            } catch (InterruptedException e) {
                exitStatus = -1;
            }
            if(exitStatus==-1){
                System.out.println("Error in decac execution");
            }
            //if the process has an output throw an error
            if (decacProcess.getInputStream().read() != -1) {
                throw new Error("Compilation has an output");
            }
            String AssemblerFileName = args[0].substring(0, args[0].length() - "deca".length()) + "ass";
            String[] imaCommand = {"/bin/bash", "ima", AssemblerFileName};
            Process imaProcess = Runtime.getRuntime().exec(imaCommand);
            //put the output of imaProcess in a string called output
            BufferedReader reader = new BufferedReader(new InputStreamReader(imaProcess.getInputStream()));
            String line = null;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
                output.append(System.getProperty("line.separator"));
            }



            String oracle = new String(Files.readAllBytes(Paths.get(fileOracle)));
            assertEquals(oracle, output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
