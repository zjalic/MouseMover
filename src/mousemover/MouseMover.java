package mousemover;

import mousemover.exception.AliveException;
import mousemover.exception.NotAliveException;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseMover extends Thread {
    
    private static Long interval = 5000L;
    private static final Random RNG = new Random();
    private static MouseMover instance;
    private Robot robot;

    private MouseMover() {
        try {
            this.robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(MouseMover.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static MouseMover getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new MouseMover();
    }

    public static void enable() throws AliveException {
        if (MouseMover.getInstance().isAlive()) {
            throw new AliveException();
        } else {
            MouseMover.getInstance().start();
        }
    }
    
    public static void setInterval(Long l){
        interval = l;
    }
    
    public static Long getInterval(){
        return interval;
    }

    public static void disable() throws NotAliveException {
        if (MouseMover.getInstance().isAlive()) {
            MouseMover.getInstance().stop();
        } else {
            throw new NotAliveException();
        }
        instance = new MouseMover();
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {

        while (true) {
            try {

                for (int i = 0; i < 10; i++) {
                    robot.mouseMove(RNG.nextInt(150), RNG.nextInt(150));
                    Thread.sleep(250);
                }
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                Logger.getLogger(MouseMover.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
