package fr.ensimag.deca.codegen;

public class MemoryArm {
    private int offset;
    private int lastGRegister;


    public MemoryArm() {
        this.offset = 1;
        this.lastGRegister = 2;
    }

    public int getOffset() {
        return offset;
    }
    public void increaseOffset(int i) {
        offset += i;
    }
    public void increaseOffset() {
        increaseOffset(1);
    }

}
