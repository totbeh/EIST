package de.tum.in.ase.eist;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileAccessController {
    private final CapabilityManager capabilityManager;

    public FileAccessController(CapabilityManager capabilityManager) {
        this.capabilityManager = capabilityManager;
    }


    public void readFile(Application application, File file) throws SecurityException {
        if (capabilityManager.hasCapability(application, file, Permission.READ)) {
            System.out.println("Reading file " + file + " for application " + application);
            Path path = Paths.get(file.getAbsolutePath());
            try {
                byte[] bytes = java.nio.file.Files.readAllBytes(path);
                System.out.println("File contents: " + new String(bytes));
            } catch (java.io.IOException e) {
                System.out.println("Error reading file " + file + ": " + e.getMessage());
            }
        } else {
            throw new SecurityException("Application " + application + " does not have permission to read file " + file);
        }
    }

    public void writeFile(Application application, File file, String content) throws SecurityException {
        if (capabilityManager.hasCapability(application, file, Permission.WRITE)) {
            System.out.println("Writing file " + file + " for application " + application);
            Path path = Paths.get(file.getAbsolutePath());
            try {
                java.nio.file.Files.write(path, content.getBytes());
            } catch (java.io.IOException e) {
                System.out.println("Error writing file " + file + ": " + e.getMessage());
            }
        } else {
            throw new SecurityException("Application " + application + " does not have permission to write file " + file);
        }
    }

    public void executeFile(Application application, File file) throws SecurityException {
        if (capabilityManager.hasCapability(application, file, Permission.EXECUTE)) {
            System.out.println("Executing file " + file + " for application " + application);
            try {
                Runtime.getRuntime().exec(file.getAbsolutePath());
            } catch (java.io.IOException e) {
                System.out.println("Error executing file " + file + ": " + e.getMessage());
            }
        } else {
            throw new SecurityException("Application " + application + " does not have permission to execute file " + file);
        }
    }
}
