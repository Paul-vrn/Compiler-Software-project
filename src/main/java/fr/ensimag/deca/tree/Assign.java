package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
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
    protected void codeGenInst(DecacCompiler compiler) {
        Identifier id = (Identifier) this.getLeftOperand();
        this.getRightOperand().codeGenExpr(compiler, 2);
        compiler.addInstruction(new STORE(Register.getR(2), id.getExpDefinition().getOperand()));
    }

    @Override
    public void armCodeGenInst(DecacCompiler compiler) {

    }

    @Override
    protected String getOperatorName() {
        return "=";
    }

}
