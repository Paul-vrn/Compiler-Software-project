package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;

/**
 * Definition associated to identifier in expressions.
 *
 * @author gl21
 * @date 01/01/2023
 */
public abstract class ExpDefinition extends Definition {



    public ExpDefinition(Type type, Location location) {
        super(type, location);
    }

}
