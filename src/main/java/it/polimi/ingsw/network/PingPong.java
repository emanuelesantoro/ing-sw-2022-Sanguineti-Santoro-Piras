package it.polimi.ingsw.network;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Class PingPong is used to create a timeout timer in order to check if the connection between the client and the server is still working. <br>
 * This class will make the client and the server send a {@link PingMessage} at a fixed rate and if the amount of time since the last ping received is
 * too big the connection will be closed. <br>
 * Both the client and the server contain a PingPong instance and therefore have their own timeout timer.
 */
public class PingPong {
    private static final boolean DEBUGGING = false;
    private volatile long time;
    long maxTime = 5000; //time that need to occur before closing the connection
    private final static long period = 2000;//frequency at which ping are send
    private final Timer pingPong;

    /**
     * Constructor PingPong creates a new instance of PingPong.
     *
     * @param pingPongController of type {@link PingPongInterface} - instance of the ControllerClient (Client side) / ClientHandler (Server side).
     */
    public PingPong(PingPongInterface pingPongController) {
        if (DEBUGGING) return;
        this.time = System.currentTimeMillis() + period;
        pingPong = new Timer();
        pingPong.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if ((System.currentTimeMillis() - time) > (maxTime + period)) {
                    pingPongController.quit();
                    quit();
                } else pingPongController.sendPingPong();
            }
        }, period, period);
    }

    /**
     * Method resetTime updates the time of the last ping received to the current one and updates the maximum time allowed based on the response time (capped at 15 seconds).
     */
    public void resetTime() {
        long delta = Math.abs(System.currentTimeMillis() - time - period);
        // updates the max time dynamically in function of the 10 * average response time and caps it between 1 and 15 seconds
        maxTime = Math.min(Math.max(Math.round((double) (maxTime + 20 * delta) / 2), 1000), 15000);
        time = System.currentTimeMillis();
    }

    public synchronized void quit(){
        pingPong.cancel();
        pingPong.purge();
    }
}
