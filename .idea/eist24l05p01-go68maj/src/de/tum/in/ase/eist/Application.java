package de.tum.in.ase.eist;

import java.io.File;

public class Application {
    private String name;
    private final FileAccessController fileAccessController;

    /*
    TODO 1.1
        add its variables name and fileAccessController according to the UML diagram
        in the exercise description and initialize all variables in the constructor
     */
    public Application(String name, FileAccessController fileAccessController) {
        this.name = name;
        this.fileAccessController = fileAccessController;
    }
    /*
    TODO 1.2
        Introduce the method readFile(File), that calls the corresponding method of
        the FileAccessController with the required parameters. Please keep in mind that
        the method may throw a SecurityException, however exception handling is not required,
        as already done in the FileAccessController class.
     */
    public void readFile(File file) {
        fileAccessController.readFile(this,file);
    }

    /*
    TODO 1.3
        Introduce the method writeFile(File, String), that calls the corresponding
        method of the FileAccessController with the required parameters. Other than in the
        readFile(File) method, writeFile(File, String) also takes a parameter of type String
        for the content. Please keep in mind that the method may also throw a SecurityException,
        however exception handling is not required, as already done in the FileAccessController class.
     */
    public void writeFile(File file, String test) {
        fileAccessController.writeFile(this,file,test);
    }

    /*
    TODO 1.4
        Introduce the method executeFile(File), that calls the corresponding method of the
        FileAccessController with the required parameters. Please keep in mind that the method
        may also throw a SecurityException, however exception handling is not required,
        as already done in the FileAccessController class.
     */
    public void executeFile(File file) {
        fileAccessController.executeFile(this,file);
    }
}
