package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

import fr.ensimag.pseudocode.LabelOperand;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.arm.instructions.*;
import fr.ensimag.pseudocode.ima.instructions.LOAD;
import fr.ensimag.pseudocode.ima.instructions.WFLOAT;
import fr.ensimag.pseudocode.ima.instructions.WFLOATX;
import fr.ensimag.pseudocode.ima.instructions.WINT;
import org.apache.commons.lang.Validate;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class AbstractExpr extends AbstractInst {
    /**
     * @return true if the expression does not correspond to any concrete token
     * in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    /**
     * Get the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }
    private Type type;

    @Override
    protected void checkDecoration() {
        if (getType() == null) {
            throw new DecacInternalError("Expression " + decompile() + " has no Type decoration");
        }
    }

    /**
     * Verify the expression for contextual error.
     *
     * implements non-terminals "expr" and "lvalue"
     *    of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return the Type of the expression
     *            (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Verify the expression in right hand-side of (implicit) assignments
     *
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute
     * @return this with an additional ConvFloat if needed...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass,
            Type expectedType)
            throws ContextualError {
        Type type2 = this.verifyExpr(compiler, localEnv, currentClass);

        if(type2 == null){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Right value undefined", this.getLocation());
        }

        if(!(type2.sameType(expectedType) || (expectedType.isFloat() && type2.isInt())
                || (type2.isClass() && expectedType.isClass() && type2.asClassType("", this.getLocation()).isSubClassOf(expectedType.asClassType("", this.getLocation()))))){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Right value type problem", this.getLocation());
        }

        if(expectedType.isFloat() && type2.isInt()){
            ConvFloat c = new ConvFloat(this);
            c.verifyExpr(compiler, localEnv, currentClass);
            return c;
        }

        return this;
    }


    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.verifyExpr(compiler, localEnv, currentClass);
    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *            Environment in which the condition should be checked.
     * @param currentClass
     *            Definition of the class containing the expression, or null in
     *            the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = this.verifyExpr(compiler, localEnv, currentClass);

        if(!type.isBoolean()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Condition argument of if/elseif or while should be a boolean", this.getLocation());
        }
    }

    /**
     * Generate code to print the expression
     * @param compiler compiler
     * @param printHex <true/> if we want to print in hexadecimal, <false/> otherwise
     */
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        this.codeGenExpr(compiler, 2);
        compiler.addInstruction(new LOAD(RegisterIMA.getR(2), RegisterIMA.R1));
        if (getType().isInt()){
            compiler.addInstruction(new WINT());
        } else if (getType().isFloat()){
            compiler.addInstruction(printHex ? new WFLOATX() : new WFLOAT());
        } else {
            throw new UnsupportedOperationException("not implemented implemented");
        }
    }


    /**
     * Generate code to print the expression in ARM assembly
     * @param compiler compiler
     * @param printHex <true/> if we want to print in hexadecimal, <false/> otherwise
     */
    protected void armCodeGenPrint(DecacCompiler compiler, boolean printHex) {
        this.armCodeGenExpr(compiler, 4, 2);
        if (getType().isInt()) {
            compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getLabelInt()), RegisterARM.getR(0)));
            compiler.addInstruction(new MOV(RegisterARM.getR(4), RegisterARM.getR(1)));
        } else if (getType().isFloat()){
            compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getLabelFloat()), RegisterARM.getR(0)));
            compiler.addInstruction(new VCVTDS(RegisterARM.getS(2), RegisterARM.getD(0)));
            compiler.addInstruction(new VMOV(RegisterARM.getD(0), RegisterARM.getR(3), RegisterARM.getR(2)));
        } else {
            throw new UnsupportedOperationException("type not printable");
        }
        compiler.addInstruction(new BL(compiler.getLabelFactory().getPrintfLabel()));
    }

    /**
     * Generate code for the instanciation
     * @param compiler compiler
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.codeGenExpr(compiler, 2);
    }

    /**
     * Generate code for the expression
     * @param compiler
     * @param n number of the register where the result is stored
     */
    protected void codeGenExpr(DecacCompiler compiler, int n) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Generate code for the expression in ARM assembly
     * @param compiler
     * @param n number of the register R (for integer)
     * @param m number of the register S (for float)
     */
    protected void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        throw new UnsupportedOperationException("not implemented");
    }


    /**
     * Generate code for the instanciation in ARM assembly
     * @param compiler
     */
    protected void armCodeGenInst(DecacCompiler compiler) {
        this.armCodeGenExpr(compiler, 4,2);
    }
    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }
}
