package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.VCVTFS;
import fr.ensimag.pseudocode.arm.instructions.VMOV;
import fr.ensimag.pseudocode.ima.instructions.FLOAT;


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

    /**
     * float <-- int
     *
     * @param compiler
     * @param n        number of the register R (for integer)
     * @param m        number of the register S (for float)
     */
    @Override
    protected void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        getOperand().armCodeGenExpr(compiler, n, m);
        compiler.addInstruction(new VMOV(RegisterARM.getR(n), RegisterARM.getS(m)));
        compiler.addInstruction(new VCVTFS(RegisterARM.getS(m), RegisterARM.getS(m)));
    }
}
