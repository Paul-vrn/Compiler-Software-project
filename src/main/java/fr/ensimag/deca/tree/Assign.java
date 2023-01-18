package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.RegisterARM;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.arm.instructions.STR;
import fr.ensimag.ima.pseudocode.instructions.STORE;

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

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //TODO : understand and modify this function

        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        try {
            this.setType(type1);
        }catch(Exception e){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Missing type declaration", this.getLocation());
        }

        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if(!(type1.sameType(type2) || (type1.isFloat() && type2.isInt()))){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Assign Type problem", this.getLocation());
        }

        return this.getType();
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        Identifier id = (Identifier) this.getLeftOperand();
        this.getRightOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new STORE(RegisterIMA.getR(n), id.getExpDefinition().getOperand()));
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.codeGenExpr(compiler, 2);
    }

    @Override
    public void armCodeGenExprInst(DecacCompiler compiler, int n, int m) {
        Identifier id = (Identifier) this.getLeftOperand();
        this.getRightOperand().armCodeGenExpr(compiler, n, m);
        if (getType().isFloat()){
            compiler.addInstruction(new STR(RegisterARM.getS(m), id.getExpDefinition().getOperand()));
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
