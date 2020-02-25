import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacketPatterns {
    private static final String startString = "START";
    public static final Pattern startPattern = Pattern.compile(startString);

    private static final String endString = "END";
    public static final Pattern endPattern = Pattern.compile(endString);

    private static final String floatValueString = ("\\.* (\\d+\\.\\d+)");     // "Current1: (\\d+\\.\\d+)";
    public static final Pattern floatValuePattern = Pattern.compile(floatValueString);

    public static String getStringValue (String rawData) {
        Matcher floatMatcher = floatValuePattern.matcher(rawData);
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
