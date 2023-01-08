package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;

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
        this.setType(type1);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if(!(type1.sameType(type2) || type1.isFloat() && type2.isInt())){
            throw new ContextualError("Assign Type problem here", this.getLocation());
        }
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //TODO : understand and modify this function

        this.setType(this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass));
        this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if(!this.getType().sameType(this.getRightOperand().getType())){
            throw new ContextualError("Assign Type problem here", this.getLocation());
        }

        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }

}
