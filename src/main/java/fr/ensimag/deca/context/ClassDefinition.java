package fr.ensimag.deca.context;

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import org.apache.commons.lang.Validate;

import java.util.Map;

/**
 * Definition of a class.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ClassDefinition extends TypeDefinition {


    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public void incNumberOfFields() {
        this.numberOfFields++;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int n) {
        Validate.isTrue(n >= 0);
        numberOfMethods = n;
    }
    
    public int incNumberOfMethods() {
        numberOfMethods++;
        return numberOfMethods;
    }

    private int numberOfFields = 0;
    private int numberOfMethods = 0;
    
    @Override
    public boolean isClass() {
        return true;
    }
    
    @Override
    public ClassType getType() {
        // Cast succeeds by construction because the type has been correctly set
        // in the constructor.
        return (ClassType) super.getType();
    };

    public ClassDefinition getSuperClass() {
        return superClass;
    }

    private final EnvironmentExp members;
    private final ClassDefinition superClass; 

    public EnvironmentExp getMembers() {
        return members;
    }

    public void disjointUnion(DecacCompiler compiler, EnvironmentExp envExpf, EnvironmentExp envExpM) throws ContextualError {
        for(Map.Entry<SymbolTable.Symbol, ExpDefinition> entry : envExpf.dictionary.entrySet()){
            try{
                this.members.declare(entry.getKey(), entry.getValue());
            }catch (EnvironmentExp.DoubleDefException ignored){

            }
        }

        for(Map.Entry<SymbolTable.Symbol, ExpDefinition> entry : envExpM.dictionary.entrySet()){
            try{
                this.members.declare(entry.getKey(), entry.getValue());
            }catch (EnvironmentExp.DoubleDefException ignored){
                throw new ContextualError( compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Field already defined", this.getLocation());
            }
        }
    }

    public ClassDefinition(ClassType type, Location location, ClassDefinition superClass) {
        super(type, location);
        EnvironmentExp parent;
        if (superClass != null) {
            parent = superClass.getMembers();
        } else {
            parent = null;
        }
        members = new EnvironmentExp(parent);
        this.superClass = superClass;
    }

    public void codeGenMethodTable(DecacCompiler compiler, ArrayList<String> methods){
        ClassDefinition superClass = this.getSuperClass();
        if(!(this.getType().getName().getName().equals("Object"))) {
            ArrayList<String> miniMethods = new ArrayList<String>();
            for (Map.Entry<SymbolTable.Symbol, ExpDefinition> entry : this.members.dictionary.entrySet()) {
                if (entry.getValue().isMethod()) {
                    String MethodName = "code.";
                    MethodName += this.getType().getName().getName();
                    MethodDefinition meth = (MethodDefinition) entry.getValue();
                    MethodName += "." + meth.getLabel().toString();
                    miniMethods.add(0, MethodName);
                }
            }

            methods.addAll(miniMethods);
            superClass.codeGenMethodTable(compiler, methods);
        }
        else{
            methods.add("code.Object.equals");
            for(int i = methods.size()-1; i>=0; i--){
                String methodToAdd = methods.get(i);
                if(!(methodToAdd.equals("method.already.class.declared"))) {
                    for (int j = i; j >= 0; j--) {
                        if (methods.get(j).split("\\.")[2].equals(methodToAdd.split("\\.")[2])) {
                            methodToAdd = methods.get(j);
                            methods.set(j, "method.already.class.declared");
                        }
                    }
                    compiler.addInstruction(new LOAD(new LabelOperand(new Label(methodToAdd)), Register.getR(1)));
                    compiler.addInstruction(new PUSH(Register.getR(1)));
                    compiler.getMemory().increaseTopOfMethodTable();
                }
            }
        }



//        for(Map.Entry<SymbolTable.Symbol, ExpDefinition> entry : this.members.dictionary.entrySet()){
//            if(entry.getValue().isMethod()){
//                MethodDefinition method = (MethodDefinition) entry.getValue();
//                Label label = new Label("code."+ superClass.getType().getName().getName() + "." + method.getLabel().toString());
//                compiler.addInstruction(new LOAD(new LabelOperand(label), Register.getR(1)));
//                compiler.addInstruction(new PUSH(Register.getR(1)));
//                compiler.addInstruction(new ADDSP(1));
//            }
//        }
    }
    
}
