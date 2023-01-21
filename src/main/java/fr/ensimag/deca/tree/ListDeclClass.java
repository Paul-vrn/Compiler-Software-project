package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import org.apache.log4j.Logger;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
        for (AbstractDeclClass current : this.getList()){
            current.verifyClass(compiler);
        }

        //TODO : env_typesr = env_types ? page 80
        LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        for (AbstractDeclClass current : this.getList()) {
            current.verifyClassMembers(compiler);
        }

    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        for(AbstractDeclClass current : this.getList()){
            current.verifyClassBody(compiler);
        }
    }


    void codeGenMethodTable(DecacCompiler compiler) {

        compiler.addInstruction(new LOAD(new NullOperand(), Register.getR(1)));
        compiler.addInstruction(new STORE(Register.getR(1), new RegisterOffset(compiler.nextGlobalOffSet(), Register.GB)));

        compiler.addInstruction(new LOAD(new LabelOperand(new Label("code.Object.equals")), Register.getR(1)));
        compiler.addInstruction(new STORE(Register.getR(1), new RegisterOffset(compiler.nextGlobalOffSet(), Register.GB)));

        Identifier dummyObjectIdentifier = new Identifier(compiler.createSymbol("Object"));
        ClassDefinition dummyObjectClass = new ClassDefinition(compiler.environmentType.OBJECT, null, null);
        dummyObjectIdentifier.setDefinition(dummyObjectClass);
        dummyObjectIdentifier.getClassDefinition().setOperand(new RegisterOffset(1, Register.GB));

        for(AbstractDeclClass c : getList()) {
            ClassDefinition currentClassDefinition = c.getName().getClassDefinition();
            Identifier superClass = (Identifier) c.getSuperClass();
            Identifier currentClass = (Identifier)c.getName();
            if(superClass.getName().getName().equals("Object")){
                compiler.addInstruction(new LEA(dummyObjectIdentifier.getClassDefinition().getOperand(), Register.getR(1)));
            }
            else {
                compiler.addInstruction(new LEA(superClass.getClassDefinition().getOperand(), Register.getR(1)));
            }
            currentClass.getClassDefinition().setOperand(new RegisterOffset(compiler.getMemory().getGlobalOffset(), Register.GB));
            compiler.addInstruction(new STORE(Register.getR(1), new RegisterOffset(compiler.nextGlobalOffSet(), Register.GB)));
            ArrayList<MethodDefinition> methods = new ArrayList<>();
            currentClassDefinition.codeGenMethodTable(compiler, methods);
        }
    }

    public void codeGenDeclClasses(DecacCompiler compiler) {
        DeclClass.codeGenObjectEquals(compiler);
        for(AbstractDeclClass c : getList()) {
            c.codeGenDeclClass(compiler);
        }
    }
}
