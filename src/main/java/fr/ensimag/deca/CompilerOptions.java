package fr.ensimag.deca;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.ensimag.pseudocode.RegisterARM;
import fr.ensimag.pseudocode.RegisterIMA;

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

    public boolean getNoCheck(){return noCheck;}

    public void enableNoCheck(){this.noCheck = true;}

    public boolean getARMCompilation(){return armCompilation;}

    public void enableARM(){this.armCompilation = true;}

    public void enableSansObjet() {
        this.sansObjet = true;
    }
    public boolean getSansObjet() {
        return sansObjet;
    }


    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;

    private boolean decompilation = false;
    private boolean verificationEnabled = false;
    private boolean printBanner = false;
    private boolean parallel = false;
    private boolean noCheck = false;
    private boolean armCompilation = false;
    private boolean sansObjet = false;
    private List<File> sourceFiles = new ArrayList<File>();

    public void parseArgs(String[] args) throws CLIException {



        ArrayList<String> argsArrayList = new ArrayList<>(Arrays.asList(args));
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
                        enableNoCheck();
                        break;
                    case "-r":
                        i++;
                        int newRMAX = Integer.parseInt(argsArrayList.get(i));
                        if(newRMAX >= 4 && newRMAX <= 16){
                            RegisterIMA.RMAX = newRMAX - 1;
                            RegisterARM.RMAX = newRMAX - 1;
                            RegisterARM.SMAX = newRMAX - 1;
                        }
                        else{
                            throw new UnsupportedOperationException("Number of registers must be : 4 <= RMAX <= 16");
                        }
                        break;
                    case "-d":
                        debug++;
                        break;
                    case "-dd":
                        debug = 2;
                        break;
                    case "-ddd":
                        debug = 3;
                        break;
                    case "-d{4,}":
                        debug = 4;
                        break;
                    case "-P":
                        enableParallel();
                        break;
                    case "-so":
                        enableSansObjet();
                        break;
                    default:
                        if (argsArrayList.get(i).matches("-d{4,}")) {
                            debug = 4;}
                        else {
                            throw new UnsupportedOperationException("Unknown option or incorrect file name/path : " + argsArrayList.get(i));
                        }
                }
                i++;
                if(i<argsArrayList.size()) {
                    file = new File(argsArrayList.get(i));
                }
                else{
                    break;
                }
            }

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
