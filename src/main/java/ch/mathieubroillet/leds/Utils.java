package ch.mathieubroillet.leds;

import ch.mathieubroillet.leds.utils.Logger;
import ch.mathieubroillet.leds.utils.OSValidator;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Scanner;

public class Utils {
    private static String execPythonCommandOnLinux(String cmd) {
        String result = null;
        try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
             Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
            result = (s.hasNext() ? s.next() : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String execCommandWithOutput(String command) {
        try {
            String[] args;
            Process proc = null;
            if (OSValidator.isWindows()) {
                args = new String[]{"cmd", "/C", command};
                proc = new ProcessBuilder(args).start();
            } else if (OSValidator.isUnix()) {
                if (command.startsWith("python")) {
                    return execPythonCommandOnLinux(command);
                }
                args = new String[]{"/bin/bash", "-c", "\"" + command + "\""};
                proc = new ProcessBuilder(args).start();
            }

            InputStream is = proc.getInputStream();

            // Read script execution results
            int i = 0;
            StringBuilder sb = new StringBuilder();
            while ((i = is.read()) != -1)
                sb.append((char) i);

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @param string
     * @return
     */
    public static int getIntegerFromString(String string) {
        if (isInteger(string)) {
            return Integer.parseInt(string);
        }

        return 0;
    }

    /**
     * @param ipAddress
     * @return
     */
    public static boolean isDeviceConnectedToNetwork(String ipAddress) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            return address.isReachable(2000);
        } catch (IOException e) {
            Logger.error("Error when checking if device is connected to network : " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Dirty method to check if the given string is an integer.
     *
     * @param number the "number string"
     * @return Return true if there are no error during the try, otherwise it returns false.
     */
    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;

    }
}
