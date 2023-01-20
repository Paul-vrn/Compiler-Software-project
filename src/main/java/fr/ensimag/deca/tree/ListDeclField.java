package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.tools.SymbolTable;

import java.util.Map;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl21
 * @date 01/01/2023
 */
public class ListDeclField extends TreeList<AbstractDeclField> {

    /**
     * Pass 2
     * @param compiler
     * @param superClass
     * @param name
     * @return
     * @throws ContextualError
     */
    protected EnvironmentExp verifyListDeclFieldPass2(DecacCompiler compiler, AbstractIdentifier superClass, AbstractIdentifier name)
            throws ContextualError {
        EnvironmentExp envExpR = new EnvironmentExp(superClass.getClassDefinition().getMembers());
        name.getClassDefinition().setNumberOfFields(superClass.getClassDefinition().getNumberOfFields());

        for (AbstractDeclField current : this.getList()){
            for(Map.Entry<SymbolTable.Symbol, ExpDefinition> entry :
                    current.verifyDeclFieldPass2(compiler, superClass, name).getDictionary().entrySet()){
                try {
                    envExpR.declare(entry.getKey(), entry.getValue());
                }catch (EnvironmentExp.DoubleDefException e){
                    throw new ContextualError( compiler.displaySourceFile() + ":"
                            + current.getLocation().errorOutPut() + ": Field already defined", current.getLocation());
                }
            }
        }

        return envExpR;
    }

    protected void verifyListDeclFieldPass3(DecacCompiler compiler, EnvironmentExp envExp, AbstractIdentifier name) throws ContextualError {
        for(AbstractDeclField current : this.getList()){
            current.verifyDeclFieldPass3(compiler, envExp, name);
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for ( AbstractDeclField current : this.getList()){
            current.decompile(s);
        }
    }

    public void codeGenDeclFieldNull(DecacCompiler compiler){
        for (AbstractDeclField current : this.getList()){
            current.codeGenDeclFieldNull(compiler);
        }
    }

    public void codeGenDeclField(DecacCompiler compiler) {
        for (AbstractDeclField declField : getList()) {
            declField.codeGenDeclField(compiler);
            compiler.getMemory().increaseTSTO();
        }
    }
}
