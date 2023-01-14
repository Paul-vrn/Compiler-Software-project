package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import fr.ensimag.ima.pseudocode.Register;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl21
 * @date 01/01/2023
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public void enableParallel() {parallel = true;}

    public boolean getPrintBanner() {
        return printBanner;
    }

    public void enablePrintBanner(){this.printBanner = true;}

    public boolean getDecompilation(){
        return decompilation;
    }

    public void enableDecompilation(){
        this.decompilation = true;
    }

    public boolean getVerification(){return verificationEnabled;}

    public void enableVerification(){this.verificationEnabled = true;}

    public void enableARM(){this.optionARM = true;}

    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;

    private boolean decompilation = false;
    private boolean verificationEnabled = false;
    private boolean printBanner = false;
    private boolean parallel = false;

    private boolean optionARM = false;

    private List<File> sourceFiles = new ArrayList<File>();

    public void parseArgs(String[] args) throws CLIException {
        // A FAIRE : parcourir args pour positionner les options correctement.
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

        ArrayList<String> argsArrayList = new ArrayList<>(Arrays.asList(args));
        //TODO option specification page 103
        if(argsArrayList.contains("-b")){
            if(argsArrayList.size()==1){
                enablePrintBanner();
            }
            else{
                throw new UnsupportedOperationException("Option -b is a standalone option and should not be used with any other option or files");
            }
            return;
        }
        if(!argsArrayList.isEmpty()) {
            int i = 0;
            File file = new File(argsArrayList.get(i));
            while (!file.exists()) {
                switch (argsArrayList.get(i)) {
                    case "-ARM":
                        enableARM();
                        break;
                    case "-p":
                        enableDecompilation();
                        break;
                    case "-v":
                        enableVerification();
                        break;
                    case "-n":
                        throw new UnsupportedOperationException("-n not yet implemented");
                    case "-r":
                        i++;
                        int newRMAX = Integer.parseInt(argsArrayList.get(i));
                        if(newRMAX >= 4 && newRMAX <= 16){
                            Register.RMAX = newRMAX - 1;
                        }
                        else{
                            throw new UnsupportedOperationException("Number of registers must be : 4 <= RMAX <= 16");
                        }
                        break;
                    case "d":
                        throw new UnsupportedOperationException("-d not yet implemented");
                    case "-P":
                        enableParallel();
                        break;
                    default:
                        throw new UnsupportedOperationException("Unknown option or incorrect file name/path : " + argsArrayList.get(i));
                }
                i++;
                if(i<argsArrayList.size()) {
                    file = new File(argsArrayList.get(i));
                }
                else{
                    break;
                }
            }
            if(getDecompilation() && getVerification()){
                throw new UnsupportedOperationException("Options -p and -v are incompatible");
            }
            while(i < argsArrayList.size()) {
                file = new File(argsArrayList.get(i));
                if (file.exists()) {
                    sourceFiles.add(file);
                    i++;
                } else {
                    throw new UnsupportedOperationException("Unknown file" + argsArrayList.get(i));
                }

            }
        }
    }

    protected void displayUsage() {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
