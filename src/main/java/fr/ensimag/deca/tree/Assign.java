package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.STR;
import fr.ensimag.pseudocode.arm.instructions.VSTR;
import fr.ensimag.pseudocode.ima.instructions.STORE;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Throws an error if there is a missing type declaration.
     *
     * @param compiler contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass
     *          corresponds to the "class" attribute (null in the main bloc).
     * @param returnType
     *          corresponds to the "return" attribute (void in the main bloc).
     * @throws ContextualError
     */
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
                              ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        try {
            this.setType(type1);
        }catch(Exception e){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Missing type declaration", this.getLocation());
        }

        this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, type1));
    }

    /**
     * Throws an error if there is a missing type declaration.
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return
     * @throws ContextualError
     */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        try {
            this.setType(type1);
        }catch(Exception e){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Missing type declaration", this.getLocation());
        }

         this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, type1));

        return this.getType();
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        this.getRightOperand().codeGenExpr(compiler, n);
        this.getLeftOperand().codeGenStore(compiler, n);
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.codeGenExpr(compiler, 2);
    }

    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        Identifier id = (Identifier) this.getLeftOperand();
        this.getRightOperand().armCodeGenExpr(compiler, n, m);
        if (getType().isFloat()){
            compiler.addInstruction(new VSTR(RegisterARM.getS(m), id.getExpDefinition().getOperand()));
        } else {
            compiler.addInstruction(new STR(RegisterARM.getR(n), id.getExpDefinition().getOperand()));
        }
    }
    @Override
    public void armCodeGenInst(DecacCompiler compiler) {
        this.armCodeGenExpr(compiler, 4, 2);
    }

    @Override
    protected String getOperatorName() {
        return "=";
    }

}
