package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.LabelIdentification;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //TODO : class objects.

        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        this.setType(compiler.environmentType.BOOLEAN);
        if(!((type1.isInt() || type1.isFloat()) && (type2.isInt() || type2.isFloat()))){
            if(!(this.getOperatorName().equals("==") || this.getOperatorName().equals("!="))){
                throw new ContextualError("Compare operation type mismatch", this.getLocation());
            }else{
                if(!(type1.isBoolean() && type2.isBoolean())){
                    throw new ContextualError("Compare operation type mismatch", this.getLocation());
                }
            }
        }
        return this.getType();
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        getLeftOperand().codeGenExpr(compiler, n);
        if (n < Register.RMAX) {
            getRightOperand().codeGenExpr(compiler, n + 1);
            compiler.addInstruction(new CMP(Register.getR(n), Register.getR(n+1)));

        } else {
            compiler.addInstruction(new PUSH(Register.getR(n)));
            getRightOperand().codeGenExpr(compiler, n);
            compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
            compiler.addInstruction(new POP(Register.getR(n)));
            compiler.addInstruction(new CMP(Register.getR(n), Register.R0));
        }
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        System.out.println("AbstractOpCmp");
        getLeftOperand().codeGenExpr(compiler,2);
        getRightOperand().codeGenExpr(compiler,3);
        //TODO en fonction de l'opérateur
    }
}
