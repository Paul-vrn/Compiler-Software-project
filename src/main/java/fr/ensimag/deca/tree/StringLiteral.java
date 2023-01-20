package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.*;
import fr.ensimag.pseudocode.arm.instructions.ASCIZ;
import fr.ensimag.pseudocode.arm.instructions.BL;
import fr.ensimag.pseudocode.arm.instructions.LDR;
import fr.ensimag.pseudocode.ima.instructions.WSTR;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * String literal
 *
 * @author gl21
 * @date 01/01/2023
 */
public class StringLiteral extends AbstractStringLiteral {

    @Override
    public String getValue() {
        return value;
    }

    private String value;

    public StringLiteral(String value) {
        Validate.notNull(value);
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.setType(compiler.environmentType.STRING);
        return this.getType();
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new WSTR(new ImmediateString(value)));
    }

    @Override
    protected void armCodeGenPrint(DecacCompiler compiler, boolean printHex) {
        Label strLabel = new Label("str" + compiler.getLabelFactory().nbString());
        compiler.addData(new Line(strLabel, new ASCIZ(new ImmediateString(value))));
        compiler.addInstruction(new LDR(new LabelOperand(strLabel), RegisterARM.getR(0)));
        compiler.addInstruction(new BL(compiler.getLabelFactory().getPrintfLabel()));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("\"");
        s.print(getValue());
        s.print("\"");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
    
    @Override
    String prettyPrintNode() {return "StringLiteral (" + value + ")";}

}
