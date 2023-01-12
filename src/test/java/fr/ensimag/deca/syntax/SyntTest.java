package fr.ensimag.deca.syntax;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractProgram;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


public class SyntTest {

    @Test
    void test1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/valid/commentaire_pars_01.deca"};
        String fileOracle = "src/test/deca/syntax/valid/commentaire_pars_01_oracle.txt";

        generalTest(args, fileOracle);
    }

    @Test
    void test2() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/valid/include_pars_01.deca"};
        String fileOracle = "src/test/deca/syntax/valid/include_pars_01_oracle.txt";

        generalTest(args, fileOracle);
    }
    @Test
    void test3() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/valid/str_conc_pars_01.deca"};
        String fileOracle = "src/test/deca/syntax/valid/str_conc_pars_01_oracle.txt";

        generalTest(args, fileOracle);
    }

    @Test
    void test4() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/commentaire_lex_02.deca"};

        generalTestError(args);
    }

    @Test
    void test5() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/pvirgule_pars_01.deca"};

        generalTestError(args);
    }

    @Test
    void test6() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/doublepvirgule_pars_01.deca"};

        generalTestError(args);
    }

    @Test
    void test7() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/if_brace_pars_01.deca"};

        generalTestError(args);
    }

    @Test
    void test8() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/if_no_cond_pars_01.deca"};

        generalTestError(args);
    }

    @Test
    void test9() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/print_parent_pars_01.deca"};

        generalTestError(args);
    }

    @Test
    void test10() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/virgule_pars_01.deca"};

        generalTestError(args);
    }

    @Test
    void test11() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/while_brace_pars_01.deca"};

        generalTestError(args);
    }

    void generalTest(String[] args, String fileOracle) throws IOException {
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = null;
        if (lex.getSourceName() != null) {
            file = new File(lex.getSourceName());
        }
        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(decacCompiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
        } else {
            String toCompare = new String(Files.readAllBytes(Paths.get(fileOracle)));
            assertEquals(prog.prettyPrint(), toCompare);
        }
    }

    void generalTestError(String[] args) throws IOException {
        try {
            DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
            CommonTokenStream tokens = new CommonTokenStream(lex);
            DecaParser parser = new DecaParser(tokens);
            File file = null;
            if (lex.getSourceName() != null) {
                file = new File(lex.getSourceName());
            }
            final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), file);
            parser.setDecacCompiler(decacCompiler);

            AbstractProgram prog = parser.parseProgramAndManageErrors(new PrintStream(new OutputStream() {
                public void write(int b) {
                }
            }));
            if (prog != null) {
                fail("Didn't return expected Exception error");
            }
        }catch (Exception e){}
    }



}