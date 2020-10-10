package ch.mathieubroillet.leds;

import ch.mathieubroillet.leds.utils.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String RASPBERRY_IP = "192.168.1.136";
    private static final String TOURMATHIEU_IP = "192.168.1.130";
    private static final Led[] LEDS = {
            new Led("192.168.1.134", TOURMATHIEU_IP, "LED_BUREAU"),
            new Led("192.168.1.219", TOURMATHIEU_IP, "LED_LIT")
    };

    private static final int MAX_RETRY = 2;
    private static int retryCount = 0;

    public static void main(String[] args) {
        Logger.info("Starting...");
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            for (Led led : LEDS) {

                //If led is offline then stop.
                if (!led.isConnectedToNetwork()) {
                    Logger.warn(led.getName() + " isn't connected to the network.");
                    break;
                }

                //Check if the target machine (the machine which needs to be on to power on the led) is ON.
                if (led.isTargetMachineOn()) {
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

        }, 0, 10, TimeUnit.SECONDS);
    }

    public void testColors() {

        ArrayList<Color> colors = new ArrayList<Color>();

        colors.add(new Color(0xFFFF00));
        colors.add(new Color(0xFF6700));
        colors.add(new Color(0xFF0000));
        colors.add(new Color(0xFF0080));
        colors.add(new Color(0xFF00FF));
        colors.add(new Color(0x8200FF));
        colors.add(new Color(0x0031FF));
        colors.add(new Color(0x0089FF));
        colors.add(new Color(0x00f1ff));
        colors.add(new Color(0x00FF76));
        colors.add(new Color(0x00C700));
        colors.add(new Color(0x00FF00));

        new Thread(() -> {
            while (true) {
                colors.forEach(color -> {
                    LedsUtils.setColor(LEDS[0].getIp(), color);
                    LedsUtils.setColor(LEDS[1].getIp(), color);
                    try {
                        TimeUnit.SECONDS.sleep((long) 0.5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();

    }
}
