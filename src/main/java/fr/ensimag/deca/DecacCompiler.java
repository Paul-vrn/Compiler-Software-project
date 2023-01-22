package fr.ensimag.deca;

import fr.ensimag.deca.codegen.LabelFactory;
import fr.ensimag.deca.codegen.Memory;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.LocationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import fr.ensimag.pseudocode.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Logger;

/**
 * Decac compiler instance.
 *
 * This class is to be instantiated once per source file to be compiled. It
 * contains the meta-data used for compiling (source file name, compilation
 * options) and the necessary utilities for compilation (symbol tables, abstract
 * representation of target file, ...).
 *
 * It contains several objects specialized for different tasks. Delegate methods
 * are used to simplify the code of the caller (e.g. call
 * compiler.addInstruction() instead of compiler.getProgram().addInstruction()).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class DecacCompiler {
    private static final Logger LOG = Logger.getLogger(DecacCompiler.class);
    
    /**
     * Portable newline character.
     */
    private static final String nl = System.getProperty("line.separator", "\n");

    public DecacCompiler(CompilerOptions compilerOptions, File source) {
        super();
        this.compilerOptions = compilerOptions;
        this.source = source;
    }

    /**
     * Source file associated with this compiler instance.
     */
    public File getSource() {
        return source;
    }

    /**
     * Compilation options (e.g. when to stop compilation, number of registers
     * to use, ...).
     */
    public CompilerOptions getCompilerOptions() {
        return compilerOptions;
    }

    /**
     * @see
     * AbstractCodeGenProgram#add(AbstractLine)
     */
    public void add(AbstractLine line) {
        program.add(line);
    }

    /**
     * @see AbstractCodeGenProgram#addComment(java.lang.String)
     */
    public void addComment(String comment) {
        program.addComment(comment);
    }

    /**
     * @see
     * AbstractCodeGenProgram#addLabel(Label)
     */
    public void addLabel(Label label) {
        program.addLabel(label);
    }

    /**
     * @see
     * AbstractCodeGenProgram#addInstruction(Instruction)
     */
    public void addInstruction(Instruction instruction) {
        program.addInstruction(instruction);
    }

    /**
     * @see
     * AbstractCodeGenProgram#addInstruction(Instruction,
     * java.lang.String)
     */
    public void addInstruction(Instruction instruction, String comment) {
        program.addInstruction(instruction, comment);
    }

    /**
     * Add instruction at the index i
     * @param i int
     * @param inst Instruction
     */
    public void addIndex(int i, Instruction inst) {
        program.addIndex(i, inst);
    }

    /**
     * Add multiple instructions at the index i
     * @param i int
     * @param l List<Line>
     */
    public void addAllIndex(int i, List<Line> l) {
        program.addAllIndex(i, l);
    }

    /**
     * Getter of the last index
     * @return int
     */
    public int getLineIndex(){
        return program.getLastIndex();
    }

    /**
     * Add a line at the index i
     * @param l Line
     */
    public void addFirst(Line l){
        program.addFirst(l);
    }

    /**
     * Add a line in the data section
     * @param l Line
     */
    public void addData(Line l) { program.addData(l);}
    /**
     * @see 
     * AbstractCodeGenProgram#display()
     */
    public String displayIMAProgram() {
        return program.display();
    }
    
    private final CompilerOptions compilerOptions;
    private final File source;
    /**
     * The main program. Every instruction generated will eventually end up here.
     */
    private AbstractCodeGenProgram program;

    /**
     * Getter for program
     * @return
     */
    public AbstractCodeGenProgram getProgram() {
        return program;
    }


    /** The global environment for types (and the symbolTable) */
    public final SymbolTable symbolTable = new SymbolTable();
    public final EnvironmentType environmentType = new EnvironmentType(this);

    public Symbol createSymbol(String name) {
        return this.symbolTable.create(name);
    }

    /**
     * Memory management
     */
    private final Memory memory = new Memory();

    /**
     * Getter for memory
     * @return memory
     */
    public Memory getMemory() {
        return memory;
    }

    /**
     * Get the next global offset
     * @return int
     */
    public int nextGlobalOffSet(){
        int val = memory.getGlobalOffset();
        memory.increaseGlobalOffset();
        return val;
    }

    /**
     * Get the next arm offset
     * @return int
     */
    public int getNextArmOffSet(){
        return memory.getArmOffset();
    }

    /**
     * Increase the arm offset
     * @param x int
     */
    public void increaseArmOffset(int x){
        memory.increaseArmOffset(x);
    }

    /**
     * Get the next local offset
     * @return int
     */
    public int nextLocalOffSet(){
        int val = memory.getLocalOffset();
        memory.increaseLocalOffset();
        return val;
    }

    /**
     * Label factory instance
     */
    private final LabelFactory labelFactory = new LabelFactory();

    /**
     * Getter for labelFactory
     * @return labelFactory
     */
    public LabelFactory getLabelFactory() {return labelFactory;}

    /**
     * Get the number of while
     * @return int
     */
    public int nbWhile(){return labelFactory.nbWhile();}
    /**
     * Get the number of not
     * @return int
     */
    public int nbNot(){return labelFactory.nbNot();}
    /**
     * Get the number of and
     * @return int
     */
    public int nbAnd(){return labelFactory.nbAnd();}
    /**
     * Get the number of or
     * @return int
     */
    public int nbOr(){return labelFactory.nbOr();}
    
    /**
     * Run the compiler (parse source file, generate code)
     *
     * @return true on error
     */
    public boolean compile() throws DecacFatalError {
        String sourceFile = source.getAbsolutePath();

        String destFile = sourceFile.substring(0, sourceFile.length() - 5) +
                (compilerOptions.getARMCompilation() ? ".s":".ass");

        PrintStream err = System.err;
        PrintStream out = System.out;
        LOG.debug("Compiling file " + sourceFile + " to assembly file " + destFile);
        try {
            return doCompile(sourceFile, destFile, out, err);
        } catch (LocationException e) {
            e.display(err);
            return true;
        } catch (DecacFatalError e) {
            err.println(e.getMessage());
            return true;
        } catch (StackOverflowError e) {
            LOG.debug("stack overflow", e);
            err.println("Stack overflow while compiling file " + sourceFile + ".");
            return true;
        } catch (Exception e) {
            LOG.fatal("Exception raised while compiling file " + sourceFile
                    + ":", e);
            err.println("Internal compiler error while compiling file " + sourceFile + ", sorry.");
            return true;
        } catch (AssertionError e) {
            LOG.fatal("Assertion failed while compiling file " + sourceFile
                    + ":", e);
            err.println("Internal compiler error while compiling file " + sourceFile + ", sorry.");
            return true;
        }
    }

    /**
     * Internal function that does the job of compiling (i.e. calling lexer,
     * verification and code generation).
     *
     * @param sourceName name of the source (deca) file
     * @param destName name of the destination (assembly) file
     * @param out stream to use for standard output (output of decac -p)
     * @param err stream to use to display compilation errors
     *
     * @return true on error
     */
    private boolean doCompile(String sourceName, String destName,
            PrintStream out, PrintStream err)
            throws DecacFatalError, LocationException {
        AbstractProgram prog = doLexingAndParsing(sourceName, err);
        if (prog == null) {
            LOG.info("Parsing failed");
            return true;
        }

        if(compilerOptions.getDecompilation()){
            prog.decompile(out);
            return false;
        }
        if(compilerOptions.getNoCheck()){
            labelFactory.setNoCheck(true);
        }
        assert(prog.checkAllLocations());

        prog.verifyProgram(this);
        assert(prog.checkAllDecorations());

        if(compilerOptions.getVerification()){
            return false;
        }

        program = (compilerOptions.getARMCompilation()) ? new ARMProgram() : new IMAProgram();

        if (compilerOptions.getARMCompilation()) {
            prog.armCodeGenProgram(this);
        } else {
            prog.codeGenProgram(this);
        }
        LOG.debug("Generated assembly code:" + nl + program.display());
        LOG.info("Output file assembly file is: " + destName);

        FileOutputStream fstream = null;
        try {
            fstream = new FileOutputStream(destName);
        } catch (FileNotFoundException e) {
            throw new DecacFatalError("Failed to open output file: " + e.getLocalizedMessage());
        }

        LOG.info("Writing assembler file ...");

        program.display(new PrintStream(fstream));
        LOG.info("Compilation of " + sourceName + " successful.");
        return false;
    }

    /**
     * Build and call the lexer and parser to build the primitive abstract
     * syntax tree.
     *
     * @param sourceName Name of the file to parse
     * @param err Stream to send error messages to
     * @return the abstract syntax tree
     * @throws DecacFatalError When an error prevented opening the source file
     * @throws DecacInternalError When an inconsistency was detected in the
     * compiler.
     * @throws LocationException When a compilation error (incorrect program)
     * occurs.
     */
    protected AbstractProgram doLexingAndParsing(String sourceName, PrintStream err)
            throws DecacFatalError, DecacInternalError {
        DecaLexer lex;
        try {
            lex = new DecaLexer(CharStreams.fromFileName(sourceName));
        } catch (IOException ex) {
            throw new DecacFatalError("Failed to open input file: " + ex.getLocalizedMessage());
        }
        lex.setDecacCompiler(this);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        parser.setDecacCompiler(this);
        return parser.parseProgramAndManageErrors(err);
    }

    public String displaySourceFile(){
        String[] error = this.getSource().toString().split("/");
        return error[error.length - 1];
    }

}
