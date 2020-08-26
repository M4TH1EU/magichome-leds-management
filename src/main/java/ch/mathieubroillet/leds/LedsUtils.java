package ch.mathieubroillet.leds;

import ch.mathieubroillet.leds.enums.EnumLedsStatus;
import ch.mathieubroillet.leds.utils.Logger;
import ch.mathieubroillet.leds.utils.OSValidator;

public class LedsUtils {

    public static EnumLedsStatus getStatus(String ip) {
        if (Utils.isDeviceConnectedToNetwork(ip)) {
            String output = Utils.execCommandWithOutput(OSValidator.isWindows() ? "py" : "python" + " -m flux_led " + ip + " --info");

            if (output.split(" ")[3].equals("ON")) {
                return EnumLedsStatus.ON;
            } else if (output.split(" ")[3].equals("OFF")){
                return EnumLedsStatus.OFF;
            } else {
                Logger.error(output);
            }

        }

        return EnumLedsStatus.OFF;
    }

    public static boolean isOn(String ip) {
        return getStatus(ip).equals(EnumLedsStatus.ON);
    }

    public static void setStatus(String ip, EnumLedsStatus enumLeds) {
        Utils.execCommandWithOutput(OSValidator.isWindows() ? "py" : "python" + " -m flux_led " + ip + " --" + enumLeds.toString().toLowerCase());

    }
}
