package ch.mathieubroillet.leds;

import ch.mathieubroillet.leds.enums.EnumLedsStatus;
import ch.mathieubroillet.leds.utils.Logger;

import java.awt.*;

public class Led {
    String ip;
    String targetMachineIP;
    String name;

    public Led(String ip, String targetMachineIP, String name) {
        this.ip = ip;
        this.targetMachineIP = targetMachineIP;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public String getTargetMachineIP() {
        return targetMachineIP;
    }

    public boolean isTargetMachineOn() {
        return Utils.isDeviceConnectedToNetwork(getTargetMachineIP());
    }

    public String getName() {
        return name;
    }

    public EnumLedsStatus getStatus() {
        return LedsUtils.getStatus(getIp());
    }

    public boolean isOn() {
        return LedsUtils.isOn(getIp());
    }

    public void turnOn() {
        LedsUtils.setStatus(getIp(), EnumLedsStatus.ON);
    }

    public void turnOff() {
        LedsUtils.setStatus(getIp(), EnumLedsStatus.OFF);
    }

    public Color getColor() {
        return LedsUtils.getColor(getIp());
    }

    public void setColor(Color color) {
        LedsUtils.setColor(getIp(), color);
    }

    public void setColor(int r, int g, int b) {
        LedsUtils.setColor(getIp(), new Color(r, g, b));
    }

    public boolean isConnectedToNetwork() {
        return Utils.isDeviceConnectedToNetwork(getIp());
    }
}
