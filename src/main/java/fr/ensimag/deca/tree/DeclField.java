package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 *
 * 
 * @author gl21
 * @date 01/01/2023
 */
public class DeclField extends AbstractDeclField {
    private Visibility visibility;
    private AbstractIdentifier type;
    private AbstractIdentifier fieldName;
    private AbstractInitialization initialization;

    public DeclField(Visibility visibility, AbstractIdentifier type, AbstractIdentifier fieldName, AbstractInitialization initialization) {
        Validate.notNull(visibility);
        Validate.notNull(type);
        Validate.notNull(fieldName);
        Validate.notNull(initialization);
        this.visibility = visibility;
        this.type = type;
        this.fieldName = fieldName;
        this.initialization = initialization;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        visibility.decompile(s);
        s.print(" ");
        type.decompile(s);
        s.print(" ");
        fieldName.decompile(s);
        initialization.decompile(s);
        s.println(";");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.type.prettyPrint(s, prefix, false);
        this.fieldName.prettyPrint(s, prefix, true);
        this.initialization.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.type.iter(f);
        this.fieldName.iter(f);
        this.initialization.iter(f);
    }

    @Override
    protected EnvironmentExp verifyDeclFieldPass2(DecacCompiler compiler, AbstractIdentifier superClass,
                                        AbstractIdentifier name) throws ContextualError {
        Type type1 = this.type.verifyType(compiler);

        if(type1.isVoid()){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Type void forbidden in class fields", this.getLocation());
        }

        this.fieldName.setType(type1);
        this.fieldName.setDefinition(new FieldDefinition(this.type.getType(), getLocation(), this.visibility,
                                    name.getClassDefinition(), 0));

        if(superClass.getClassDefinition().getMembers().get(name.getName()) != null){
            if(!superClass.getClassDefinition().getMembers().get(name.getName()).isField()){
                throw new ContextualError( compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Field name conflict in super class", this.getLocation());
            }
        }

        EnvironmentExp envToReturn = new EnvironmentExp(superClass.getClassDefinition().getMembers());
        try{
            envToReturn.declare(name.getName(), this.fieldName.getFieldDefinition());
        }catch (EnvironmentExp.DoubleDefException ignored){}

        return envToReturn;
    }

    @Override
    public void codeGen(DecacCompiler compiler) {

    }
}
