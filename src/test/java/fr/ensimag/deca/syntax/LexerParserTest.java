package fr.ensimag.deca.syntax;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractProgram;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerParserTest {

    void testHelloWorld() throws IOException {
        //lancer programme deca

        String[] args = {"src/test/deca/syntax/invalid/commentaire_lex_01.deca"};
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);

        String file = "src/test/deca/syntax/invalid/commentaire_lex_01_oracle.txt";
        String toCompare = new String(Files.readAllBytes(Paths.get(file)));

        generalTest(args, toCompare,"");
    }

    void generalTest(String[] args, String toCompare, String name) throws IOException {
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
            assertEquals(toCompare, System.out);
            prog.prettyPrint(System.out);
        }
    }

}
