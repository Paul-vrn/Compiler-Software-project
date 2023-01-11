package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {

    @Test
    void test1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/commentaire_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/commentaire_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Commentaire1");
    }

    @Test
    void test2() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/str_conc_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/str_conc_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Conc1");
    }

    @Test
    void test3() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/float_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/float_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Float1");
    }

    @Test
    void test4() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/floathex_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/floathex_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "FloatHex1");
    }

    @Test
    void test5() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/multiline_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/multiline_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Multiline1");
    }

    @Test
    void test6() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/multiline_lex_02.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/multiline_lex_02_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Multiline2");
    }

    @Test
    void test7() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/sci_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/sci_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Sci1");
    }

    /**
     * Checks if the lexer return is ok.
     * @param lex
     * @param toCompare
     * @param name
     */
    void generalTest(DecaLexer lex, String toCompare, String name) {

        String concatenatedString = "";
        //On parcourt les tokens qui nous interessent
        while(true)
        {
            Token t = lex.nextToken();
            if(t.getText() == "<EOF>")
            {
                break;
            }
            concatenatedString += t.getText();
        }
        assertEquals(toCompare, concatenatedString);
    }

    void generalTestError(DecaLexer lex) {
        try{
            String concatenatedString = "";
            //On parcourt les tokens qui nous interessent
            while(true)
            {
                Token t = lex.nextToken();
                if(t.getText() == "<EOF>")
                {
                    break;
                }
                concatenatedString += t.getText();
                fail( "Didn't throw an Exception as expected" );
            }
        }catch (Exception e){}

    }
}
