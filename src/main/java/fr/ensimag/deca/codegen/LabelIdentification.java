package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

public class LabelIdentification {

    public static int nbLabelNot = 0;
    public static int nbLabelIfThenElse = 0;
    public static int nbLabelWhile = 0;
    public static int nbAnd = 0;
    public static int nbOr = 0;
    public static int printBool = 0;

    private LabelIdentification(){}

    public static void printBool(DecacCompiler compiler){
        Label label = new Label("printBool_" + printBool);
        Label labelEnd = new Label("printBoolEnd_" + printBool);
        compiler.addInstruction(new CMP(1, Register.R1));
        compiler.addInstruction(new BEQ(label));
        compiler.addInstruction(new WSTR("false"));
        compiler.addInstruction(new BRA(labelEnd));
        compiler.addLabel(label);
        compiler.addInstruction(new WSTR("true"));
        compiler.addLabel(labelEnd);
        printBool++;
    }
}
