package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.ima.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Class for new
 * Exemple : A a = new A();
 *
 * @author gl21
 * @date 10/01/2023
 */
public class New extends AbstractExpr {

    public AbstractIdentifier typeNew;

    public New(AbstractIdentifier typeNew) {
        Validate.notNull(typeNew);
        this.typeNew = typeNew;
    }

    /**
     * VerifyExpr for New, throws an error if the type is not one of a class
     *
     * @param compiler     (contains the "env_types" attribute)
     * @param localEnv     Environment in which the expression should be checked
     *                     (corresponds to the "env_exp" attribute)
     * @param currentClass Definition of the class containing the expression
     *                     (corresponds to the "class" attribute)
     *                     is null in the main bloc.
     * @return
     * @throws ContextualError
     */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.typeNew.verifyType(compiler);
        this.setType(type1);
        // Throws the error "New type must be a class" if the type is not one of a class. Ex: int x = new int();
        if (!type1.isClass()) {
            throw new ContextualError(compiler.displaySourceFile() + ":"
                    + this.typeNew.getLocation().errorOutPut() + ": New type must be a class", this.typeNew.getLocation());
        }

        return type1;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        this.typeNew.decompile(s);
        s.print("()");
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
        compiler.addInstruction(new NEW(typeNew.getClassDefinition().getNumberOfFields() + 1, RegisterIMA.getR(n)));
        compiler.getLabelFactory().createHeapOverflow(compiler);
        compiler.addInstruction(new LEA(typeNew.getClassDefinition().getOperand(), RegisterIMA.getR(1)));
        compiler.addInstruction(new STORE(RegisterIMA.getR(1), new RegisterOffset(0, RegisterIMA.getR(n))));
        compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
        compiler.addInstruction(new BSR(new Label("init." + typeNew.getName().getName())));
        compiler.addInstruction(new POP(RegisterIMA.getR(n)));
    }
}