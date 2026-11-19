-- Bài 03: bảng Videos (1 user - n videos). Chạy trong SSMS với DB JPAShopping.
-- Lưu ý: lệnh DROP sẽ xóa bảng Videos cũ (do bài test trước tạo ra).
USE JPAShopping;
GO
IF OBJECT_ID('Videos','U') IS NOT NULL DROP TABLE Videos;
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
