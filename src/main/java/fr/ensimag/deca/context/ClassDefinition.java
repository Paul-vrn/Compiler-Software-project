package fr.ensimag.deca.context;

import java.util.ArrayList;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.LabelOperand;
import fr.ensimag.pseudocode.RegisterIMA;
import fr.ensimag.pseudocode.RegisterOffset;
import fr.ensimag.pseudocode.ima.instructions.LOAD;
import fr.ensimag.pseudocode.ima.instructions.STORE;
import org.apache.commons.lang.Validate;

import java.util.Comparator;
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

    public void codeGenMethodTable(DecacCompiler compiler, ArrayList<MethodDefinition> methods){
        ClassDefinition currentSuperClass = this.getSuperClass();
        if(!(this.getType().getName().getName().equals("Object"))) {
            ArrayList<MethodDefinition> miniMethods = new ArrayList<>();
            this.members.dictionary.entrySet().stream()
                    .filter(entry -> entry.getValue().isMethod())
                    .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(value -> ((MethodDefinition) value).getIndex()).reversed()))
                    .forEach(entry -> {
                        MethodDefinition methDef = (MethodDefinition) entry.getValue();
                        methDef.setFullName("code." + this.getType().getName().getName() + "." + methDef.getLabel().toString());
                        miniMethods.add(methDef);
                    });
            methods.addAll(miniMethods);
            currentSuperClass.codeGenMethodTable(compiler, methods);
        }
        else{
            MethodDefinition dummyObjectEquals = new MethodDefinition(null, null, null, 0);
            dummyObjectEquals.setFullName("code.Object.equals");
            methods.add(dummyObjectEquals);

            MethodDefinition dummyMethod = new MethodDefinition(null, null, null, 0);
            dummyMethod.setFullName("method.already.class.declared");

            for(int i = methods.size()-1; i>=0; i--){
                MethodDefinition methodToAdd = methods.get(i);
                if(!(methodToAdd.getFullName().equals("method.already.class.declared"))) {
                    for (int j = i; j >= 0; j--) {
                        if (methods.get(j).getFullName().split("\\.")[2].equals(methodToAdd.getFullName().split("\\.")[2])) {
                            methodToAdd = methods.get(j);
                            methods.set(j, dummyMethod);
                        }
                    }
                    compiler.addInstruction(new LOAD(new LabelOperand(new Label(methodToAdd.getFullName())), RegisterIMA.getR(1)));
                    compiler.addInstruction(new STORE(RegisterIMA.getR(1), new RegisterOffset(compiler.nextGlobalOffSet(), RegisterIMA.GB)));
                    compiler.getMemory().increaseTSTO();
                    methodToAdd.setOperand(new RegisterOffset(compiler.getMemory().getGlobalOffset(), RegisterIMA.GB));
                }
            }
        }
    }
}

