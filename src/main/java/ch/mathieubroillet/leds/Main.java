package ch.mathieubroillet.leds;

import ch.mathieubroillet.leds.utils.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String RASPBERRY_IP = "192.168.1.136";
    private static final String TOURMATHIEU_IP = "192.168.1.130";
    private static final Led[] LEDS = {
            new Led("192.168.1.134", TOURMATHIEU_IP, "LED_BUREAU")
    };

    private static final int MAX_RETRY = 2;
    private static int retryCount = 0;

    public static void main(String[] args) {
        Logger.info("Starting...");
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            for (Led led : LEDS) {
                //if led is offline then stop.
                if (!led.isConnectedToNetwork()) {
                    return;
                }

                //Check if the target machine (the machine which needs to be on to power on the led) is ON.
                if (led.isTargetMachineIsOn()) {

                    //if led is off, then turn it on
                    if (!led.isOn()) {
                        led.turnOn();
                        Logger.info("Turning ON " + led.getName());
                    }
                } else {
                    if (led.isOn()) {
                        Logger.warn("Cannot contact target machine (" + led.getTargetMachineIP() + ") turning " + led.getName() + " off at 5 (" + (retryCount + 1) + "/" + MAX_RETRY + ") !");
                        retryCount++;

                        if (retryCount >= MAX_RETRY) {
                            if (led.isOn()) {
                                led.turnOff();
                                Logger.info("Turning OFF " + led.getName());
                                retryCount = 0;
                            }
                        }
                    } else {
                        Logger.info("Led and target are off. OK !");
                    }
                }
            }

        }, 0, 20, TimeUnit.SECONDS);
    }
}
