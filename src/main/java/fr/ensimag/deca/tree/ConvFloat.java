package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.RegisterIMA;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;


/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl21
 * @date 01/01/2023
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        this.setType(compiler.environmentType.FLOAT);
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        getOperand().codeGenExpr(compiler, n);
        compiler.addInstruction(new FLOAT(RegisterIMA.getR(n), RegisterIMA.getR(n)));
    }
}
