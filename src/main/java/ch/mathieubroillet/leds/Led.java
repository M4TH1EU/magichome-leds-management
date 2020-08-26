package ch.mathieubroillet.leds;

import ch.mathieubroillet.leds.enums.EnumLedsStatus;

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

    public boolean isTargetMachineIsOn() {
        return Utils.isDeviceConnectedToNetwork(getTargetMachineIP());
    }

    public String getName() {
        return name;
    }

    public EnumLedsStatus getLedsStatus() {
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

    public boolean isConnectedToNetwork() {
        return Utils.isDeviceConnectedToNetwork(getIp());
    }
}
