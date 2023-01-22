package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.io.PrintStream;

import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.ima.instructions.*;
import fr.ensimag.pseudocode.LabelOperand;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.arm.instructions.*;
import fr.ensimag.pseudocode.ima.instructions.LOAD;
import fr.ensimag.pseudocode.ima.instructions.WFLOAT;
import fr.ensimag.pseudocode.ima.instructions.WINT;
import org.apache.commons.lang.Validate;

/**
 * Deca Identifier
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Identifier extends AbstractIdentifier {
    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;


    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }



    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        if(localEnv.get(this.getName()) != null){
            this.setType(localEnv.get(this.getName()).getType());
            this.setDefinition(localEnv.get(this.getName()));
        }
        else{
            this.setDefinition(new VariableDefinition(this.getType(), this.getLocation()));
        }
        return this.getType();
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {

        /* Verifies if the type exists */
        if(compiler.environmentType.defOfType(this.getName()) == null){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Unknown type", this.getLocation());
        }

        this.setType(compiler.environmentType.defOfType(this.getName()).getType());
        this.setDefinition(compiler.environmentType.defOfType(this.getName()));
        return this.getType();
    }

    @Override
    public Definition verifyDefinition(DecacCompiler compiler, EnvironmentExp envExp) throws ContextualError {
        /* Verifies if the metho or field if declared and exists */
        if(envExp.get(this.getName()) != null){
            this.setDefinition(envExp.get(this.getName()));
        }else{
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Undeclared method or field", this.getLocation());
            }

        return this.definition;
    }

    private Definition definition;

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

    @Override
    public void codeGenStore(DecacCompiler compiler, int n) {
        // if is field
        compiler.getMemory().setLastGRegister(n);
        if (getExpDefinition().isField()) {
            if (n < RegisterIMA.RMAX) {
                compiler.addInstruction(new LOAD(getExpDefinition().getOperand(), RegisterIMA.getR(n+1)));
                compiler.addInstruction(new STORE(RegisterIMA.getR(n), new RegisterOffset(getFieldDefinition().getIndex(), RegisterIMA.getR(n+1))));
                compiler.getMemory().setLastGRegister(n+1);
            } else {
                compiler.addInstruction(new PUSH(RegisterIMA.getR(n)));
                compiler.getMemory().increaseTSTO();
                compiler.addInstruction(new LOAD(getExpDefinition().getOperand(), RegisterIMA.getR(n)));
                compiler.addInstruction(new LOAD(RegisterIMA.getR(n), RegisterIMA.R0));
                compiler.addInstruction(new POP(RegisterIMA.getR(n)));
                compiler.getMemory().decreaseTSTO();
                compiler.addInstruction(new STORE(RegisterIMA.getR(n), new RegisterOffset(getFieldDefinition().getIndex(), RegisterIMA.getR(0))));
            }
        } else {
            compiler.addInstruction(new STORE(RegisterIMA.getR(n), getExpDefinition().getOperand()));
        }
    }

    @Override
    public void codeGenExpr(DecacCompiler compiler, int n) {
        compiler.getMemory().setLastGRegister(n);

        compiler.addInstruction(new LOAD(this.getExpDefinition().getOperand(), RegisterIMA.getR(n)));
        if (this.getExpDefinition().isField()) {
            compiler.addInstruction(new LOAD(new RegisterOffset(getFieldDefinition().getIndex(), RegisterIMA.getR(n)), RegisterIMA.getR(n)));
        }
    }

    public void codeGenDeclVar(DecacCompiler compiler) {
        this.getExpDefinition().setOperand(new RegisterOffset(compiler.nextGlobalOffSet(), RegisterIMA.GB));
    }
    public void codeGenDeclField(DecacCompiler compiler, EnvironmentExp localEnvExpr) {
        localEnvExpr.get(this.getName()).setOperand(new RegisterOffset(compiler.nextLocalOffSet(), RegisterIMA.LB));
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new LOAD(this.getExpDefinition().getOperand(), RegisterIMA.R1));
        if (getType().isInt()) {
            compiler.addInstruction(new WINT());
        } else if (getType().isFloat()) {
            compiler.addInstruction(new WFLOAT());
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    protected void armCodeGenPrint(DecacCompiler compiler, boolean printHex) {
        if (getType().isInt()) {
            compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getLabelInt()), RegisterARM.getR(0)));
            compiler.addInstruction(new LDR(this.getExpDefinition().getOperand(), RegisterARM.getR(1)));
            compiler.addInstruction(new BL(compiler.getLabelFactory().getPrintfLabel()));
        } else {
            compiler.addInstruction(new LDR(new LabelOperand(compiler.getLabelFactory().getLabelFloat()), RegisterARM.getR(0)));
            compiler.addInstruction(new VLDR(this.getExpDefinition().getOperand(), RegisterARM.getS(16)));
            compiler.addInstruction(new VCVTDS(RegisterARM.getS(16), RegisterARM.getD(0)));
            compiler.addInstruction(new VMOV(RegisterARM.getD(0), RegisterARM.getR(3), RegisterARM.getR(2)));
            compiler.addInstruction(new BL(compiler.getLabelFactory().getPrintfLabel()));
        }
    }
    @Override
    public void armCodeGenExpr(DecacCompiler compiler, int n, int m) {
        if (getType().isFloat()) {
            compiler.addInstruction(new VLDR(this.getExpDefinition().getOperand(), RegisterARM.getS(m)));
        } else {
            compiler.addInstruction(new LDR(this.getExpDefinition().getOperand(), RegisterARM.getR(n)));
        }
    }
    public void armCodeGenDeclVar(DecacCompiler compiler){
        compiler.increaseArmOffset(4);
        this.getExpDefinition().setOperand(new RegisterOffset(compiler.getNextArmOffSet(), RegisterARM.FP));
    }
}
