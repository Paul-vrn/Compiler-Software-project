package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

public class LabelFactory {

    public static int nbLabelNot = 0;
    public static int nbLabelIfThenElse = 0;
    public static int nbLabelWhile = 0;
    public static int nbAnd = 0;
    public static int nbOr = 0;

    private static boolean flagOverflowError = false;
    private static boolean flagStackError = false;
    private static boolean flagIOError = false;
    private static boolean flagDivByZeroError = false;
    private static final Label overflowErrorLabel = new Label("overflow_error");
    private static final Label stackErrorLabel = new Label("stack_error");
    private static final Label ioErrorLabel = new Label("io_error");
    private static final Label DivByZeroErrorLabel = new Label("div_by_zero_error");

    public static void createErrorSection(DecacCompiler compiler){
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
            compiler.addLabel(DivByZeroErrorLabel);
            compiler.addInstruction(new WSTR("Error: Division by zero"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }
    public static Label createOverflowErrorLabel(){
        flagOverflowError = true;
        return overflowErrorLabel;
    }
    public static Label createStackErrorLabel(){
        flagStackError = true;
        return stackErrorLabel;
    }
    public static Label createIOErrorLabel(){
        flagIOError = true;
        return ioErrorLabel;
    }

    public static Label createDivByZeroErrorLabel(){
        flagDivByZeroError = true;
        return DivByZeroErrorLabel;
    }

    public void createOverFlowErrorCode(DecacCompiler compiler){

    }

}
