IF DB_ID('JPAShopping') IS NULL CREATE DATABASE JPAShopping;
GO
USE JPAShopping;
GO
CREATE TABLE Users(
  id int IDENTITY(1,1) PRIMARY KEY,
  email nvarchar(255) NOT NULL UNIQUE,
  username nvarchar(100) NOT NULL UNIQUE,
  fullname nvarchar(255) NULL,
  [password] nvarchar(255) NOT NULL,
  avatar nvarchar(255) NULL,
  roleid int NOT NULL,
  phone nvarchar(20) NULL,
  createddate date NULL,
  active bit NULL,
  otp nvarchar(100) NULL,
  otp_purpose nvarchar(20) NULL,
  otp_expiry datetime2 NULL,
  otp_attempts int NULL,
  remember_token nvarchar(100) NULL
);
GO
CREATE TABLE Category(
  cate_id int IDENTITY(1,1) PRIMARY KEY,
  cate_name nvarchar(255) NOT NULL,
  icons nvarchar(255) NULL
);
GO
CREATE TABLE Videos(
  id int IDENTITY(1,1) PRIMARY KEY,
  title nvarchar(255) NOT NULL,
  description nvarchar(max) NULL,
  poster nvarchar(255) NULL,
  video_url nvarchar(500) NULL,
  views int NOT NULL DEFAULT 0,
  active bit NOT NULL DEFAULT 1,
  created_date datetime2 NULL,
  user_id int NOT NULL,
  cate_id int NULL,
  CONSTRAINT FK_Videos_Users FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
  CONSTRAINT FK_Videos_Category FOREIGN KEY (cate_id) REFERENCES Category(cate_id) ON DELETE SET NULL
);
GO
-- roleid: 1 = admin, 2 = manager, 5 = người dùng thường
-- Mật khẩu mẫu ở dạng thường; sẽ tự động được hash (BCrypt) ở lần đăng nhập đầu tiên.
INSERT INTO Users(email,username,fullname,[password],roleid,phone,createddate,active) VALUES
 (N'admin@gmail.com',  N'admin',   N'Quản trị viên', N'123456', 1, N'0900000001', GETDATE(), 1),
 (N'manager@gmail.com',N'manager', N'Quản lý',       N'123456', 2, N'0900000002', GETDATE(), 1),
 (N'user@gmail.com',   N'user',    N'Người dùng',    N'123456', 5, N'0900000003', GETDATE(), 1);
INSERT INTO Category(cate_name,icons) VALUES (N'Quần áo nam',N'avatar.png'),(N'Quần áo nữ',N'avatar.png');
GO
