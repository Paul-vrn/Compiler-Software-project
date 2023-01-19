package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class New extends AbstractExpr {


    final private AbstractIdentifier className;

    public New(AbstractIdentifier c) {
        Validate.notNull(c);
        className = c;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        className.decompile(s);
        s.print("()");
    }

    public void codeGen(DecacCompiler compiler) {
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        className.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        className.prettyPrint(s, prefix, true);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        return null;
    }

    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.addInstruction(new NEW(className.getClassDefinition().getNumberOfFields()+1, Register.getR(2)));
        //TODO add erreur tas plein in label factory
        compiler.addInstruction(new BOV(new Label("tas_plein")));
        compiler.addInstruction(new LEA(className.getClassDefinition().getOperand(), Register.getR(1)));
        compiler.addInstruction(new STORE(Register.getR(1), new RegisterOffset(0, Register.getR(2))));
        compiler.addInstruction(new BSR(new Label("init." + className.getName().getName())));
        compiler.addInstruction(new POP(Register.getR(2)));
    }
}
