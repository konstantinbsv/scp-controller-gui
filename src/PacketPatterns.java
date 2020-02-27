import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacketPatterns {
    private static final String startString = "START";
    public static final Pattern startPattern = Pattern.compile(startString);

    private static final String endString = "END";
    public static final Pattern endPattern = Pattern.compile(endString);

    private static final String floatValueString1FD = ("\\.* (\\d+\\.\\d)");     // "Current1: (\\d+\\.\\d)";
    public static final Pattern floatValuePattern1FD = Pattern.compile(floatValueString1FD);

    private static final String floatValueString0FD = ("\\.* (\\d+)");     // "Current1: (\\d+\\.\\d)";
    public static final Pattern floatValuePattern0FD = Pattern.compile(floatValueString0FD);

    public static String getStringValue (String rawData) {
        return getStringValue(rawData, true);
    }

    public static String getStringValue (String rawData, boolean fractionalDigits) {
        Matcher floatMatcher;

        if (fractionalDigits ) {
            floatMatcher = floatValuePattern1FD.matcher(rawData);
        } else {
            floatMatcher = floatValuePattern0FD.matcher(rawData);
        }

        if (floatMatcher.find()) {
            return floatMatcher.group(1);
        } else {
            return "---";
        }
    }

    public static boolean isPacketStart (String rawData) {
        Matcher startMatcher = startPattern.matcher(rawData);
        if (startMatcher.find()) {
            return true;
        } else {
            System.out.println("START not found.");
            return false;
        }
    }

    public static boolean isPacketEnd (String rawData) {
        Matcher endMatcher = endPattern.matcher(rawData);
        if (endMatcher.find()) {
            return true;
        } else {
            return false;
        }

    }
}
