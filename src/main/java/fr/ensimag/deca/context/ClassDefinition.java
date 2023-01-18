package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;
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
    
}
