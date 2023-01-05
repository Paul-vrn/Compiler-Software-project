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
    void testCommentaire1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/commentaire_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/commentaire_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Commentaire1");
    }

    @Test
    void testCommentaire2() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/commentaire_lex_02.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/commentaire_lex_02_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Commentaire2");
    }

    @Test
    void testCommentaire3() throws IOException {
        System.out.println("------------------------------------");
        System.out.println("TEST : Commentaire3" + "\n");

        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/commentaire_lex_03.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        //On skip les 10 lignes de commentaire du test
        for(int i = 0; i < 10; i++)
        {
            lex.nextToken();
        }

        String concatenatedString = "";
        //On parcourt les tokens qui nous interessent
        while(true)
        {
            Token t = lex.nextToken();
            //System.out.println("TEST : " + t.getText());
            if(t.getType() == -1)
            {
                break;
            }
            System.out.println(t);
            concatenatedString += t.getText();
        }
        System.out.println(concatenatedString);
    }

    @Test
    void testFloat1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/float_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/float_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Float1");
    }

    @Test
    void testFloatHex1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/floathex_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/floathex_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "FloatHex1");
    }

    @Test
    void testMultiline1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/multiline_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/multiline_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Multiline1");
    }

    @Test
    void testMultiline2() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/multiline_lex_02.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/multiline_lex_02_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Multiline2");
    }

    @Test
    void testSci1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/sci_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/sci_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Sci1");
    }

    @Test
    void testConc1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/str_conc_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/str_conc_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare, "Conc1");
    }

    /**
     * Checks if the lexer return is ok.
     * @param lex
     * @param toCompare
     * @param name
     */
    void generalTest(DecaLexer lex, String toCompare, String name) {
        System.out.println("------------------------------------");
        System.out.println("TEST : " + name + "\n");

        String concatenatedString = "";
        //On parcourt les tokens qui nous interessent
        while(true)
        {
            Token t = lex.nextToken();
            if(t.getText() == "<EOF>")
            {
                break;
            }
            System.out.println(t.getText() + "  " + t.getType());
            concatenatedString += t.getText();
        }
        System.out.println("AFFICHAGE DE CONCATENATED STRING : " + concatenatedString);
        assertEquals(toCompare, concatenatedString);
        System.out.println("");
    }
}
