package fr.ensimag.deca.syntax;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractProgram;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


public class SyntTest {

    @Test
    void testCommentaire1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/valid/commentaire_pars_01.deca"};
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
            String file2 = "src/test/deca/syntax/valid/commentaire_pars_01_oracle.txt";
            String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
            assertEquals(prog.prettyPrint(), toCompare);
        }
    }

    @Test
    void testIncl1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/valid/include_pars_01.deca"};
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
            String file2 = "src/test/deca/syntax/valid/include_pars_01_oracle.txt";
            String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
            assertEquals(prog.prettyPrint(), toCompare);
        }
    }
    @Test
    void testConcStr1() throws IOException {
        //lancer programme deca
        String[] args = {"src/test/deca/syntax/valid/str_conc_pars_01.deca"};
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
            String file2 = "src/test/deca/syntax/valid/str_conc_pars_01_oracle.txt";
            String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
            assertEquals(prog.prettyPrint(), toCompare);
        }
    }


}