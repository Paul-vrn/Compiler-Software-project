package fr.ensimag.pseudocode;

public abstract class AbstractRegister extends DVal {

    protected String name;

    protected AbstractRegister(String name) {
        this.name = name;
    }

    /**
     * General Purpose Registers
     */

    @Override
    public String toString() {
        return name;
    }
}
