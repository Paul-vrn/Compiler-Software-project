package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.pseudocode.*;
import fr.ensimag.pseudocode.arm.instructions.ASCIZ;
import fr.ensimag.pseudocode.ima.instructions.*;

import java.util.List;

/**
 * Factory for labels.
 */
public class LabelFactory {

    private static String suffixCurrentMethod;
    private boolean noCheck;
    private int nbNot;
    private int nbIfThenElse;
    private int nbWhile;
    private int nbAnd;
    private int nbOr;
    private int nbOpComp;
    private int nbInstanceOf;
    /* Error labels and flags */
    private boolean flagOverflowError;
    private boolean flagStackError;
    private boolean flagIOError;
    private boolean flagDivByZeroError;
    private boolean flagDeferencementNullError;
    private boolean flagHeapOverflowError;
    private static final Label overflowErrorLabel = new Label("overflow_error");
    private static final Label stackErrorLabel = new Label("stack_error");
    private static final Label ioErrorLabel = new Label("io_error");
    private static final Label DeferencementNullErrorLabel = new Label("deferencement_null");
    private static final Label HeapOverflowErrorLabel = new Label("heap_overflow_error");
    private static final Label divByZeroErrorLabel = new Label("div_by_zero_error");

    /* ARM labels */
    private int nbString;
    private int nbInt;
    private int nbFloat;
    private boolean flagLabelInt;
    private boolean flagLabelFloat;
    private boolean flagLabelLn;
    private static final Label printfLabel = new Label("printf");
    private static final Label scanfLabel = new Label("scanf");
    private static final Label LabelInt = new Label("int");
    private static final Label LabelFloat = new Label("float");
    private static final Label LabelLn = new Label("ln");

    /**
     * Constructor for LabelFactory
     */
    public LabelFactory() {
        this.noCheck = false;
        this.nbNot = 0;
        this.nbIfThenElse = 0;
        this.nbWhile = 0;
        this.nbAnd = 0;
        this.nbOr = 0;
        this.flagOverflowError = false;
        this.flagStackError = false;
        this.flagIOError = false;
        this.flagDivByZeroError = false;
    }

    public boolean getNoCheck() {
        return noCheck;
    }
    /**
     * Setter for noCheck
     * @param noCheck boolean
     */
    public void setNoCheck(boolean noCheck) {
        this.noCheck = noCheck;
    }

    /**
     * Create the error section
     * @param compiler compiler
     */
    public void createErrorSection(DecacCompiler compiler){
        if (noCheck)
            return;
        compiler.addComment("--- Start of Error messages section ---");
        if (flagOverflowError){
            compiler.addLabel(overflowErrorLabel);
            compiler.addInstruction(new WSTR("Error: Overflow during arithmetic operation"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagStackError){
            compiler.addLabel(stackErrorLabel);
            compiler.addInstruction(new WSTR("Error: Stack Overflow"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagIOError){
            compiler.addLabel(ioErrorLabel);
            compiler.addInstruction(new WSTR("Error: Input/Output error"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagDivByZeroError){
            compiler.addLabel(divByZeroErrorLabel);
            compiler.addInstruction(new WSTR("Error: Division by zero"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagDeferencementNullError) {
            compiler.addLabel(DeferencementNullErrorLabel);
            compiler.addInstruction(new WSTR("Error: Deferencement of null"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagHeapOverflowError) {
            compiler.addLabel(HeapOverflowErrorLabel);
            compiler.addInstruction(new WSTR("Error: Heap Overflow"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        compiler.addComment("--- End of Error messages section ---");
    }

    public void createArmErrorSection(DecacCompiler compiler) {
        if (noCheck)
            return;
        compiler.addComment("--- Start of Error messages section ---");
        if (flagIOError){
            compiler.addLabel(ioErrorLabel);
            compiler.addInstruction(new WSTR("Error: Input/Output error"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        if (flagDivByZeroError){
            compiler.addLabel(divByZeroErrorLabel);
            compiler.addInstruction(new WSTR("Error: Division by zero"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        compiler.addComment("--- End of Error messages section ---");

    }

    /**
     * Return the number of the Not label and increment it
     * @return int
     */
    public int nbNot(){
        int i = nbNot;
        nbNot++;
        return i;
    }

    /**
     * Return the number of the OpComp label and increment it
     * @return int
     */
    public int NbOpComp() {
        int i = nbOpComp;
        nbOpComp++;
        return i;
    }

    public int nbInstanceOf() {
        int i = nbInstanceOf;
        nbInstanceOf++;
        return i;
    }
    /**
     * Getter for nbIfThenElse
     * @return int
     */
    public int getNbIfThenElse() {
        return nbIfThenElse;
    }

    /**
     * Setter for nbIfThenElse
     * @param nbIfThenElse int
     */
    public void setNbIfThenElse(int nbIfThenElse) {
        this.nbIfThenElse = nbIfThenElse;
    }

    /**
     * Return the number of the while label and increment it
     * @return int
     */
    public int nbWhile(){
        int i = nbWhile;
        nbWhile++;
        return i;
    }

    /**
     * Return the number of the and label and increment it
     * @return int
     */

    public int nbAnd(){
        int i = nbAnd;
        nbAnd++;
        return i;
    }

    /**
     * Return the number of the or label and increment it
     * @return int
     */
    public int nbOr(){
        int i = nbOr;
        nbOr++;
        return i;
    }

    /**
     * Return the number of the string label and increment it
     * @return int
     */
    public int nbString(){
        int i = nbString;
        nbString++;
        return i;
    }

    /**
     * Return the number of the float label and increment it
     * @return int
     */
    public int nbFloat(){
        int i = nbFloat;
        nbFloat++;
        return i;
    }

    /**
     * Return the number of the int label and increment it
     * @return int
     */
    public int nbInt() {
        int i = nbInt;
        nbInt++;
        return i;
    }

    /**
     * Create the test deferencement null
     * @param compiler compiler
     * @param r register
     * @param isInt boolean
     */
    public void createTestDiv0(DecacCompiler compiler, GPRegister r, boolean isInt) {
        if (noCheck)
            return;
        flagDivByZeroError = true;
        compiler.addInstruction(new CMP(
                isInt ? new ImmediateInteger(0) : new ImmediateFloat(0.0f),
                r
        ));
        compiler.addInstruction(new BEQ(divByZeroErrorLabel));
    }

    /**
     * Create the test heap overflow
     * @param compiler compiler
     */
    public void createTestOverflow(DecacCompiler compiler) {
        if (noCheck)
            return;
        flagOverflowError = true;
        compiler.addInstruction(new BOV(overflowErrorLabel));
    }

    /**
     * Create the test stack error
     * @param compiler compiler
     * @param i index
     */
    public void createTestStack(DecacCompiler compiler, int i) {
        if (noCheck)
            return;
        flagStackError = true;
        compiler.addIndex(i+1, new BOV(stackErrorLabel));
    }

    /**
     * Create the test stack error
     * @param l list of line
     * @param i index
     */
    public void createTestStack(List<Line> l, int i) {
        if (noCheck)
            return;
        flagStackError = true;
        l.add(i, new Line(new BOV(stackErrorLabel)));
    }

    /**
     * Create the test IO error
     * @param compiler compiler
     */
    public void createTestIo(DecacCompiler compiler){
        if (noCheck)
            return;
        flagIOError = true;
        compiler.addInstruction(new BOV(ioErrorLabel));
    }

    /**
     * Create the test return error
     * @param compiler compiler
     */
    public void createTestReturn(DecacCompiler compiler){
        if (noCheck)
            return;
        compiler.addInstruction(new WSTR("Error: Exiting method "+ suffixCurrentMethod +" without a return statement"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
    }

    /**
     * Create the deferencement null error
     * @param compiler compiler
     * @param r register
     */
    public void createTestDeferencementNull(DecacCompiler compiler, GPRegister r){
        if (noCheck)
            return;
        flagDeferencementNullError = true;
        compiler.addInstruction(new CMP(new NullOperand(), r));
        compiler.addInstruction(new BEQ(DeferencementNullErrorLabel));
    }

    /**
     * Create the heap overflow error
     * @param compiler compiler
     */
    public void createHeapOverflow(DecacCompiler compiler) {
        if (noCheck)
            return;
        flagHeapOverflowError = true;
        compiler.addInstruction(new BOV(HeapOverflowErrorLabel));
    }

    /**
     * get the label for int
     * @return the label for int
     */
    public Label getLabelInt(){
        flagLabelInt = true;
        return LabelFactory.LabelInt;
    }

    /**
     * get the label for float
     * @return the label for float
     */
    public Label getLabelFloat(){
        flagLabelFloat = true;
        return LabelFactory.LabelFloat;
    }

    /**
     * get the label for ln
     * @return the label for ln
     */
    public Label getLabelLn(){
        flagLabelLn = true;
        return LabelFactory.LabelLn;
    }

    /**
     * get the label for printf
     * @return the label for printf
     */
    public Label getPrintfLabel() {
        return printfLabel;
    }

    /**
     * get the label for scanf
     * @return the label for scanf
     */
    public Label getScanfLabel() { return scanfLabel; }

    /**
     * Create labels for ARM code
     * @param compiler the compiler
     */
    public void createPrintLabel(DecacCompiler compiler) {
        if (flagLabelInt) {
            compiler.addData(new Line(new Label("int"), new ASCIZ(new ImmediateString("%d"))));
        }
        if (flagLabelFloat){
            compiler.addData(new Line(new Label("float"), new ASCIZ(new ImmediateString("%f"))));
        }
        if (flagLabelLn){
            compiler.addData(new Line(new Label("ln"), new ASCIZ(new ImmediateString("\\n"))));
        }
    }

    /**
     * @return the suffixCurrentMethod
     */
    public String getSuffixCurrentMethod() {
        return suffixCurrentMethod;
    }

    /**
     * @param suffixCurrentMethod the suffixCurrentMethod to set
     */
    public void setSuffixCurrentMethod(String suffixCurrentMethod) {
        this.suffixCurrentMethod = suffixCurrentMethod;
    }


}
