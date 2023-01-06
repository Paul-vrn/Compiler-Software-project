package fr.ensimag.deca;

import java.io.File;
import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) throws DecacFatalError {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            System.out.println("\033[1;95m" + "Ratio Systems" + "\u001B[0m"); //print Banner in purple and return
            return;
        }
        if (options.getSourceFiles().isEmpty()) { //TODO spécifier le fichier dans lequel on envoie le code avec -p une fois qu'on sera lequel c'est
            System.out.println("This is the " + "\033[1;91m" + "decac" + "\u001B[0m" + " compiler\n" +
                    "To use it follow this syntax : \n" + "\033[1;37m" + "decac [[-p | -v] [-n] [-r X] [-d]* [-P] [-w] <source deca file(s)>...] | [-b]\n" + "\u001B[0m"
                    + "Options : " +
                    "\n-p : After the lexing and parsing operation, decompiles the code and send it to a file\n"+
                    "-v : Stops the program after the verification process (no output in the absence of problems)\n"+
                    "(note that option -p and -v are incompatible\n"+
                    "-n : No check, removes execution checks\n" +
                    "-r X : pas compris\n"+ //TODO marquer ce que ça veut dire quand on le saura
                    "-d : Debug option, enables debug trace. Repeat the option to get more traces\n" +
                    "-P : Parallel, if there are multiple source files, compiles concurrently");
        }
        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            throw new UnsupportedOperationException("Parallel build not yet implemented");
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
