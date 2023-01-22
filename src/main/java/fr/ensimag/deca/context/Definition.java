package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.pseudocode.DAddr;

/**
 * Definition of an identifier.
 * A definition is a couple (nature, type).
 * 
 * @author gl21
 * @date 01/01/2023
 */
public abstract class Definition {
    @Override
    public String toString() {
        String res;
        res = getNature();
        if (location == Location.BUILTIN) {
            res += " (builtin)";
        } else {
            res += " defined at " + location;
        }
        res += ", type=" + type;
        return res;
    }

    public void setOperand(DAddr operand) {
        this.operand = operand;
    }

    public DAddr getOperand() {
        return operand;
    }
    private DAddr operand;

    public abstract String getNature();

    public Definition(Type type, Location location) {
        super();
        this.location = location;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;
    private Type type;
    public boolean isField() {
        return false;
    }
    
    public boolean isMethod() {
        return false;
    }

    public boolean isClass() {
        return false;
    }

    public boolean isParam() {
        return false;
    }

    /**
     * Return the same object, as type MethodDefinition, if possible. Throws
     * ContextualError(errorMessage, l) otherwise.
     */
    public MethodDefinition asMethodDefinition(String errorMessage, Location l)
            throws ContextualError {
        if (this instanceof MethodDefinition) // TODO potentially measure the performance compared to a comparison with getNature()
            return (MethodDefinition) this;
        throw new ContextualError(errorMessage, l);
    }
    
    /**
     * Return the same object, as type FieldDefinition, if possible. Throws
     * ContextualError(errorMessage, l) otherwise.
     */
    public FieldDefinition asFieldDefinition(String errorMessage, Location l)
            throws ContextualError {
        if (this instanceof FieldDefinition)
            return (FieldDefinition) this;
        throw new ContextualError(errorMessage, l);
    }

    public abstract boolean isExpression();

}
