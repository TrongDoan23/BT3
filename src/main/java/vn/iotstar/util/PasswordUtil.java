package vn.iotstar.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    private PasswordUtil() {}

    public static String hash(String plain) {
        return BCrypt.withDefaults().hashToString(12, plain.toCharArray());
    }

    public static boolean isHashed(String s) {
        return s != null && s.matches("^\\$2[abxy]\\$\\d\\d\\$.{53}$");
    }

    /** So khớp mật khẩu. Mật khẩu cũ chưa hash (dữ liệu cũ) vẫn so được để tự nâng cấp khi đăng nhập. */
    public static boolean matches(String plain, String stored) {
        if (plain == null || stored == null) return false;
        if (isHashed(stored)) {
            return BCrypt.verifyer().verify(plain.toCharArray(), stored).verified;
        }
        return MessageDigest.isEqual(plain.getBytes(StandardCharsets.UTF_8), stored.getBytes(StandardCharsets.UTF_8));
    }
}
