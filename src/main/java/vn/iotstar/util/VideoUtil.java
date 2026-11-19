package vn.iotstar.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoUtil {
    private static final Pattern YOUTUBE =
            Pattern.compile("(?:youtu\\.be/|youtube\\.com/(?:watch\\?(?:.*&)?v=|embed/|shorts/))([A-Za-z0-9_-]{11})");

    private VideoUtil() {}

    /** Link YouTube -> link embed; không phải YouTube thì trả về null. */
    public static String youtubeEmbed(String url) {
        if (url == null) return null;
        Matcher m = YOUTUBE.matcher(url);
        return m.find() ? "https://www.youtube.com/embed/" + m.group(1) : null;
    }

    public static boolean isHttp(String s) {
        return s != null && (s.startsWith("http://") || s.startsWith("https://"));
    }
}
