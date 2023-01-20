package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
//import jdk.javadoc.internal.doclint.Env;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl21
 * @date 01/01/2023
 */
public class DeclClass extends AbstractDeclClass {

    private final AbstractIdentifier name;
    private AbstractIdentifier superClass;
    private final ListDeclField fieldSets;
    private final ListDeclMethod methods;

    public AbstractIdentifier getName() {
        return name;
    }

    public AbstractIdentifier getSuperClass(){
        return superClass;
    }

    public DeclClass(AbstractIdentifier name, AbstractIdentifier superClass,
                     ListDeclField lf, ListDeclMethod lm) {
        Validate.notNull(name);
        this.name = name;
        this.superClass = superClass;
        this.fieldSets = lf;
        this.methods = lm;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        name.decompile(s);
        if (superClass != null) {
            s.print(" extends ");
            superClass.decompile(s);
        }
        s.println(" {");
        s.indent();
        fieldSets.decompile(s);
        methods.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {

        //TODO: Comprendre où est envExpr ici et comment y accéder + faire le cas où c'est Object

        if(this.superClass == null){
            this.superClass = new Identifier(compiler.createSymbol("Object"));
            this.superClass.setLocation(Location.BUILTIN);
            this.superClass.setDefinition(compiler.environmentType.getEnvTypes().get(compiler.createSymbol("Object")));
        }else{
            if(compiler.environmentType.getEnvTypes().get(this.superClass.getName()) != null){
                this.superClass.setDefinition(compiler.environmentType.getEnvTypes().get(this.superClass.getName()));
            }else{
                throw new ContextualError( compiler.displaySourceFile() + ":"
                        + this.getLocation().errorOutPut() + ": Extends a class that doesn't exist", this.getLocation());
            }

        }

        ClassType classtype = new ClassType(name.getName(),getLocation(), superClass.getClassDefinition());
        this.name.setType(classtype);
        this.name.setDefinition(new ClassDefinition(classtype, getLocation(), superClass.getClassDefinition()));

        try{
            compiler.environmentType.declare(name.getName(), (TypeDefinition) name.getDefinition());
        }catch(EnvironmentType.DoubleDefException e){
            throw new ContextualError( compiler.displaySourceFile() + ":"
                    + this.getLocation().errorOutPut() + ": Class name already used", this.getLocation());
        }

    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {

        EnvironmentExp envExpF = this.fieldSets.verifyListDeclFieldPass2(compiler, superClass, name);
        EnvironmentExp envExpM = this.methods.verifyListDeclMethodPass2(compiler,superClass,name);

        //if(superClass.getClassDefinition().getMembers().get(name.getName()) != null){
        ClassDefinition newDef = new ClassDefinition(this.name.getClassDefinition().getType(), this.getLocation(), this.superClass.getClassDefinition());
        newDef.disjointUnion(compiler, envExpF, envExpM);

        for(Map.Entry<SymbolTable.Symbol, ExpDefinition> entry : newDef.getMembers().getDictionary().entrySet()){
            try {
                this.name.getClassDefinition().getMembers().declare(entry.getKey(), entry.getValue());
            }catch (EnvironmentExp.DoubleDefException ignored){}
        }
        //}

    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        this.fieldSets.verifyListDeclFieldPass3(compiler, this.name.getClassDefinition().getMembers(), this.name);
        this.methods.verifyListDeclMethodPass3(compiler, this.name.getClassDefinition().getMembers(), this.name);
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.name.prettyPrint(s, prefix, false);
        if (this.superClass != null) {
            this.superClass.prettyPrint(s, prefix, true);
        }
        this.fieldSets.prettyPrint(s, prefix, true);
        this.methods.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.name.iter(f);
        if (this.superClass != null) {
            this.superClass.iter(f);
        }
        this.fieldSets.iter(f);
        this.methods.iter(f);
    }

    public static void codeGenObjectEquals(DecacCompiler compiler){
        Label labelEnd = new Label("fin.Object.equals");
        compiler.addLabel(new Label("code.Object.equals"));
        //On utilise R0 et R1 pas sûr de devoir TSTO
        //compiler.addInstruction(new TSTO(1));
        //ajouter l'instruciton BOV une fois que le code pour le message d'erreur de stack overflow existera
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.getR(1)));
        compiler.addInstruction(new CMP(new RegisterOffset(-3, Register.LB), Register.getR(1)));
        compiler.addInstruction((new SEQ(Register.getR(0))));
        compiler.addLabel(labelEnd);
        compiler.addInstruction(new RTS());

    }

    public void codeGenDeclClass(DecacCompiler compiler) {
        // init.X
        List<Line> preInit = new ArrayList<>();
        compiler.getMemory().resetLastGRegister();

        compiler.addLabel(new Label("init." + this.name.getName().getName()));
        int indexTSTO = compiler.getLineIndex();
        if (superClass.getClassDefinition().getNumberOfFields() > 0){
            fieldSets.codeGenDeclFieldNull(compiler);
            compiler.addInstruction(new PUSH(Register.getR(1)));
            compiler.addInstruction(new BSR(new Label("init." + this.superClass.getName().getName())));
            compiler.addInstruction(new SUBSP(1));
        }
        fieldSets.codeGenDeclField(compiler);
        if (compiler.getMemory().getLastGRegister() > 1) {
            for (int i = 2; i < compiler.getMemory().getLastGRegister()+1; i++) {
                preInit.add(new Line(new PUSH(Register.getR(i))));
                compiler.getMemory().increaseTSTO();
                compiler.addInstruction(new POP(Register.getR(compiler.getMemory().getLastGRegister()-(i-2))));
            }
        }
        preInit.add(0, new Line(new TSTO(compiler.getMemory().TSTO())));
        preInit.add(1, new Line(new BOV(compiler.getLabelFactory().getStackErrorLabel())));
        compiler.addAllIndex(indexTSTO, preInit);
        compiler.addInstruction(new RTS());
        compiler.getMemory().resetLastGRegister();
        // codeGenDeclMethod
        methods.codeGenDeclMethod(compiler, this.name.getName().getName());

    }


}
