package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import org.apache.log4j.Logger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.NullOperand;

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
        throw new UnsupportedOperationException("not yet implemented");
        // LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    void codeGenMethodTable(DecacCompiler compiler) {
        for(AbstractDeclClass AbstractC : getList()) {
            DeclClass C = (DeclClass) AbstractC;
            Identifier supperClassID = (Identifier) C.getSuperClass();
            supperClassID.getClassDefinition().setOperand(new RegisterOffset(compiler.nextOffSet(), Register.GB));
            if(supperClassID != null) {
                compiler.addInstruction(new LOAD(supperClassID.getClassDefinition().getOperand(), Register.R1));
                compiler.addInstruction(new PUSH(Register.R1));
            }
            else {
                compiler.addInstruction(new LOAD(new NullOperand(), Register.R1));
                compiler.addInstruction(new PUSH(Register.R1));
            }
        }
    }
}
