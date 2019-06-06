package main;

import game.Liga;
import java.io.File;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ui.JFbase;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.toString());

    public static void main(String[] args) {

        //log4j customizado
        logger();

        new JFbase().run();
    }

    private static void logger() {

        File logFile = new File(new File("").getAbsolutePath(), "log4j.properties");
        System.out.println(logFile.exists());

        //PropertiesConfigurator is used to configure logger from properties file
        PropertyConfigurator.configure(logFile.toString());

        //Log in console in and log file
        logger.debug("Log4j appender configuration is successful !!");
    }
}
