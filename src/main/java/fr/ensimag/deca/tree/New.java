package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.Register;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class New extends AbstractExpr{

    public AbstractIdentifier typeNew;

    public New(AbstractIdentifier typeNew){
        Validate.notNull(typeNew);
        this.typeNew = typeNew;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.typeNew.verifyType(compiler);
        this.setType(type1);

        if(!type1.isClass()){
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.typeNew.getLocation().errorOutPut() + ": New type must be a class", this.typeNew.getLocation());
        }

        return type1;
    }

    @Override
    public void decompile(IndentPrintStream s) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.typeNew.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }

    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.addInstruction(new NEW(typeNew.getClassDefinition().getNumberOfFields()+1, Register.getR(n)));
        compiler.getLabelFactory().createHeapOverflow(compiler);
        compiler.addInstruction(new LEA(typeNew.getClassDefinition().getOperand(), Register.getR(1)));
        compiler.addInstruction(new STORE(Register.getR(1), new RegisterOffset(0, Register.getR(n))));
        compiler.addInstruction(new PUSH(Register.getR(n)));
        compiler.addInstruction(new BSR(new Label("init." + typeNew.getName().getName())));
        compiler.addInstruction(new POP(Register.getR(n)));
    }
}