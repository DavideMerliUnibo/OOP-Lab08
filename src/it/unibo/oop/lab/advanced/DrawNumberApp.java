package it.unibo.oop.lab.advanced;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String USER = System.getProperty("user.home");
    private static final String FILENAME = "config.yml";
    private static final String FILEPATH = USER + SEPARATOR + "Desktop" + SEPARATOR + "Università"
                                           + SEPARATOR + "Programmazione a Oggetti" + SEPARATOR
                                           + "Laboratory" + SEPARATOR + "OOP-Lab08" + SEPARATOR
                                           + "res" + SEPARATOR + FILENAME;
    
    private int min;
    private int max;
    private int attempts;
    
    private final DrawNumber model;
    private final DrawNumberView view;

    /**
     * 
     */
    public DrawNumberApp() {
        initializeSettings();
        this.model = new DrawNumberImpl(min, max, attempts);
        this.view = new DrawNumberViewImpl();
        this.view.setObserver(this);
        this.view.start();
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            this.view.result(result);
        } catch (IllegalArgumentException e) {
            this.view.numberIncorrect();
        } catch (AttemptsLimitReachedException e) {
            view.limitsReached();
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        System.exit(0);
    }
    
    public void initializeSettings() {
        try (            
            InputStream inStream = new FileInputStream(FILEPATH);
            InputStream buffStream = new BufferedInputStream(inStream);
            DataInputStream dataStream = new DataInputStream(buffStream);
        ){
            for(String line = dataStream.readLine(); line != null; line = dataStream.readLine()) {
                final String[] result = line.split(": ");
                if(result[0].contains("max")) {
                    max = Integer.parseInt(result[1]);
                }
                else if(result[0].contains("min")) {
                    min = Integer.parseInt(result[1]);
                }
                else if(result[0].contains("attempts")) {
                    attempts = Integer.parseInt(result[1]);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Configuration failed!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args
     *            ignored
     */
    public static void main(final String... args) {
        new DrawNumberApp();
        
    }

}
