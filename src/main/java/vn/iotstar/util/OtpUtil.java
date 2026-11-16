package vn.iotstar.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class OtpUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private OtpUtil() {}

    /** OTP 6 chữ số. */
    public static String generateOtp() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }

    /** Token ngẫu nhiên cho chức năng "Nhớ tôi". */
    public static String generateToken() {
        return new BigInteger(256, RANDOM).toString(16);
    }
}
