package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.ima.instructions.ADDSP;
import fr.ensimag.pseudocode.ima.instructions.TSTO;

import java.io.PrintStream;

import fr.ensimag.pseudocode.ima.instructions.HALT;
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

    /**
     * VerifyProgram, calls the functions associated with the 3 passes of the part B
     *
     * @param compiler
     * @throws ContextualError
     */
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
        if (!compiler.getCompilerOptions().getSansObjet()) {
            compiler.addComment("--- Start of Method Table Initialization ---");
            classes.codeGenMethodTable(compiler);
            compiler.addComment("--- End of Method Table Initialization ---");
        }
        compiler.addComment("--- Start of main instructions ---");
        main.codeGenMain(compiler);
        compiler.addInstruction(new HALT());
        compiler.addComment("--- End of main instructions ---");
        compiler.addIndex(indexTSTO, new TSTO(compiler.getMemory().TSTO()));
        compiler.getLabelFactory().createTestStack(compiler, indexTSTO);
        compiler.addIndex(indexTSTO + ((compiler.getLabelFactory().getNoCheck())?1:2), new ADDSP(compiler.getMemory().getGlobalOffset() - 1));

        if (!compiler.getCompilerOptions().getSansObjet()) {
            compiler.addComment("--- Start of class definition ---");
            classes.codeGenDeclClasses(compiler);
            compiler.addComment("--- End of class definition ---");
        }
        compiler.getLabelFactory().createErrorSection(compiler);
    }

    @Override
    public void armCodeGenProgram(DecacCompiler compiler) {
        compiler.addComment("Main program");
        main.armCodeGenMain(compiler);
        //compiler.getLabelFactory().createErrorSection(compiler);

        compiler.getLabelFactory().createArmErrorSection(compiler);
        compiler.getLabelFactory().createPrintLabel(compiler);
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
