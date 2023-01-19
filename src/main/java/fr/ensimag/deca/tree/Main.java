package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.context.VoidType;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl21
 * @date 01/01/2023
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);

    private EnvironmentExp mainEnvironment;

    private ListDeclVar declVariables;
    private ListInst insts;
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        // LOG.debug("verify Main: start");
        // A FAIRE: Appeler méthodes "verify*" de ListDeclVarSet et ListInst.
        // Vous avez le droit de changer le profil fourni pour ces méthodes
        // (mais ce n'est à priori pas nécessaire).
//        LOG.debug("verify Main: end");
//        throw new UnsupportedOperationException("not yet implemented");

        this.mainEnvironment = new EnvironmentExp(null);
        // TO DO : Don't forget to add "equals" in the env_exp_object

        this.declVariables.verifyListDeclVariable(compiler, this.mainEnvironment, null);
        this.insts.verifyListInst(compiler, this.mainEnvironment, null, new VoidType(compiler.createSymbol("void")));
    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        compiler.addComment("Beginning of main instructions:");
        int indexTSTO = compiler.getLineIndex();
        compiler.addInstruction(new ADDSP(this.mainEnvironment.size()));
        declVariables.codeGenListDeclVar(compiler);
        insts.codeGenListInst(compiler);
        compiler.addIndex(indexTSTO, new TSTO(compiler.getMemory().TSTO()));
        compiler.getLabelFactory().createTestStack(compiler, indexTSTO);
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
