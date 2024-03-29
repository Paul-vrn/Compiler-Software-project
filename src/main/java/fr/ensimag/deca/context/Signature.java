package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl21
 * @date 01/01/2023
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void popHead(){
        this.args.remove(0);
    }

    public void add(Type t) {
        args.add(t);
    }
    
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature signature = (Signature) o;
        return Objects.equals(args, signature.args);
    }
}
