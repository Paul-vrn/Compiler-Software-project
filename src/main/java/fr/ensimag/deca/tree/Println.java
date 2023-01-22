package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.pseudocode.LabelOperand;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.arm.instructions.BL;
import fr.ensimag.pseudocode.arm.instructions.LDR;
import fr.ensimag.pseudocode.ima.instructions.WNL;



/**
 * @author gl21
 * @date 01/01/2023
 */
public class Println extends AbstractPrint {

    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printlnx)
     */
    public Println(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new WNL());
    }

    @Override
    public void armCodeGenInst(DecacCompiler compiler) {
        super.codeGenInstARM(compiler);
        compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getLabelLn()), RegisterARM.getR(0)));
        compiler.addInstruction(new BL(compiler.getLabelFactory().getPrintfLabel()));
    }

    @Override
    String getSuffix() {
        return "ln";
    }
}
