﻿---CREATE  DATABASE SuperSportSneakers
GO
USE SuperSportSneakers
GO


--- SAN PHAM
IF OBJECT_ID('SANPHAM') IS NOT NULL
	DROP TABLE SANPHAM
CREATE TABLE SANPHAM 
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	MaSP VARCHAR(15)  NOT NULL ,
	TenSP NVARCHAR(50) NOT NULL,
	NgayTao DATE DEFAULT GETDATE(),
	TrangThai INT DEFAULT 0 NOT NULL --- 0 - Đang Kinh Kinh Doanh 1 - Ngừng Kinh Doanh 2 - Tạm Hết Hàng
	CONSTRAINT PK_SANPHAM PRIMARY KEY (ID)
)
--- THEM SANPHAM
INSERT INTO SANPHAM (MaSP, TenSP)
VALUES 
('SP00001', 'Nike Air Force 1'),
('SP00002', 'Adidas Superstar'),
('SP00003', 'Converse Chuck Taylor'),
('SP00004', 'Vans Old Skool'),
('SP00005', 'Puma Suede');
SELECT * FROM SANPHAM;

IF OBJECT_ID('THUONGHIEU') IS NOT NULL
	DROP TABLE THUONGHIEU
CREATE TABLE THUONGHIEU 
(
	 ID BIGINT IDENTITY (1,1) NOT NULL,
	 MaThuongHieu VARCHAR(15)  NOT NULL ,
	 TenThuongHieu NVARCHAR(50) NOT NULL,
     TrangThai INT DEFAULT 0 NOT NULL , -- 0 ĐANG HỢP TÁC , 1 NGỪNG HỢP TÁC
	 CONSTRAINT PK_THUONGHIEU PRIMARY KEY (ID)
)
-- THEM THUONG HIEU
INSERT INTO THUONGHIEU (MaThuongHieu, TenThuongHieu)
VALUES 
('TH001', 'Nike'),
('TH002', 'Adidas'),
('TH003', 'Converse'),
('TH004', 'Vans'),
('TH005', 'Puma');
SELECT * FROM THUONGHIEU

--- MAU
IF OBJECT_ID('MAU') IS NOT NULL
	DROP TABLE MAU
CREATE TABLE MAU 
(
	 ID BIGINT IDENTITY (1,1) NOT NULL,
	 MaMau VARCHAR(15)  NOT NULL ,
	 TenMau NVARCHAR(50) NOT NULL,
     TrangThai INT DEFAULT 0 NOT NULL , -- 0 CÒN DÙNG , 1 KHÔNG DÙNG
	 CONSTRAINT PK_MAU PRIMARY KEY (ID)
)
INSERT INTO MAU (MaMau, TenMau )
VALUES 
('MAU0001', N'Đỏ'),
('MAU0002', N'Xanh'),
('MAU0003', N'Trắng'),
('MAU0004', N'Vàng'),
('MAU0005', N'Đen');

--- SIZE 
IF OBJECT_ID('SIZE') IS NOT NULL
	DROP TABLE SIZE
CREATE TABLE SIZE 
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	MaSize VARCHAR(15)  NOT NULL ,
	TenSize DECIMAL(3, 1) NOT NULL,
    TrangThai INT DEFAULT 0 NOT NULL -- 0 CÒN DÙNG , 1 KHÔNG DÙNG
	CONSTRAINT PK_SIZE PRIMARY KEY (ID)
)
-- them SIZE
INSERT INTO SIZE (MaSize, TenSize)
VALUES 
('KT001', 34.5),
('KT002', 35),
('KT003', 35.5),
('KT004', 36),
('KT005', 36.5),
('KT006', 37),
('KT007', 37.5),
('KT008', 38),
('KT009', 38.5),
('KT010', 39);


--- NHAN VIEN
IF OBJECT_ID('NHANVIEN') IS NOT NULL
	DROP TABLE NHANVIEN
CREATE TABLE NHANVIEN 
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	MaNhanVien VARCHAR(10)  NULL,
	HoVaTen NVARCHAR(50) NULL,
	MatKhau VARCHAR(10) NULL,
	NgaySinh DATE NULL,
	CCCD VARCHAR(12) NULL ,
	Email VARCHAR(50) NULL,
	DiaChi NVARCHAR(100) NULL,
	SDT VARCHAR(15) NULL,
	VaiTro BIT  DEFAULT 0 ,--- 0 NHÂN VIÊN , 1 LÀ QUẢN LÍ
	NgayTao DATE DEFAULT GETDATE(),
	TrangThai BIT DEFAULT 1 , -- 0 ĐÃ NGHỈ 1 ĐANG LÀM
	CONSTRAINT PK_NHANVIEN PRIMARY KEY (ID),
)
--- THEM NHAN VIEN
-- Thêm 10 nhân viên vào NHANVIEN
INSERT INTO NHANVIEN (MaNhanVien, HoVaTen, MatKhau, NgaySinh, CCCD, Email, DiaChi, SDT, VaiTro, NgayTao, TrangThai)
VALUES 
('NV001', N'Nguyễn Văn A', 'pass123', '1990-01-15', '123456789012', 'nva@example.com', N'123 Đường ABC, Quận XYZ', '0901234567', 0, GETDATE(), 1),
('NV002', N'Trần Thị B', 'pass456', '1995-05-20', '234567890123', 'ttb@example.com', N'456 Đường DEF, Quận LMN', '0912345678', 0, GETDATE(), 1),
('NV003', N'Phạm Văn C', 'pass789', '1988-08-10', '345678901234', 'pvc@example.com', N'789 Đường GHI, Quận OPQ', '0923456789', 0, GETDATE(), 1),
('NV004', N'Lê Thị D', 'passABC', '1992-12-25', '456789012345', 'ltd@example.com', N'101 Đường JKL, Quận RST', '0934567890', 0, GETDATE(), 1),
('NV005', N'Ngô Văn E', 'passDEF', '1985-04-30', '567890123456', 'nve@example.com', N'202 Đường UVW, Quận XYZ', '0945678901', 0, GETDATE(), 1),
('NV006', N'Lý Thị F', 'passGHI', '1998-07-05', '678901234567', 'ltf@example.com', N'303 Đường LMN, Quận OPQ', '0956789012', 0, GETDATE(), 1),
('NV007', N'Nguyễn Văn Mạnh', 'passLMN', '1980-11-12', '789012345678', 'hvm@example.com', N'404 Đường OPQ, Quận RST', '0967890123', 1, GETDATE(), 1),
('NV008', N'Tạ Bá Hòa', 'passOPQ', '1983-09-18', '890123456789', 'tbh@example.com', N'505 Đường RST, Quận UVW', '0978901234', 1, GETDATE(), 1),
('NV009', N'Trương Văn I', 'passRST', '1993-03-08', '901234567890', 'tvi@example.com', N'606 Đường XYZ, Quận ABC', '0989012345', 0, GETDATE(), 1),
('NV010', N'Vũ Thị K', 'passUVW', '1996-06-22', '012345678901', 'vtk@example.com', N'707 Đường ABC, Quận DEF', '0990123456', 0, GETDATE(), 1);


SELECT * FROM NHANVIEN
-- ĐỢT GIẢM GIÁ
IF OBJECT_ID('DOT_GIAM_GIA') IS NOT NULL
	DROP TABLE DOT_GIAM_GIA
CREATE TABLE DOT_GIAM_GIA 
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdNV BIGINT NOT NULL,
	MaDGG VARCHAR(10)  NULL ,
	TenDGG NVARCHAR(100) NULL,
	Loai INT  NULL ,--- 0 LÀ % 1 LÀ VND
	GiaTri MONEY NULL, 
	NgayBatDau DATETIME NULL,
	NgayKetThuc DATETIME NULL,
	MoTa NVARCHAR(250) NULL,
	NgayTao DATE DEFAULT GETDATE(),
	TrangThai INT NULL , ---0 SẮP ĐẾN , 1 ĐANG ĐƯỢC ÁP DỤNG , 2 KẾT THÚC ĐỢT GIẢM GIÁ 
	CONSTRAINT PK_DOT_GIAM_GIA PRIMARY KEY (ID),
	CONSTRAINT FK_DOT_GIAM_GIA_NHANVIEN FOREIGN KEY (IdNV) REFERENCES NHANVIEN (ID)
)



SELECT * FROM DOT_GIAM_GIA
--- THEM ĐỢT GIẢM GIÁ 
-- Thêm 10 bản ghi giả mạo vào bảng DOT_GIAM_GIA
INSERT INTO DOT_GIAM_GIA (IdNV, MaDGG, TenDGG, Loai, GiaTri, NgayBatDau, NgayKetThuc, MoTa, NgayTao, TrangThai)
VALUES 
(1, 'DGG001', N'Giảm giá mùa hè', 0, 20, '2023-07-15', '2023-08-15', N'Giảm giá cho mùa hè 2023 20% sản phẩm', GETDATE(), 0),
(2, 'DGG002', N'Khuyến mãi tháng 8', 1, 50000, '2023-08-15', '2023-08-31', N'Ưu đãi đặc biệt trong tháng 8 giảm 50000k', GETDATE(), 2),
(3, 'DGG003', N'Chào mừng năm mới', 0, 15, '2024-01-15', '2024-02-15', N'Giảm giá đón chào năm mới giảm 15%', GETDATE(), 3),
(4, 'DGG004', N'Big Sale', 1, 100000, '2023-09-15', '2023-10-15', N'Ưu đãi lớn trong chương trình Big Sale giảm 100000k', GETDATE(), 2),
(5, 'DGG005', N'Halloween Special', 0, 25, '2023-10-15', '2023-10-31', N'Giảm giá đặc biệt cho Halloween 25%', GETDATE(), 2),
(6, 'DGG006', N'Black Friday', 1, 150000, '2023-11-15', '2023-11-30', N'Chương trình khuyến mãi Black Friday 150000k', GETDATE(), 0),
(7, 'DGG007', N'Cyber Monday', 0, 30, '2023-11-30', '2023-12-15', N'Giảm giá trong sự kiện Cyber Monday  30% sản phẩm', GETDATE(), 0),
(8, 'DGG008', N'Year-End Sale', 1, 200000, '2023-12-15', '2023-12-31', N'Ưu đãi cuối năm 200000k/1 sp', GETDATE(), 3),
(9, 'DGG009', N'Tết Nguyên Đán', 0, 10, '2024-01-15', '2024-02-15', N'Giảm giá dịp Tết Nguyên Đán 10%/ 1sp', GETDATE(), 3),
(10, 'DGG010', N'Valentine Special', 1, 75000, '2024-02-01', '2024-02-14', N'Ưu đãi đặc biệt cho Valentine 75.000 / 1sp', GETDATE(), 0),
(1, 'DGG011', N'Khuyến mãi tháng 11', 1, 50000, '2023-11-15', '2023-11-30', N'Ưu đãi đặc biệt trong tháng 11 giảm 50000k /1sp', GETDATE(), 1);



-------------------------------------------------------------------------------------------------
--- CHI TIẾT SẢN PHẨM

IF OBJECT_ID('CHI_TIET_SAN_PHAM') IS NOT NULL
	DROP TABLE CHI_TIET_SAN_PHAM
CREATE TABLE CHI_TIET_SAN_PHAM
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdSP BIGINT NOT NULL ,
	IdThuongHieu BIGINT NOT NULL,
	IdMau BIGINT NOT NULL,
	IdSize BIGINT NOT NULL,
	--QR VARCHAR(100) NULL,
	IdDGG BIGINT NULL,
	MaCTSP VARCHAR(15) NOT NULL,
	SoLuongTon INT NOT NULL ,
	GiaNiemYet MONEY NOT NULL , -- ÁNH XẠ JAVA => BigDecimal
	GiaBan MONEY NOT NULL,
	MoTa NVARCHAR (250) NOT NULL ,
	NgayTao DATE DEFAULT GETDATE(),
	TrangThai INT NOT NULL DEFAULT 0 , -- 0 CÒN HÀNG , 1 TẠM HẾT , 2 DỪNG BÁN
	CONSTRAINT PK_CHI_TIET_SAN_PHAM PRIMARY KEY (ID),
	CONSTRAINT FK_CHI_TIET_SAN_PHAM_SANPHAM FOREIGN KEY (IdSP) REFERENCES SANPHAM (ID),
	CONSTRAINT FK_CHI_TIET_SAN_PHAM_THUONGHIEU FOREIGN KEY (IdThuongHieu) REFERENCES THUONGHIEU (ID),
	CONSTRAINT FK_CHI_TIET_SAN_PHAM_MAU FOREIGN KEY (IdMau) REFERENCES MAU (ID),
	CONSTRAINT FK_CHI_TIET_SAN_PHAM_SIZE FOREIGN KEY (IdSize) REFERENCES SIZE (ID),
	CONSTRAINT FK_CHI_TIET_SAN_PHAM_DOT_GIAM_GIA FOREIGN KEY (IdDGG) REFERENCES DOT_GIAM_GIA (ID),
)
--ALTER TABLE CHI_TIET_SAN_PHAM
--ADD  QR VARCHAR(100) NULL;

--- THEM CHI TIET SAN PHAM
INSERT INTO CHI_TIET_SAN_PHAM (IdSP, IdThuongHieu, IdMau, IdSize, MaCTSP, SoLuongTon, GiaNiemYet, GiaBan, MoTa, NgayTao, TrangThai)
VALUES 
(1, 1, 1, 1, 'CTSP0001', 50, 1200000.00, 1200000.00, N'Mô tả CTSP', GETDATE(), 0),
(2, 2, 2, 2, 'CTSP0002', 30, 1100000.00, 1100000.00, N'Mô tả CTSP', GETDATE(), 0),
(3, 3, 3, 3, 'CTSP0003', 20, 1000000.00, 1000000.00, N'Mô tả CTSP', GETDATE(), 0),
(4, 4, 4, 4, 'CTSP0004', 25, 1050000.00, 1050000.00, N'Mô tả CTSP', GETDATE(), 0),
(5, 5, 5, 5, 'CTSP0005', 40, 1150000.00, 1150000.00, N'Mô tả CTSP', GETDATE(), 0),
(5, 1, 2, 3, 'CTSP0006', 55, 1300000.00, 1300000.00, N'Mô tả CTSP', GETDATE(), 0),
(4, 2, 3, 4, 'CTSP0007', 35, 1250000.00, 1250000.00, N'Mô tả CTSP', GETDATE(), 0),
(3, 3, 4, 6, 'CTSP0008', 40, 1180000.00, 1180000.00, N'Mô tả CTSP', GETDATE(), 0),
(2, 4, 5, 1, 'CTSP0009', 30, 1100000.00, 1100000.00, N'Mô tả CTSP', GETDATE(), 0),
(1, 5, 1, 2, 'CTSP0010', 25, 1050000.00, 1050000.00, N'Mô tả CTSP', GETDATE(), 0),
(2, 1, 2, 3, 'CTSP0011', 60, 1400000.00, 1400000.00, N'Mô tả CTSP', GETDATE(), 0),
(3, 2, 3, 10, 'CTSP0012', 45, 1350000.00, 1350000.00, N'Mô tả CTSP', GETDATE(), 0),
(4, 3, 4, 8, 'CTSP0013', 50, 1200000.00, 1200000.00, N'Mô tả CTSP', GETDATE(), 0),
(5, 4, 5, 7, 'CTSP0014', 38, 1120000.00, 1120000.00, N'Mô tả CTSP', GETDATE(), 0),
(1, 5, 1, 2, 'CTSP0015', 42, 1150000.00, 1150000.00, N'Mô tả CTSP', GETDATE(), 0),
(3, 1, 2, 3, 'CTSP0016', 48, 1220000.00, 1220000.00, N'Mô tả CTSP', GETDATE(), 0),
(4, 2, 3, 9, 'CTSP0017', 32, 1280000.00, 1280000.00, N'Mô tả CTSP', GETDATE(), 0),
(5, 3, 4, 5, 'CTSP0018', 28, 1150000.00, 1150000.00, N'Mô tả CTSP', GETDATE(), 0),
(1, 4, 5, 8, 'CTSP0019', 22, 1100000.00, 1100000.00, N'Mô tả CTSP', GETDATE(), 0),
(2, 5, 1, 6, 'CTSP0020', 65, 1380000.00, 1380000.00, N'Mô tả CTSP', GETDATE(), 0),
(2, 1, 2, 3, 'CTSP0021', 48, 1220000.00, 1220000.00, N'Mô tả CTSP', GETDATE(), 0),
(1, 2, 3, 9, 'CTSP0022', 32, 1280000.00, 1280000.00, N'Mô tả CTSP', GETDATE(), 0),
(5, 3, 4, 5, 'CTSP0023', 28, 1150000.00, 1150000.00, N'Mô tả CTSP', GETDATE(), 0),
(4, 4, 5, 8, 'CTSP0024', 22, 1100000.00, 1100000.00, N'Mô tả CTSP', GETDATE(), 0),
(3, 5, 1, 6, 'CTSP0025', 65, 1380000.00, 1380000.00, N'Mô tả CTSP', GETDATE(), 0),
(3, 1, 2, 4, 'CTSP0026', 48, 1220000.00, 1220000.00, N'Mô tả CTSP', GETDATE(), 0),
(4, 2, 3, 6, 'CTSP0027', 32, 1280000.00, 1280000.00, N'Mô tả CTSP', GETDATE(), 0),
(5, 3, 4, 10, 'CTSP0028', 28, 1150000.00, 1150000.00, N'Mô tả CTSP', GETDATE(), 0),
(1, 4, 5, 10, 'CTSP0029', 22, 1100000.00, 1100000.00, N'Mô tả CTSP', GETDATE(), 0),
(2, 5, 1, 7, 'CTSP0023', 65, 1380000.00, 1380000.00, N'Mô tả CTSP', GETDATE(), 0);

SELECT * FROM CHI_TIET_SAN_PHAM

-- ++++++ BẢNG THÊN MỚI


--- CT ĐỢT GIẢM GIÁ 

-- CSTP SẼ KO LÀM KHÓA NGOẠI XỬ LÝ JAVA TỰ THÊM ID VÀ MÃ CTSP VÀ TÍNH GIÁ CÒN LẠI , GIÁ TRỊ GIẢM VD % THÌ PHẢI QUY RA ĐƯỢC BN TIỀN
IF OBJECT_ID('CHI_TIET_DGG') IS NOT NULL
	DROP TABLE CHI_TIET_DGG
CREATE TABLE CHI_TIET_DGG 
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdDGG BIGINT NOT NULL,
	IdCTSP BIGINT NULL,
	MaCTSP VARCHAR(20) NULL,
	DonGia MONEY NULL,
	DonGiaConLai MONEY NULL,
	GiaTriGiam FLOAT NULL, 
	TrangThai INT DEFAULT 0 , --- 0 CHUA XOA , 1 XOA
	CONSTRAINT PK_CHI_TIET_DGG PRIMARY KEY (ID),
	CONSTRAINT FK_CHI_TIET_DGG_DOT_GIAM_GIA FOREIGN KEY (IdDGG) REFERENCES DOT_GIAM_GIA (ID),
)
go
---- kêt thúc phần chỉ sửa
IF OBJECT_ID('KHACHHANG') IS NOT NULL
	DROP TABLE KHACHHANG
CREATE TABLE KHACHHANG
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdNV BIGINT NOT NULL,
	MaKhachHang VARCHAR(20) NULL,
	TenKhachHang NVARCHAR(50) NULL,
	SDT VARCHAR(15) NOT NULL,
	NgaySinh DATE NULL,
	GioiTinh BIT NULL , --- 0  NU , 1 NAM
	Email VARCHAR(50) NULL,
	DiaChi NVARCHAR(100) NULL,
	Diem INT DEFAULT 0 ,
	CapBac INT DEFAULT 0 , -- O DONG 1 BAC 2 VANG 
	NgayTao DATE DEFAULT GETDATE(),
	TrangThai BIT DEFAULT 1 , --- 1 CÒN HOẠT ĐỘNG 0 DÙNG HOẶT ĐỌNG
	CONSTRAINT PK_KHACHHANG PRIMARY KEY (ID) ,
	CONSTRAINT FK_KHACHHANG_NHANVIEN FOREIGN KEY (IdNV) REFERENCES NHANVIEN (ID)
)


--- THEM KHACH HANG
-- Thêm 10 khách hàng giả mạo vào bảng KHACHHANG
INSERT INTO KHACHHANG (IdNV, MaKhachHang, TenKhachHang, SDT, NgaySinh, Email, GioiTinh, DiaChi, Diem, CapBac, NgayTao, TrangThai)
VALUES 
(1, 'KH000', N'Khách bán lẻ', '....', '1990-05-20', '...', 0, N'....', 0, 3, GETDATE(), 1),
(1, 'KH001', N'Nguyễn Thị Ánh', '0901234567', '1990-05-20', 'manh@gmail.com', 0, N'123 Đường ABC, Quận XYZ', 100, 2, GETDATE(), 1),
(2, 'KH002', N'Trần Văn Bình', '0912345678', '1985-08-10', 'manh@gmail.com', 1, N'456 Đường DEF, Quận LMN', 50, 1, GETDATE(), 1),
(3, 'KH003', N'Phạm Minh Châu', '0923456789', '1992-12-25', 'manh@gmail.com', 1, N'789 Đường GHI, Quận OPQ', 200, 2, GETDATE(), 1),
(4, 'KH004', N'Lê Thị Dương', '0934567890', '1988-04-30', 'manh@gmail.com', 0, N'101 Đường JKL, Quận RST', 150, 1, GETDATE(), 1),
(5, 'KH005', N'Ngô Hồng Êm', '0945678901', '1995-07-05', 'manh@gmail.com', 0, N'202 Đường UVW, Quận XYZ', 80, 1, GETDATE(), 1),
(6, 'KH006', N'Lý Minh Giao', '0956789012', '1980-11-12', 'manh@gmail.com', 1, N'303 Đường LMN, Quận OPQ', 120, 2, GETDATE(), 1),
(7, 'KH007', N'Nguyễn Thị Hương', '0967890123', '1983-09-18', 'manh@gmail.com', 0, N'404 Đường OPQ, Quận RST', 90, 1, GETDATE(), 1),
(8, 'KH008', N'Tạ Đình Hòa', '0978901234', '1998-03-08', 'manh@gmail.com', 1, N'505 Đường RST, Quận UVW', 180, 2, GETDATE(), 1),
(9, 'KH009', N'Trương Hoàng I', '0989012345', '1993-06-22', 'manh@gmail.com', 1, N'606 Đường XYZ, Quận ABC', 60, 1, GETDATE(), 1),
(10, 'KH010', N'Vũ Thị Kiều', '0990123456', '1996-09-15', 'manh@gmail.com', 0, N'707 Đường ABC, Quận DEF', 130, 2, GETDATE(), 1);

--- PHIEU GIAM GIA
IF OBJECT_ID('PHIEU_GIAM_GIA') IS NOT NULL
	DROP TABLE PHIEU_GIAM_GIA
CREATE TABLE PHIEU_GIAM_GIA 
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdNV BIGINT NOT NULL,
	MaPhieu NVARCHAR(20) NULL ,
	TenPhieu NVARCHAR(100) NULL,
	LoaiPhieu BIT NULL , -- 0 LÀ % , 1 VND
	GiaTri FLOAT NULL ,-- ++=> sửa lại thành FLoat
	SoLuongPhieu INT NULL,
	DonToiThieu FLOAT NULL , --- ÁNH XẠ JAVA => BigDecimal
	NgayBatDau DATETIME NULL,
	NgayKetThuc DATETIME NULL,
	NgayTao DATE DEFAULT GETDATE(),
	MoTa NVARCHAR(250) NULL,
	TrangThai INT DEFAULT 0 NULL , -- 0 SẮP ĐẾN , 1 ĐANG DIỄN RA , 2 ĐÃ KẾT THÚC , 
	CONSTRAINT PK_PHIEU_GIAM_GIA PRIMARY KEY (ID),
	CONSTRAINT FK_PHIEU_GIAM_GIA_NHANVIEN FOREIGN KEY (IdNV) REFERENCES NHANVIEN (ID)
)


-- Thêm 10 phiếu giảm giá giả mạo vào bảng PHIEU_GIAM_GIA
INSERT INTO PHIEU_GIAM_GIA (IdNV, MaPhieu, TenPhieu, LoaiPhieu, GiaTri, SoLuongPhieu, DonToiThieu, NgayBatDau, NgayKetThuc, MoTa, TrangThai)
VALUES 
(1, 'PGG001', N'Phiếu giảm giá mùa hè', 0, 20, 100, 500000, '2023-07-15T00:00:00', '2023-08-15T23:59:59', N'Áp dụng cho mùa hè 2023 20% hóa đơn', 2),
(2, 'PGG002', N'Phiếu giảm giá tháng 8', 1, 50000, 50, 1000000, '2023-08-01T00:00:00', '2023-08-31T23:59:59', N'Ưu đãi đặc biệt trong tháng 8 50000k / hóa đơn', 2),
(3, 'PGG003', N'Phiếu khuyến mãi năm mới', 0, 15, 200, 2000000, '2024-01-01T00:00:00', '2024-01-15T23:59:59', N'Chào đón năm mới 2024 15% /hóa đơn', 3),
(4, 'PGG004', N'Phiếu giảm giá Big Sale', 1, 100000, 30, 1500000, '2023-09-01T00:00:00', '2023-09-30T23:59:59', N'Ưu đãi lớn trong chương trình Big Sale 100000k / hóa đơn', 2),
(5, 'PGG005', N'Phiếu giảm giá Halloween', 0, 25, 20, 800000, '2023-10-15T00:00:00', '2023-10-31T23:59:59', N'Giảm giá đặc biệt cho Halloween 25% / hóa đơn', 2),
(6, 'PGG006', N'Phiếu khuyến mãi Black Friday', 1, 150000, 10, 2000000, '2023-11-25T00:00:00', '2023-11-30T23:59:59', N'Chương trình khuyến mãi Black Friday 150000k / hóa đơn', 0),
(7, 'PGG007', N'Phiếu giảm giá Cyber Monday', 0, 30, 15, 3000000, '2023-11-30T00:00:00', '2023-12-15T23:59:59', N'Giảm giá trong sự kiện Cyber Monday 30% / hóa đơn', 0),
(8, 'PGG008', N'Phiếu giảm giá Year-End Sale', 1, 200000, 5, 500000, '2023-11-12T00:00:00', '2023-12-31T23:59:59', N'Ưu đãi cuối năm 200000k / hóa đơn', 1),
(9, 'PGG009', N'Phiếu giảm giá Tết Nguyên Đán', 0, 10, 25, 1000000, '2024-01-15T00:00:00', '2024-02-15T23:59:59', N'Giảm giá dịp Tết Nguyên Đán 10 % / hóa đơn', 3),
(10, 'PGG010', N'Phiếu giảm giá Valentine', 1, 75000, 40, 1200000, '2024-02-01T00:00:00', '2024-02-14T23:59:59', N'Ưu đãi đặc biệt cho Valentine 75000k / hóa đơn', 3);


SELECT * FROM PHIEU_GIAM_GIA

IF OBJECT_ID('HOADON') IS NOT NULL
	DROP TABLE HOADON
CREATE TABLE HOADON
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdPGG BIGINT  NULL,
	IdNV BIGINT NOT NULL,
	IdKH BIGINT NOT NULL,
	QR VARCHAR (MAX) NULL,
	MaHoaDon VARCHAR(20) NOT NULL,------------ THÊM CẤP BẬC Ở ĐÂY
	TongTienSP MONEY DEFAULT 0.00, -- Thêm mói
	CapBac INT DEFAULT 0,-- LƯU CẤP BẬC TẠI THỜI ĐIỂM TT
	PhanTramGia FLOAT DEFAULT 0 , --- CẤP BẬC + PHIẾU THEO % +=> //	PhanTramGia INT NULL , --- CẤP BẬC + PHIẾU THEO % ++ Doi thanh FLoat
	TienPhieuGiam FLOAT DEFAULT 0 ,-- TIỀN GIẢM THEO PHIẾU VND
	DiemDoi FLOAT DEFAULT 0, -- tiền khách hàng từ đó lấy ra tiền 1 diem = 10k 
	-- +=> //	DiemDoi INT NULL, -- tiền khách hàng từ đó lấy ra tiền 1 diem = 10k => MONEY
	PhuongThucTT INT DEFAULT 0 , -- 0 là tại quầy , 1 chuyển khoản ,2  có thể làm kết hợp 2 loại(Có thể bỏ)
	TienKhDua FLOAT DEFAULT 0,
	TienKhChuyenKhoan FLOAT DEFAULT 0 ,
	TienThua FLOAT DEFAULT 0,
	ThanhTien FLOAT DEFAULT 0,
	HinhThucMua BIT DEFAULT 0,-- 0 tại quầy 1 ship
	NgayTao DATETIME DEFAULT GETDATE(),
	NgayThanhToan DATETIME NULL,
	TrangThai INT  DEFAULT 0, -- 0 CHƯA THANH TOÁN , 1 ĐÃ THANH TOÁN , 2 ĐANG GIAO , 3 THANH TOÁN TRƯỚC , 4 hủy 
	CONSTRAINT PK_HOADON PRIMARY KEY (ID),
	CONSTRAINT FK_HOADON_PHIEU_GIAM_GIA FOREIGN KEY (IdPGG) REFERENCES PHIEU_GIAM_GIA(ID) ,
	CONSTRAINT FK_HOADON_NHANVIEN FOREIGN KEY (IdNV) REFERENCES NHANVIEN (ID),
	CONSTRAINT FK_HOADON_KHACHHANG FOREIGN KEY (IdKH) REFERENCES KHACHHANG (ID),
)
SELECT *FROM NHANVIEN

INSERT INTO HOADON (IdPGG, IdNV, IdKH, MaHoaDon, CapBac, PhanTramGia, TienPhieuGiam, DiemDoi, PhuongThucTT, TienKhDua, TienKhChuyenKhoan, TienThua, ThanhTien, NgayTao, NgayThanhToan, TrangThai)
VALUES 
(NULL, 1, 1, 'HD001' , 0, NULL, NULL, NULL, 0, 1500000, NULL, NULL, 1500000, GETDATE(), '2023-11-01T15:30:00', 1),
(NULL, 2, 2, 'HD002', 0, NULL, NULL, NULL, 0, 2000000, NULL, NULL, 2000000, GETDATE(), '2023-11-02T14:45:00', 1),
(NULL, 3, 3, 'HD003', 0, NULL, NULL, NULL, 0, 1800000, NULL, NULL, 1800000, GETDATE(), '2023-11-03T16:20:00', 1),
(NULL, 4, 4, 'HD004', 0, NULL, NULL, NULL, 0, 1200000, NULL, NULL, 1200000, GETDATE(), '2023-11-04T17:10:00', 1),
(NULL, 5, 5, 'HD005', 0, NULL, NULL, NULL, 0, 2500000, NULL, NULL, 2500000, GETDATE(), '2023-11-05T18:00:00', 1),
(NULL, 6, 6, 'HD006', 0, NULL, NULL, NULL, 0, 3000000, NULL, NULL, 3000000, GETDATE(), '2023-11-06T19:45:00', 1),
(NULL, 7, 7, 'HD007', 0, NULL, NULL, NULL, 0, 2200000, NULL, NULL, 2200000, GETDATE(), '2023-11-07T20:30:00', 1),
(NULL, 8, 8, 'HD008', 0, NULL, NULL, NULL, 0, 1900000, NULL, NULL, 1900000, GETDATE(), '2023-11-08T21:15:00', 1),
(NULL, 9, 9, 'HD009', 0, NULL, NULL, NULL, 0, 1700000, NULL, NULL, 1700000, GETDATE(), '2023-11-09T22:00:00', 1),
(NULL, 10, 10, 'HD010', 0, NULL, NULL, NULL, 0, 2100000, NULL, NULL, 2100000, GETDATE(), '2023-11-10T23:30:00', 1);
	
--- HÓA ĐƠN CHI TIẾT

IF OBJECT_ID('HOADONCHITIET') IS NOT NULL
	DROP TABLE HOADONCHITIET

CREATE TABLE HOADONCHITIET
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdHoaDon BIGINT NOT NULL ,
	IdCTSP BIGINT NOT NULL,
	SoLuong INT NULL,
	MaDGG VARCHAR(10)  NULL ,
	LoaiDGG INT NULL,
	GiaTriDGG INT  NULL,
	QuyDoiDGGTT FLOAT NULL , -- => ĐỀU QUY RA THÀNH vnd THEO SẢN PHẨM
	GiaBan FLOAT NULL,
	DonGia FLOAT NULL,
	ThanhTien FLOAT NULL,
	NgayTao DATETIME DEFAULT GETDATE(),
	TrangThai INT NULL ,
	CONSTRAINT PK_HOADONCHITIET PRIMARY KEY (ID),
	CONSTRAINT FK_HOADONCHITIET_HOADON FOREIGN KEY (IdHoaDon) REFERENCES HOADON  (ID),
	CONSTRAINT FK_HOADONCHITIET_CHI_TIET_SAN_PHAM FOREIGN KEY (IdCTSP) REFERENCES CHI_TIET_SAN_PHAM (ID)
)
INSERT INTO HOADONCHITIET (IdHoaDon, IdCTSP, SoLuong, MaDGG, LoaiDGG, GiaTriDGG, QuyDoiDGGTT, GiaBan, DonGia, NgayTao, TrangThai)
VALUES 
(1, 1, 2, NULL, NULL, NULL, 0, 750000, 375000, GETDATE(), 1),
(1, 2, 1, NULL, NULL, NULL, 0, 800000, 800000, GETDATE(), 1),
(2, 3, 3, NULL, NULL, NULL, 0, 900000, 300000, GETDATE(), 1),
(2, 4, 2, NULL, NULL, NULL, 0, 1200000, 600000, GETDATE(), 1),
(3, 5, 1, NULL, NULL, NULL, 0, 450000, 450000, GETDATE(), 1),
(3, 3, 2, NULL, NULL, NULL, 0, 500000, 250000, GETDATE(), 1),
(4, 2, 2, NULL, NULL, NULL, 0, 700000, 350000, GETDATE(), 1),
(4, 1, 1, NULL, NULL, NULL, 0, 1500000, 1500000, GETDATE(), 1),
(5, 1, 3, NULL, NULL, NULL, 0, 750000, 250000, GETDATE(), 1),
(5, 5, 1, NULL, NULL, NULL, 0, 1000000, 1000000, GETDATE(), 1),
(6, 1, 1, NULL, NULL, NULL, 0, 800000, 800000, GETDATE(), 1),
(6, 2, 2, NULL, NULL, NULL, 0, 1600000, 800000, GETDATE(), 1),
(7, 3, 1, NULL, NULL, NULL, 0, 2000000, 2000000, GETDATE(), 1),
(7, 4, 2, NULL, NULL, NULL, 0, 2400000, 1200000, GETDATE(), 1),
(8, 5, 1, NULL, NULL, NULL, 0, 2500000, 2500000, GETDATE(), 1),
(8, 3, 3, NULL, NULL, NULL, 0, 2700000, 900000, GETDATE(), 1),
(9, 4, 2, NULL, NULL, NULL, 0, 3000000, 1500000, GETDATE(), 1),
(9, 2, 1, NULL, NULL, NULL, 0, 1800000, 1800000, GETDATE(), 1),
(10, 3, 3, NULL, NULL, NULL, 0, 2400000, 800000 ,  GETDATE(), 1);

SELECT * FROM HOADONCHITIET
--- PHIEU GIAO HÀNG
IF OBJECT_ID('PHIEUGIAOHANG') IS NOT NULL
	DROP TABLE PHIEUGIAOHANG
CREATE TABLE PHIEUGIAOHANG
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdHoaDon BIGINT NOT NULL,
	IdKH BIGINT NOT NULL,
	TenNguoiNhan NVARCHAR(50)  NULL,
	SDTNguoiNhan VARCHAR(15)  NULL,
	DiaChi NVARCHAR(100)  NULL,
	TenShip NVARCHAR(50)  NULL,
	SDTShip VARCHAR(15)  NULL,
	GiaShip FLOAT NULL , ---->ÁNH XẠ JAVA => BigDecimal
	NgayTaoPhieu DATETIME DEFAULT GETDATE(),
	MaVanDon VARCHAR(20) NULL,
	ĐonViVanChuyen NVARCHAR(100) NULL,
	NgayHoanThanhDon DATETIME NULL,
	TrangThai INT DEFAULT 0 , --0   CHỜ GIAO, 1 : DANG GIAO , 2 : LÀ HẸN LẠI (UPDATE LẠI THÁNG THÁI NGÀY) , 3 hủy , 4 Đã nhận
	CONSTRAINT PK_PHIEUGIAOHANG PRIMARY KEY (ID),
	CONSTRAINT FK_PHIEUGIAOHANG_HOADON FOREIGN KEY (IdHoaDon) REFERENCES HOADON  (ID),
	CONSTRAINT FK_PHIEUGIAOHANG_KHACHHANG FOREIGN KEY (IdKH) REFERENCES KHACHHANG   (ID)
)
SELECT * FROM CHI_TIET_SAN_PHAM


-- Thêm dữ liệu vào bảng PHIEUGIAOHANG
INSERT INTO PHIEUGIAOHANG (IdHoaDon, IdKH, TenNguoiNhan, SDTNguoiNhan, DiaChi, TenShip, SDTShip, GiaShip, NgayTaoPhieu, MaVanDon, ĐonViVanChuyen, NgayHoanThanhDon, TrangThai)
VALUES 
(1, 1, N'Nguyễn Văn A', '0901234567', N'123 Đường ABC, Quận 1, TP.HCM', N'Nhân viên A', '0987654321', 50000.00, GETDATE(), 'VD001', N'Giao hàng nhanh', NULL, 1),
(2, 2, N'Nguyễn Thị B', '0909876543', N'456 Đường XYZ, Quận 2, TP.HCM', N'Nhân viên B', '0123456789', 60000.00, GETDATE(), 'VD002', N'Giao hàng tiết kiệm', NULL, 1),
(3, 3, N'Trần Văn C', '0987654321', N'789 Đường XYZ, Quận 3, TP.HCM', N'Nhân viên C', '0987654321', 70000.00, GETDATE(), 'VD003', N'Giao hàng nhanh', NULL, 0),
(4, 4, N'Lê Thị D', '0123456789', N'101 Đường DEF, Quận 4, TP.HCM', N'Nhân viên D', '0901234567', 80000.00, GETDATE(), 'VD004', N'Giao hàng tiết kiệm', NULL, 2),
(5, 5, N'Phạm Văn E', '0901234567', N'202 Đường GHI, Quận 5, TP.HCM', N'Nhân viên E', '0123456789', 90000.00, GETDATE(), 'VD005', N'Giao hàng nhanh', NULL, 3),
(6, 6, N'Mai Thị F', '0987654321', N'303 Đường KLM, Quận 6, TP.HCM', N'Nhân viên F', '0987654321', 100000.00, GETDATE(), 'VD006', N'Giao hàng tiết kiệm', NULL, 0),
(7, 7, N'Đỗ Văn G', '0123456789', N'404 Đường NOP, Quận 7, TP.HCM', N'Nhân viên G', '0901234567', 110000.00, GETDATE(), 'VD007', N'Giao hàng nhanh', NULL, 1),
(8, 8, N'Vũ Thị H', '0909876543', N'505 Đường QRS, Quận 8, TP.HCM', N'Nhân viên H', '0123456789', 120000.00, GETDATE(), 'VD008', N'Giao hàng tiết kiệm', NULL, 2),
(9, 9, N'Hoàng Văn I', '0987654321', N'606 Đường TUV, Quận 9, TP.HCM', N'Nhân viên I', '0987654321', 130000.00, GETDATE(), 'VD009', N'Giao hàng nhanh', NULL, 0),
(10, 10, N'Ngô Thị K', '0123456789', N'707 Đường WXY, Quận 10, TP.HCM', N'Nhân viên K', '0901234567', 140000.00, GETDATE(), 'VD010', N'Giao hàng tiết kiệm', NULL, 3);

--- PHIEU TRA HÀNG
IF OBJECT_ID('PHIEUTRA') IS NOT NULL
	DROP TABLE PHIEUTRA
CREATE TABLE PHIEUTRA
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdHoaDon BIGINT NOT  NULL,
	IdNhanVien BIGINT NOT NULL,
	MaPhieu VARCHAR(10) NULL,
	NgayTra DATETIME NULL,
	NgayTao DATETIME DEFAULT GETDATE(),
	TienHoanTra MONEY DEFAULT 0 ,
	GhiChu NVARCHAR(250) NULL,
	CONSTRAINT PK_PHIEUTRA PRIMARY KEY (ID),
	CONSTRAINT FK_PHIEUTRA_NHANVIEN FOREIGN KEY (IdNhanVien) REFERENCES NHANVIEN   (ID),
	CONSTRAINT FK_PHIEUTRA_HOADON FOREIGN KEY (IdHoaDon) REFERENCES HOADON    (ID),
)

INSERT INTO PHIEUTRA (idHoaDo) values
--- CHI TIET PHIEU TRA
IF OBJECT_ID('CHITIETPHIEUTRA') IS NOT NULL
	DROP TABLE CHITIETPHIEUTRA
CREATE TABLE CHITIETPHIEUTRA
(
	ID BIGINT IDENTITY (1,1) NOT NULL,
	IdPhieuTra BIGINT NULL,
	MaCTSP VARCHAR(10) NULL,
	TensSP VARCHAR(20) NULL,
	Mau VARCHAR(20) NULL,
	Size FLOAT NULL,
	ThuongHieu VARCHAR(20) NULL,
	SoLuong INT DEFAULT 0 ,
	NgayTao DATETIME DEFAULT GETDATE(),
	CONSTRAINT PK_CHITIETPHIEUTRA PRIMARY KEY (ID),
	CONSTRAINT FK_CHITIETPHIEUTRA_PHIEUTRA FOREIGN KEY (IdPhieuTra) REFERENCES PHIEUTRA   (ID),
)

SELECT * FROM PHIEUTRA
SELECT * FROM CHITIETPHIEUTRA


SELECT * FROM NHANVIEN

select * from KHACHHANG

UPDATE KHACHHANG
SET GioiTinh = 0
WHERE GioiTinh IS NULL





