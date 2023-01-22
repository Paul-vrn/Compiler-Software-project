package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.VoidType;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.pseudocode.ImmediateInteger;
import fr.ensimag.pseudocode.Label;
import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.arm.instructions.*;
import fr.ensimag.pseudocode.ima.instructions.ADDSP;
import fr.ensimag.pseudocode.ima.instructions.POP;
import fr.ensimag.pseudocode.ima.instructions.PUSH;
import fr.ensimag.pseudocode.ima.instructions.TSTO;
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
        declVariables.codeGenListDeclVar(compiler);
        insts.codeGenListInst(compiler);
    }

    @Override
    protected void armCodeGenMain(DecacCompiler compiler) {
        compiler.addComment("Beginning of main instructions:");
        compiler.addInstruction(new PUSHARM(RegisterARM.FP, RegisterARM.LR)); // PUS {FP, LR}
        compiler.addInstruction(new ADDS(new ImmediateInteger(4), RegisterARM.SP, RegisterARM.FP)); // ADDS FP, SP, #4
        compiler.addInstruction(new SUBS(new ImmediateInteger(this.mainEnvironment.size()*4 +(this.mainEnvironment.size()%2==0?0:4)), RegisterARM.SP)); // SUBS SP, SP, nb_var*4

        declVariables.armCodeGenListDeclVar(compiler);
        insts.armCodeGenListInst(compiler);
        compiler.addInstruction(new ADDS(new ImmediateInteger(this.mainEnvironment.size()*4+(this.mainEnvironment.size()%2==0?0:4)), RegisterARM.SP));
        compiler.addInstruction(new POPARM(RegisterARM.FP, RegisterARM.PC)); // POP {fp, pc}
        compiler.addInstruction(new MOV(new ImmediateInteger(0), RegisterARM.getR(0)));
        compiler.addInstruction(new BL(new Label("exit")));
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
