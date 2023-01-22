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

    private boolean noCheck;
    private int nbNot;
    private int nbIfThenElse;
    private int nbWhile;
    private int nbAnd;
    private int nbOr;
    private int nbOpComp;
    private int nbString;
    private int nbInt;
    private int nbFloat;
    private boolean flagOverflowError;
    private boolean flagStackError;
    private boolean flagIOError;
    private boolean flagDivByZeroError;
    private boolean flagDeferencementNullError;
    private boolean flagHeapOverflowError;
    private static final Label overflowErrorLabel = new Label("overflow_error");
    private static final Label stackErrorLabel = new Label("stack_error");
    private static final Label ioErrorLabel = new Label("io_error");
    private static final Label DivByZeroErrorLabel = new Label("div_by_zero_error");
    private static final Label NoReturnErrorLabel = new Label("no_return_error");
    private static final Label DeferencementNullErrorLabel = new Label("deferencement_null");
    private static final Label HeapOverflowErrorLabel = new Label("heap_overflow_error");
    private static final Label divByZeroErrorLabel = new Label("div_by_zero_error");

    /* ARM labels */
    private static final Label printfLabel = new Label("printf");
    private static final Label scanfLabel = new Label("scanf");
    private boolean flagLabelInt;
    private static final Label LabelInt = new Label("int");
    private boolean flagLabelFloat;
    private static final Label LabelFloat = new Label("float");
    private boolean flagLabelLn;
    private static final Label LabelLn = new Label("ln");

    private static String suffixCurrentMethod;
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
    }

    public int nbNot(){
        int i = nbNot;
        nbNot++;
        return i;
    }
    public int NbOpComp() {
        int i = nbOpComp;
        nbOpComp++;
        return i;
    }
    public int getNbIfThenElse() {
        return nbIfThenElse;
    }
    public void setNbIfThenElse(int nbIfThenElse) {
        this.nbIfThenElse = nbIfThenElse;
    }
    public int nbWhile(){
        int i = nbWhile;
        nbWhile++;
        return i;
    }
    public int nbAnd(){
        int i = nbAnd;
        nbAnd++;
        return i;
    }
    public int nbOr(){
        int i = nbOr;
        nbOr++;
        return i;
    }

    public int nbString(){
        int i = nbString;
        nbString++;
        return i;
    }
    public int nbFloat(){
        int i = nbFloat;
        nbFloat++;
        return i;
    }
    public int nbInt() {
        int i = nbInt;
        nbInt++;
        return i;
    }

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

    public void createTestOverflow(DecacCompiler compiler) {
        if (noCheck)
            return;
        flagOverflowError = true;
        compiler.addInstruction(new BOV(overflowErrorLabel));
    }

    public void createTestStack(DecacCompiler compiler, int i) {
        if (noCheck)
            return;
        flagStackError = true;
        compiler.addIndex(i+1, new BOV(stackErrorLabel));
    }
    public void createTestStack(List<Line> l, int i) {
        if (noCheck)
            return;
        flagStackError = true;
        l.add(i, new Line(new BOV(stackErrorLabel)));
    }
    public void createTestIo(DecacCompiler compiler){
        if (noCheck)
            return;
        flagIOError = true;
        compiler.addInstruction(new BOV(ioErrorLabel));
    }
    public void createTestReturn(DecacCompiler compiler){
        if (noCheck)
            return;
        compiler.addInstruction(new WSTR("Error: Exiting method "+ suffixCurrentMethod +" without a return statement"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
    }
    public void createTestDeferencementNull(DecacCompiler compiler, GPRegister r){
        if (noCheck)
            return;
        flagDeferencementNullError = true;
        compiler.addInstruction(new CMP(new NullOperand(), r));
        compiler.addInstruction(new BEQ(DeferencementNullErrorLabel));
    }
    public void createHeapOverflow(DecacCompiler compiler) {
        if (noCheck)
            return;
        flagHeapOverflowError = true;
        compiler.addInstruction(new BOV(HeapOverflowErrorLabel));
    }
    public Label getLabelInt(){
        flagLabelInt = true;
        return LabelFactory.LabelInt;
    }

    public Label getLabelFloat(){
        flagLabelFloat = true;
        return LabelFactory.LabelFloat;
    }
    public Label getLabelLn(){
        flagLabelLn = true;
        return LabelFactory.LabelLn;
    }

    public Label getPrintfLabel() {
        return printfLabel;
    }
    public Label getScanfLabel() { return scanfLabel; }


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

    public String getSuffixCurrentMethod() {
        return suffixCurrentMethod;
    }
    public void setSuffixCurrentMethod(String suffixCurrentMethod) {
        this.suffixCurrentMethod = suffixCurrentMethod;
    }
}
