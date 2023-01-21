package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.LabelFactory;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {

        /* PASS 1*/
        this.classes.verifyListClass(compiler);

        /* PASS 2*/
        this.classes.verifyListClassMembers(compiler);

        /* PASS 3*/
        this.classes.verifyListClassBody(compiler);
        this.main.verifyMain(compiler);
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        int indexTSTO = compiler.getLineIndex();
        compiler.addComment("--- Start of Method Table Initialization ---");
        classes.codeGenMethodTable(compiler);
        compiler.addComment("--- End of Method Table Initialization ---");
        compiler.addComment("");
        compiler.addComment("--- Start of main instructions ---");
        main.codeGenMain(compiler);
        compiler.addInstruction(new HALT());
        compiler.addComment("--- End of main instructions ---");
        compiler.addComment("");
        compiler.addIndex(indexTSTO, new TSTO(compiler.getMemory().TSTO()));
        compiler.getLabelFactory().createTestStack(compiler, indexTSTO);
        compiler.addIndex(indexTSTO+2, new ADDSP(compiler.getMemory().getGlobalOffset()-1));

        compiler.addComment("--- Start of class definition ---");
        classes.codeGenDeclClasses(compiler);
        compiler.addComment("--- End of class definition ---");
        compiler.addComment("");
        compiler.addComment("--- Start of Error messages section ---");
        compiler.getLabelFactory().createErrorSection(compiler);
        compiler.addComment("--- End of Error messages section ---");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
