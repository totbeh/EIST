package de.tum.in.ase.eist;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public final class App {

    private App() {
    }

    public static void main(String[] args) {
        // TODO 4: Make sure to use the Configurator to configure the loggers root level
        //This is a workaround for a known issue when starting JavaFX applications
        Configurator.setRootLevel(Level.INFO);
        Logger logManager = LogManager.getLogger(UniversityApp.class);
        logManager.info("start");
        UniversityApp.startApp(args);
    }
}
