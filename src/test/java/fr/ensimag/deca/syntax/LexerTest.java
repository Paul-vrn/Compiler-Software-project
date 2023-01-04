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

        generalTest(lex, toCompare);
    }

    @Test
    void testCommentaire2() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/commentaire_lex_02.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/commentaire_lex_02_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare);
    }

//    @Test
//    void testCommentaire3() throws IOException {
//        //lancer programme deca
//        String[] args = {"src/test/deca/syntax/invalid/commentaire_lex_03.deca"};
//        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
//
//        //On skip les 10 lignes de commentaire du test
//        for(int i = 0; i < 10; i++)
//        {
//            lex.nextToken();
//        }
//
//        String concatenatedString = "";
//        //On parcourt les tokens qui nous interessent
//        while(true)
//        {
//            Token t = lex.nextToken();
//            //System.out.println("TEST : " + t.getText());
//            if(t.getType() == -1)
//            {
//                break;
//            }
//            System.out.println(t);
//            concatenatedString += t.getText();
//        }
//        System.out.println(concatenatedString);
//        //assertEquals(scanner.nextLine(), concatenatedString);
//
//    }

//    @Test
//    void testFormatCaract1() throws IOException {
//        //lancer programme deca
//        String[] args = {"src/test/deca/syntax/invalid/esc_lex_01.deca"};
//        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
//
//        FileInputStream file = new FileInputStream("src/test/deca/syntax/invalid/esc_lex_01_oracle.txt");
//        Scanner scanner = new Scanner(file);
//
//        generalTest(lex, scanner);
//    }

//    @Test
//    void testFormatCaract2() throws IOException {
//        //lancer programme deca
//        String[] args = {"src/test/deca/syntax/invalid/esc_lex_02.deca"};
//        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
//
//        FileInputStream file = new FileInputStream("src/test/deca/syntax/invalid/esc_lex_02.deca_oracle.txt");
//        Scanner scanner = new Scanner(file);
//
//        generalTest(lex, scanner);
//    }

    @Test
    void testFloat1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/float_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/float_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare);
    }

    @Test
    void testFloatHex1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/floathex_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/floathex_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare);
    }

    @Test
    void testHex1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/hex_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/hex_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare);

    }

//    @Test
//    void testIdent1() throws IOException {
//        //lancer programme deca
//        String[] args = {"src/test/deca/syntax/invalid/ident_lex_01.deca"};
//        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
//
//        FileInputStream file = new FileInputStream("src/test/deca/syntax/invalid/ident_lex_01.deca_oracle.txt");
//        Scanner scanner = new Scanner(file);
//
//        generalTest(lex, scanner);
//    }

//    @Test
//    void testInclude0() throws IOException {
//        //lancer programme deca
//        String[] args = {"src/test/deca/syntax/invalid/include_lex_0.deca"};
//        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
//
//        FileInputStream file = new FileInputStream("src/test/deca/syntax/invalid/include_lex_0.deca_oracle.txt");
//        Scanner scanner = new Scanner(file);
//
//        generalTest(lex, scanner);
//    }
//
//    @Test
//    void testInclude1() throws IOException {
//        //lancer programme deca
//        String[] args = {"src/test/deca/syntax/invalid/include_lex_1.deca"};
//        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
//
//        FileInputStream file = new FileInputStream("src/test/deca/syntax/invalid/include_lex_1.deca_oracle.txt");
//        Scanner scanner = new Scanner(file);
//
//        generalTest(lex, scanner);
//    }
//
//    @Test
//    void testInclude2() throws IOException {
//        //lancer programme deca
//        String[] args = {"src/test/deca/syntax/invalid/include_lex_2.deca"};
//        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
//
//        FileInputStream file = new FileInputStream("src/test/deca/syntax/invalid/include_lex_2.deca_oracle.txt");
//        Scanner scanner = new Scanner(file);
//
//        generalTest(lex, scanner);
//    }

    @Test
    void testMultiline1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/multiline_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/multiline_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare);
    }

    @Test
    void testMultiline2() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/multiline_lex_02.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/multiline_lex_02_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare);
    }

//    @Test
//    void testNbr1() throws IOException {
//        //lancer programme deca
//        String[] args = {"src/test/deca/syntax/invalid/nbr_lex_01.deca"};
//        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
//
//        FileInputStream file = new FileInputStream("src/test/deca/syntax/invalid/nbr_lex_01.deca_oracle.txt");
//        Scanner scanner = new Scanner(file);
//
//        generalTest(lex, scanner);
//    }

    @Test
    void testSci1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/sci_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/sci_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare);
    }

    @Test
    void testConc1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/invalid/str_conc_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/str_conc_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(lex, toCompare);
    }

    /**
     * Vérifie si le lexer retourne bien ce qu'on attend (dans le fichier test correspondant).
     * @param lex
     * @param toCompare
     */
    void generalTest(DecaLexer lex, String toCompare) {
        System.out.println("------------------------------------ \n");

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
            if(t.getText() == "<EOF>")
            {
                break;
            }
            System.out.println("ICI : " + t.getText() + "  " + t.getType());
            concatenatedString += t.getText();
        }
        System.out.println("AFFICHAGE DE CONCATENATEDSTRING : " + concatenatedString);
        assertEquals(toCompare, concatenatedString);
        System.out.println("");
    }
}
