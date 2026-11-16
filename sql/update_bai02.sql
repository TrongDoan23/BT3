-- Chạy file này nếu đã có DB JPAShopping từ bài trước (thêm cột cho OTP / kích hoạt / nhớ tôi)
USE JPAShopping;
GO
ALTER TABLE Users ADD active bit NULL, otp nvarchar(100) NULL, otp_purpose nvarchar(20) NULL,
    otp_expiry datetime2 NULL, otp_attempts int NULL, remember_token nvarchar(100) NULL;
GO
UPDATE Users SET active = 1;
GO
