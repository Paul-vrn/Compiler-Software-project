package fr.ensimag.deca;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;


/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);

    public static final ExecutorService service = Executors.newFixedThreadPool(8);


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
        if (options.getSourceFiles().isEmpty()) {
            System.out.println("This is the " + "\033[1;91m" + "decac" + "\u001B[0m" + " compiler\n" +
                    "To use it follow this syntax : \n" + "\033[1;37m" + "decac [[-p | -v] [-n] [-r X] [-d]* [-P] [-w] <source deca file(s)>...] | [-b]\n" + "\u001B[0m"
                    + "Options : " +
                    "\n-p : After the lexing and parsing operations, decompiles the code and prints it in the terminal\n"+
                    "-v : Stops the program after the verification process (no output in the absence of problems)\n"+
                    "(note that option -p and -v are incompatible\n"+
                    "-n : No check, removes execution checks\n" +
                    "-r X : limits the number of available registers (4<= X <= 16)\n"+
                    "-d : Debug option, enables debug trace. Repeat the option to get more traces\n" +
                    "-P : Parallel, if there are multiple source files, compiles concurrently" +
                    "-ARM : Generates ARM assembly instead of IMA assembly\n");
        }
        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            List<Future> futures = new ArrayList<>();
            for (File source : options.getSourceFiles()){
                Future<Boolean> future = service.submit(new Task(new DecacCompiler(options, source)));
                futures.add(future);
            }

            for (Future future : futures){
                try {
                    if (!(boolean) future.get()){
                        error = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    private static class Task implements Callable<Boolean> {
        private DecacCompiler compiler;

        public Task(DecacCompiler compiler) {
            this.compiler = compiler;
        }

        @Override
        public Boolean call() throws Exception {
            return compiler.compile();
        }
    }
}
