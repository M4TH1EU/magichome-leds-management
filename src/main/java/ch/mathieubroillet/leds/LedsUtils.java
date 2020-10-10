package ch.mathieubroillet.leds;

import ch.mathieubroillet.leds.enums.EnumLedsStatus;
import ch.mathieubroillet.leds.utils.Logger;
import ch.mathieubroillet.leds.utils.OSValidator;

import java.awt.*;

public class LedsUtils {
    private static final String COMMAND_BASE = (OSValidator.isWindows() ? "py" : "python3") + " -m flux_led ";

    public static EnumLedsStatus getStatus(String ip) {
        if (Utils.isDeviceConnectedToNetwork(ip)) {
            String output = Utils.execCommandWithOutput(COMMAND_BASE + ip + " --info");

            String[] infos = output.split(" ");
            if (infos[3].equals("ON")) {
                return EnumLedsStatus.ON;
            } else if (infos[3].equals("OFF")) {
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
        Utils.execCommandWithOutput(COMMAND_BASE + ip + " --" + enumLeds.toString().toLowerCase());
    }

    public static void setColor(String ip, int r, int g, int b) {
        setColor(ip, new Color(r, g, b));
    }

    public static void setColor(String ip, Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        Utils.execCommandWithOutput(COMMAND_BASE + ip + " -c" + r + "," + g + "," + b);
    }

    public static Color getColor(String ip) {
        if (Utils.isDeviceConnectedToNetwork(ip)) {
            String output = Utils.execCommandWithOutput(COMMAND_BASE + ip + " --info");
            String[] infos = output.split(" ");
            int r, g, b;
            if (infos[5].equals("Color")) {
                String red = infos[6].replaceFirst("\\(", "").replaceFirst(",", "");
                r = Utils.getIntegerFromString(red);

                String blue = infos[7].replaceFirst(",", "");
                b = Utils.getIntegerFromString(blue);

                String green = infos[8].replaceFirst("\\)", "");
                g = Utils.getIntegerFromString(green);

                return new Color(r, g, b);
            }

        }

        return Color.WHITE;
    }
}
