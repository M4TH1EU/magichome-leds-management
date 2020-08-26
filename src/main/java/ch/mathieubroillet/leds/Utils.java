package ch.mathieubroillet.leds;

import ch.mathieubroillet.leds.utils.OSValidator;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

public class Utils {

    public static String execCommandWithOutput(String command) {
        try {
            String[] args;
            Process proc = null;
            if (OSValidator.isWindows()) {
                args = new String[]{"cmd", "/C", command};
                proc = new ProcessBuilder(args).start();
            } else if (OSValidator.isUnix()) {
                args = new String[]{"/bin/bash", "-c", command};
                proc = new ProcessBuilder(args).start();
            }

            InputStream is = proc.getInputStream();

            // Read script execution results
            int i = 0;
            StringBuffer sb = new StringBuffer();
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


    public static byte[] convertIpToBytes(String ipAdress) {
        String[] ipNumbers = ipAdress.split("\\.");
        byte[] ip = {
                (byte) getIntegerFromString(ipNumbers[0]),
                (byte) getIntegerFromString(ipNumbers[1]),
                (byte) getIntegerFromString(ipNumbers[2]),
                (byte) getIntegerFromString(ipNumbers[3])
        };

        return ip;
    }

    /**
     * @param ipAdress
     * @return
     */
    public static boolean isDeviceConnectedToNetwork(String ipAdress) {
        try {
            InetAddress address = InetAddress.getByAddress(convertIpToBytes(ipAdress));
            return address.isReachable(1000);
        } catch (IOException e) {
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
