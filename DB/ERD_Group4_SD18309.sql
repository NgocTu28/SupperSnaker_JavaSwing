CREATE DATABASE ERD_Group4_SD18309 
GO 
USE ERD_Group4_SD18309 
GO

CREATE TABLE ThuongHieu (
	ID_ThuongHieu INT IDENTITY PRIMARY KEY NOT NULL,
	Ten_ThuongHieu NVARCHAR(256) NOT NULL
)

INSERT INTO ThuongHieu (Ten_ThuongHieu)
VALUES
    (N'Adidas'),
    (N'Nike'),
    (N'Puma'),
    (N'Converse'),
    (N'Vans');

CREATE TABLE Mau (
	ID_Mau INT IDENTITY PRIMARY KEY NOT NULL,
	Ten_Mau NVARCHAR(256) NOT NULL
)

INSERT INTO Mau (Ten_Mau)
VALUES
    (N'Đen'),
    (N'Trắng'),
    (N'Đỏ'),
    (N'Xanh dương'),
    (N'Xanh lá'),
    (N'Cam'),
    (N'Hồng'),
    (N'Vàng');


CREATE TABLE Size (
	ID_Size INT IDENTITY PRIMARY KEY NOT NULL,
	KichCo_Size FLOAT NOT NULL
)

INSERT INTO Size (KichCo_Size)
VALUES
    (36.0),
    (37.0),
    (38.0),
    (39.0),
    (40.0),
    (41.0),
    (42.0),
    (43.0),
    (44.0),
    (45.0);

CREATE TABLE ChatLieu (
	ID_ChatLieu INT IDENTITY PRIMARY KEY NOT NULL,
	Ten_ChatLieu NVARCHAR(256) NOT NULL
)

INSERT INTO ChatLieu (Ten_ChatLieu)
VALUES
    (N'Canvas'),
    (N'Vải'),
    (N'Nubuck'),
    (N'Được cấu tạo'),
    (N'Da tổng hợp'),
    (N'Len'),
    (N'Kaki');

CREATE TABLE SanPham (
	id_SanPham UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	Ma_SanPham VARCHAR(20) UNIQUE,
	Ten_SanPham NVARCHAR(256) NOT NULL,
	MoTa_SanPham NVARCHAR(500),
	TrangThai_SanPham NVARCHAR(100) NOT NULL
)

Insert Into SanPham(Ma_SanPham,Ten_SanPham,MoTa_SanPham,TrangThai_SanPham) Values ('SP01',N'GIÀY STAN SMITH SPORTY & RICH',N'Authentic',N'Còn Hàng'),
																					('SP02',N'Giày Sanba OG Sporty & Rich',N'',N'Còn Hàng'),
																					('SP03',N'GIÀY GRAND COURT CLOUDFOAM COMFORT',N'Hàng Hiếm',N'Còn Hàng'),
																					('SP04',N'GIÀY QUESTAR',N'Ngừng sản xuất',N'Hết Hàng'),
																					('SP05',N'GIÀY GRAND COURT CLOUDFOAM COMFORT',N'Giày xách tay',N'Còn Hàng'),
																					('SP06',N'GIÀY ULTRABOUNCE',N'','Hết Hàng')


CREATE TABLE ChucVu (
	ID_ChucVu INT IDENTITY PRIMARY KEY NOT NULL,
	Ten_ChucVu NVARCHAR(100) NOT NULL
)

INSERT INTO ChucVu (Ten_ChucVu)
VALUES
    (N'Nhân Viên'),
    (N'Quản Lý');


CREATE TABLE NhanVien (
	Id_NhanVien UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	Ma_NhanVien VARCHAR(20) UNIQUE,
	Ten_NhanVien NVARCHAR(256) NOT NULL,
	MatKhau_NhanVien VARCHAR(50),
	CCCD_NhanVien BIGINT NOT NULL,
	Email_NhanVien VARCHAR(200) NOT NULL,
	Sdt_NhanVien INT NOT NULL,
	GioiTinh_NhanVien NVARCHAR(10) NOT NULL,
	DiaChi_NhanVien NVARCHAR (256) NOT NULL,
	ID_ChucVu INT FOREIGN KEY REFERENCES ChucVu(ID_ChucVu),
	TrangThaiLamViec_NhanVien BIT NOT NULL
)

INSERT INTO NhanVien (Ma_NhanVien,Ten_NhanVien,MatKhau_NhanVien,CCCD_NhanVien,Email_NhanVien,Sdt_NhanVien,GioiTinh_NhanVien,DiaChi_NhanVien,ID_ChucVu,TrangThaiLamViec_NhanVien) VALUES ('NV01',N'Vũ Ngọc Tú','abc12345',001202045622,'vutu8288@gmail.com',0397962849,N'Nam',N'TP Hòa Bình, Hòa Bình',2,0),
																																										('NV02',N'Trần Tiến Mạnh','efgh123',001202046798,'manhtt@fpt.edu.vn',0393566948,N'Nam',N'Thành phố Buôn Ma Thuột, Đắk Lắk',2,0),
																																										('NV03',N'Tạ Bá Hào','aye987',00134668866,'haotb@fpt.edu.vn',0399723567,N'Nam',N'Thành phố Vinh, Nghệ An',1,0),
																																										('NV04',N'Đỗ Thị Ánh Ngọc','ngoc246',00132547564,'ngocdta@fpt.edu.vn',0972456728,N'Nữ',N'Thành phố Hạ Long, Quảng Ninh',1,0),
																																										('NV05',N'Nguyễn Đức Dương','duongk34',00132547564,'duongnd@fpt.edu.vn',0856724082,N'Nữ',N'Thành phố Thái Bình, Thái Bình',1,0)


CREATE TABLE DotGiamGia (
	ID_DotGiamGia UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	Ma_DotGiamGia VARCHAR(20) UNIQUE,
	Ten_DotGiamGia NVARCHAR(256) NOT NULL,
	NgayBatDau_DotGiamGia DATE NOT NULL,
	NgayKetThuc_DotGiamGia DATE NOT NULL,
	TrangThai_DotGiamGia BIT NOT NULL -- 0 là Áp Dụng / 1 là hết hạn
)

INSERT INTO DotGiamGia (Ma_DotGiamGia, Ten_DotGiamGia, NgayBatDau_DotGiamGia, NgayKetThuc_DotGiamGia, TrangThai_DotGiamGia)
VALUES
    ('DOT001', N'Giảm giá mùa hè', '2023-06-01', '2023-08-31', 1),
	('DOT002', N'Giảm giá mùa thu', '2023-10-01', '2023-10-31', 0),
	('DOT003', N'Giảm giá Black Friday', '2023-11-11', '2023-11-13', 0),
	('DOT004', N'Giảm giá cuối năm', '2023-12-01', '2023-12-30', 0),
	('DOT005', N'Giảm giá mùa xuân', '2022-12-01', '2023-02-27', 1);

CREATE TABLE HinhThuc_DotGiamGia (
	ID_HinhThucDotGiamGia UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	ID_DotGiamGia UNIQUEIDENTIFIER FOREIGN KEY REFERENCES  DotGiamGia (ID_DotGiamGia),
	LoaiGiamGia BIT NOT NULL, -- 0 giảm theo % / 1 giảm theo $
	GiaTriToiThieu FLOAT NOT NULL,
	GiaTriGiam FLOAT NOT NULL
)

INSERT INTO HinhThuc_DotGiamGia (ID_DotGiamGia,LoaiGiamGia,GiaTriToiThieu,GiaTriGiam) VALUES ('55A0D3DA-ECA0-4A52-9164-30E2E3A22FDC',0,200000,2),
																							('CE7CC384-502E-454F-970A-494C8E4C96EF',1,500000,10000),
																							('69EF520B-E246-43B6-A7FE-622A3A8FFF17',0,1000000,120000),
																							('F3D38350-4291-4362-8BF4-C14C3B546A17',1,2000000,20),
																							('6BA16B21-2933-4298-8625-E4F8BAB4EBB1',1,500000,5);

Select * From ThuongHieu
Select * From Mau
Select * From ChatLieu
Select * From SanPham
Select * From Size
Select * From NhanVien

CREATE TABLE SanPham_ChiTiet (
	ID_SanPhamChiTiet UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	GiaNiemYet_SanPhamChiTiet FLOAT NOT NULL,
	SoLuong_SanPhamChiTiet INT NOT NULL,
	ID_ThuongHieu INT  FOREIGN KEY REFERENCES ThuongHieu (ID_ThuongHieu),
	ID_Mau INT  FOREIGN KEY REFERENCES Mau (ID_Mau),
	ID_ChatLieu INT FOREIGN KEY REFERENCES ChatLieu(ID_ChatLieu),
	ID_SanPham UNIQUEIDENTIFIER FOREIGN KEY REFERENCES SanPham(ID_SanPham),
	ID_Size INT FOREIGN KEY REFERENCES Size(ID_Size),
	Anh_SanPhamChiTiet VARCHAR(256) NOT NULL,
	ID_NhanVien UNIQUEIDENTIFIER FOREIGN KEY REFERENCES NhanVien(ID_NhanVien),
	TrangThai_SanPhamChiTiet BIT NOT NULL  -- 0 là còn Hàng / 1 là Hết Hàng
)

INSERT INTO SanPham_ChiTiet (GiaNiemYet_SanPhamChiTiet, SoLuong_SanPhamChiTiet, ID_ThuongHieu, ID_Mau, ID_ChatLieu, ID_SanPham, ID_Size, Anh_SanPhamChiTiet, ID_NhanVien, TrangThai_SanPhamChiTiet) VALUES
					(1200000,3,1,2,2,'A592B756-DCAF-4044-8F27-52323EE13434',8,'Ảnh QUESTAR','6271B6CA-7DBB-4E64-AC17-ABC7209EA02D',0),
					(2300000,10,3,3,1,'319D8130-D76B-477E-8F7C-915C56E411AE',7,'Ảnh GRAND COURT CLOUDFOAM COMFORT','E632968F-C249-4D41-B59C-9C57BECAE5F3',0),
					(900000,100,4,5,5,'C08AE30B-B890-4290-B08A-9574ECCA9AAC',6,'Ảnh STAN SMITH SPORTY & RICH','E632968F-C249-4D41-B59C-9C57BECAE5F3',0),
					(4000000,6,2,1,2,'4D4CF967-5123-4D6B-9457-DD685232D046',5,'Ảnh Sanba OG Sporty & Rich','0036B75C-8378-4B5C-8C8F-C874EDF4B21E',0),
					(600000,0,2,4,2,'640F6F1C-C1CB-4E0E-95C1-F586B8F132D1',3,'Ảnh ULTRABOUNCET','6271B6CA-7DBB-4E64-AC17-ABC7209EA02D',1);

CREATE TABLE HinhThuc_PhieuGiamGia (
	ID_HinhThucPhieu UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	GiaTriDieuKien FLOAT NOT NULL,
	LoaiGiamGia BIT NOT NULL, -- 0 giảm theo % / 1 giảm theo $
	GiaTriToiDa FLOAT NOT NULL
)

INSERT INTO HinhThuc_PhieuGiamGia (GiaTriDieuKien,LoaiGiamGia,GiaTriToiDa) VALUES (400000,0,20),
(1000000,1,100000),(700000,0,10),(4000000,1,300000),(7000000,0,40);

CREATE TABLE PhieuGiamGia (
	ID_PhieuGiamGia UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	Ma_PhieuGiamGia VARCHAR(256) NOT NULL,
	Ten_PhieuGiamGia NVARCHAR(256) NOT NULL,
	ID_HinhThucPhieu UNIQUEIDENTIFIER FOREIGN KEY REFERENCES  HinhThuc_PhieuGiamGia (ID_HinhThucPhieu),
	SoPhieuPhatHanh_PhieuGiamGia INT NOT NULL,
	NgayBatDau_PhieuGiamGia DATE NOT NULL,
	NgayKetThuc_PhieuGiamGia DATE NOT NULL,
	MoTa_PhieuGiamGia NVARCHAR(MAX) ,
	TrangThai_PhieuGiamGia BIT NOT NULL -- 0: còn hạn / 1 hết hạn
)
 

 INSERT INTO PhieuGiamGia (Ma_PhieuGiamGia, Ten_PhieuGiamGia, ID_HinhThucPhieu, SoPhieuPhatHanh_PhieuGiamGia, NgayBatDau_PhieuGiamGia, NgayKetThuc_PhieuGiamGia, MoTa_PhieuGiamGia, TrangThai_PhieuGiamGia) VALUES 
 ('PGG001', N'Phiếu giảm giá mùa hè', '429CEACE-F48D-41D3-8FE8-01C67B49A881', 100, '2023-06-01', '2023-08-31', N'Áp dụng cho mùa hè 2023', 1),
  ('PGG002', N'Phiếu giảm giá mùa Xuân', '437CC8AE-13AB-4409-B848-38190D5B5C1B', 10, '2023-02-01', '2023-03-29', N'Áp dụng cho mùa xuân 2023', 1),
   ('PGG003', N'Phiếu giảm giá khai trương', '7F7F5866-AA2C-4E46-8763-74A5A015523C', 30, '2023-11-01', '2023-11-28', N'Áp dụng cho sinh nhật', 0),
    ('PGG004', N'Phiếu giảm giá tuần lễ vàng', '39C69861-9C67-4F45-AC24-F61C45DA132C', 7, '2023-11-13', '2023-11-20', N'Áp dụng cho tuần lễ vàng', 0),
	  ('PGG005', N'Phiếu giảm giá tết', '429CEACE-F48D-41D3-8FE8-01C67B49A881', 100, '2024-02-01', '2024-03-01', N'Áp dụng cho mùa tết 2024', 0);


CREATE TABLE KhachHang (
	ID_KhachHang UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	Ma_KhachHang NVARCHAR(256) NOT NULL,
	HoVaTen_KhachHang NVARCHAR(100) NOT NULL,
	SDT_KhachHang INT NOT NULL,
	Email_KhachHang VARCHAR(256) NOT NULL,
	DiaChi_KhachHang VARCHAR(564) NOT NULL,
	Diem_KhachHang INT NOT NULL,
	CapBac_KhachHang INT NOT NULL,
	NgayTao_KhachHang DATE NOT NULL,
	ID_NhanVien UNIQUEIDENTIFIER FOREIGN KEY REFERENCES NhanVien(ID_NhanVien)
)

SELECT * FROM NhanVien

INSERT INTO KhachHang (Ma_KhachHang, HoVaTen_KhachHang, SDT_KhachHang, Email_KhachHang, DiaChi_KhachHang, Diem_KhachHang, CapBac_KhachHang, NgayTao_KhachHang, ID_NhanVien)
VALUES ('KH001', N'Nguyễn Văn Tài', 0982345546, 'tai2toc@gmail.com', N'Quận 1, TP.HCM', 10, 1, '2023-06-01', '6271B6CA-7DBB-4E64-AC17-ABC7209EA02D'),
('KH002', N'Lê Thị Cẩm Vân', 0982954613, 'vancam234@gmail.com', N'thành phố Thái Nguyên, Thái Nguyên', 10, 2, '2023-02-19', 'E632968F-C249-4D41-B59C-9C57BECAE5F3'),
('KH003', N'Vũ Ngọc Tùng', 0982345546, 'tungthoi8998@gmail.com', N'Huyện Quất Lâm, Nam Định', 30, 3, '2023-09-14', '6271B6CA-7DBB-4E64-AC17-ABC7209EA02D'),
('KH004', N'Trần Thu Hiền', 0998173854, 'hientt8081@gmail.com', N'Huyện Lương Sơn, Hòa Bình', 34, 2, '2021-07-11', '0036B75C-8378-4B5C-8C8F-C874EDF4B21E'),
('KH005', N'Nguyễn Minh Quân', 0393484712, 'quancritiano@gmail.com', N'Huyện Kim Bôi, Hòa Bình', 3, 1, '2022-12-21', '6271B6CA-7DBB-4E64-AC17-ABC7209EA02D');

-------------------------------------------
CREATE TABLE PhieuGiaoHang (
	ID_PhieuGiao UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	ID_KhachHang UNIQUEIDENTIFIER FOREIGN KEY REFERENCES KhachHang(ID_KhachHang),
	TenNguoiNhan_PhieuGiaoHang NVARCHAR(256) NOT NULL,
	SDTNguoiNhan_GiaoHang INT NOT NULL,
	DiaChi_GiaoHang NVARCHAR(256) NOT NULL,
	GiaShip_GiaoHang FLOAT NOT NULL,
	NgayTao_GiaoHang DATE NOT NULL  DEFAULT GETDATE(),
	TrangThai_GiaoHang NVARCHAR(100) NOT NULL,
	MaVanDon UNIQUEIDENTIFIER NOT NULL DEFAULT NEWID(),
	DonViVanChuyen NVARCHAR(256) NOT NULL
)

SELECT * FROM KhachHang

INSERT INTO PhieuGiaoHang (ID_KhachHang, TenNguoiNhan_PhieuGiaoHang, SDTNguoiNhan_GiaoHang, DiaChi_GiaoHang, GiaShip_GiaoHang, TrangThai_GiaoHang, MaVanDon, DonViVanChuyen)
VALUES ('B329C50E-AFCB-46EB-B2D9-AD4EE20D5106', N'Nguyễn Văn Tài', 982345546, N'Quận 1, TP.HCM', 50000, N'Đang giao', NEWID(), N'Giao Hàng Express'),
('57A2DD1A-CED3-41BC-8D4B-C8DDBC66164A', N'Trần Thu Hiền', 998173854, N'Huyện Lương Sơn, Hòa Bình', 90000, N'Đang giao', NEWID(), N'Giao Hàng Express'),
('BAF53D71-1E5C-4320-9528-D1609D125AE3', N'Lê Thị Cẩm Vân', 982954613, N'thành phố Thái Nguyên, Thái Nguyên', 40000, N'Đang giao', NEWID(), N'Giao Hàng Express'),
('B8E66A67-A5AB-4991-8073-DBE68ECAD1C2', N'Vũ Ngọc Tùng', 982345546, N'Huyện Quất Lâm, Nam Ðịnh', 10000, N'Đang giao', NEWID(), N'Giao Hàng Express'),
('0F2B8114-CFB5-4FA7-A7EB-E4D226763B75', N'Nguyễn Minh Quân', 393484712, N'Huyện Kim Bôi, Hòa Bình', 20000, N'Đang giao', NEWID(), N'Giao Hàng Express');

CREATE TABLE PhieuGiao_SanPham (
	ID_PhieuGiao_SanPham UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	ID_SanPhamChiTiet UNIQUEIDENTIFIER FOREIGN KEY REFERENCES SanPham_ChiTiet(ID_SanPhamChiTiet),
	ID_PhieuGiaoHang UNIQUEIDENTIFIER FOREIGN KEY REFERENCES PhieuGiaoHang (ID_PhieuGiao),
	SoLuong INT Not Null
)
SELECT * FROM SanPham_ChiTiet
INSERT INTO PhieuGiao_SanPham (ID_SanPhamChiTiet,ID_PhieuGiaoHang,SoLuong) VALUES ('6445DBFC-C170-4379-AE8C-2F33142FD478','57A2DD1A-CED3-41BC-8D4B-C8DDBC66164A',1),
('E47D2A4D-F564-43A6-8D4A-6C11FFA070AA','B329C50E-AFCB-46EB-B2D9-AD4EE20D5106',1),
('62A8E9A6-F610-4B8E-A3B8-9A0BC5932EBA','BAF53D71-1E5C-4320-9528-D1609D125AE3',1),
('575326CB-4DEF-4C0E-A9F1-AA10E36170D2','B8E66A67-A5AB-4991-8073-DBE68ECAD1C2',1),
('DC0DFF0C-A449-4D7C-9A68-CA007F89D75F','0F2B8114-CFB5-4FA7-A7EB-E4D226763B75',1);
-----------------------------------------------

CREATE TABLE HoaDon (
	ID_HoaDon UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
	Ma_HoaDon NVARCHAR(256) NOT NULL,
	NgayTao_HoaDon DATE NOT NULL DEFAULT GETDATE(),
	PhuongThucThanhToan_HoaDon NVARCHAR(256) NOT NULL,
	TongTien_HoaDon FLOAT NOT NULL,
	DiemDoi_HoaDon INT NOT NULL,
	ID_PhieuGiamGia UNIQUEIDENTIFIER FOREIGN KEY REFERENCES PhieuGiamGia(ID_PhieuGiamGia),
	ID_DotGiamGia UNIQUEIDENTIFIER FOREIGN KEY REFERENCES DotGiamGia(ID_DotGiamGia),
	GiamGia FLOAT NOT NULL,
	TienPhaiTra FLOAT NOT NULL,
	ID_KhachHang UNIQUEIDENTIFIER FOREIGN KEY REFERENCES KhachHang(ID_KhachHang),
	ID_NhanVien UNIQUEIDENTIFIER FOREIGN KEY REFERENCES NhanVien(ID_NhanVien),
	ID_PhieuGiao UNIQUEIDENTIFIER FOREIGN KEY REFERENCES PhieuGiaoHang(ID_PhieuGiao),
	TrangThai_HoaDon BIT NOT NULL  -- 1/ Chưa Thanh Toán / 0 Đã thanh toán
)

Select * From PhieuGiamGia 
Select * From DotGiamGia
Select * From KhachHang
Select * From NhanVien
Select * From PhieuGiaoHang

INSERT INTO HoaDon (Ma_HoaDon, PhuongThucThanhToan_HoaDon, TongTien_HoaDon, DiemDoi_HoaDon, ID_PhieuGiamGia, ID_DotGiamGia, GiamGia, TienPhaiTra, ID_KhachHang, ID_NhanVien, ID_PhieuGiao, TrangThai_HoaDon)
VALUES 
('HD001', N'Thanh toán bằng thẻ', 1000000, 10, '34F5CC83-B0DC-4BEC-AB54-221A2C0C0C58', 'CE7CC384-502E-454F-970A-494C8E4C96EF', 10000, 900900, 'BAF53D71-1E5C-4320-9528-D1609D125AE3', '06C61B60-F22B-495B-9088-C8344214D496', '81016CED-7CE0-4730-BF4A-E339D5B49875', 0),
('HD002', N'Thanh toán bằng thẻ', 2000000, 1, '34F5CC83-B0DC-4BEC-AB54-221A2C0C0C58', 'CE7CC384-502E-454F-970A-494C8E4C96EF', 100003, 900900, 'BAF53D71-1E5C-4320-9528-D1609D125AE3', '06C61B60-F22B-495B-9088-C8344214D496', '81016CED-7CE0-4730-BF4A-E339D5B49875', 0),
('HD003', N'Thanh toán bằng thẻ', 3000000, 4, '34F5CC83-B0DC-4BEC-AB54-221A2C0C0C58', 'CE7CC384-502E-454F-970A-494C8E4C96EF', 1000230, 900900, 'BAF53D71-1E5C-4320-9528-D1609D125AE3', '06C61B60-F22B-495B-9088-C8344214D496', '81016CED-7CE0-4730-BF4A-E339D5B49875', 0),
('HD004', N'Thanh toán bằng thẻ', 4000000, 3, '34F5CC83-B0DC-4BEC-AB54-221A2C0C0C58', 'CE7CC384-502E-454F-970A-494C8E4C96EF', 100200, 900900, 'BAF53D71-1E5C-4320-9528-D1609D125AE3', '06C61B60-F22B-495B-9088-C8344214D496', '81016CED-7CE0-4730-BF4A-E339D5B49875', 0),
('HD005', N'Thanh toán bằng thẻ', 5000000, 1, '34F5CC83-B0DC-4BEC-AB54-221A2C0C0C58', 'CE7CC384-502E-454F-970A-494C8E4C96EF', 100030, 900900, 'BAF53D71-1E5C-4320-9528-D1609D125AE3', '06C61B60-F22B-495B-9088-C8344214D496', '81016CED-7CE0-4730-BF4A-E339D5B49875', 0);


CREATE TABLE HoaDon_ChiTiet (
	ID_HoaDonChiTiet UNIQUEIDENTIFIER PRIMARY KEY NOT NULL DEFAULT NEWID(),
	ID_HoaDon UNIQUEIDENTIFIER FOREIGN KEY REFERENCES HoaDon(ID_HoaDon),
	ID_SanPhamChiTiet UNIQUEIDENTIFIER FOREIGN KEY REFERENCES SanPham_ChiTiet(ID_SanPhamChiTiet),
	SoLuong_HoaDonChiTiet INT NOT NULL,
	DonGia FLOAT NOT NULL,
	ThanhTien FLOAT NOT NULL
)
Select * From HoaDon
Select * From SanPham_ChiTiet

INSERT INTO HoaDon_ChiTiet (ID_HoaDon, ID_SanPhamChiTiet, SoLuong_HoaDonChiTiet, DonGia, ThanhTien)
VALUES ('B41179FB-A1D2-4A27-BCD1-79B498BAF7D8', '6445DBFC-C170-4379-AE8C-2F33142FD478', 1, 500000, 490000),
('6FD0E16C-6DFD-48B3-A6DB-805F2E4F9D19', 'E47D2A4D-F564-43A6-8D4A-6C11FFA070AA', 1, 500000, 490000),
('54F0D196-5056-4752-AC10-9920A1745D2F', '575326CB-4DEF-4C0E-A9F1-AA10E36170D2', 2, 600000, 500000),
('C819BFED-23E6-417E-9472-CA6043E4E5BA', '6445DBFC-C170-4379-AE8C-2F33142FD478', 3, 200000, 120000),
('7C819F0F-A35A-4D4D-BCC0-CF4236659966', 'DC0DFF0C-A449-4D7C-9A68-CA007F89D75F', 4, 450000, 200000);


CREATE TABLE LichSuGiaoDich (
	ID_GiaoDich UNIQUEIDENTIFIER PRIMARY KEY NOT NULL DEFAULT NEWID(),
	ID_KhachHang UNIQUEIDENTIFIER FOREIGN KEY REFERENCES KhachHang(ID_KhachHang),
	ID_HoaDon UNIQUEIDENTIFIER FOREIGN KEY REFERENCES HoaDon(ID_HoaDon),
	NgayGiaoDich Date Not Null,
	TienKhachTra FLoat Not Null,
	TienThua Float ,
	PhuongThucTT NVARCHAR(256) NOT NULL,
	TrangThai BIT NOT NULL,
	ID_NhanVien UNIQUEIDENTIFIER FOREIGN KEY REFERENCES NhanVien(ID_NhanVien)
)

Select * From KhachHang
Select * From HoaDon
Select * From NhanVien

INSERT INTO LichSuGiaoDich (ID_KhachHang, ID_HoaDon, NgayGiaoDich, TienKhachTra, TienThua, PhuongThucTT, TrangThai, ID_NhanVien)
VALUES ('B329C50E-AFCB-46EB-B2D9-AD4EE20D5106', 'B41179FB-A1D2-4A27-BCD1-79B498BAF7D8', '2023-11-07', 1000.00, 0.00, N'Thanh toán bằng thẻ', 0, '467FEBC1-9F55-45CE-9520-43C47AC82389'),
('57A2DD1A-CED3-41BC-8D4B-C8DDBC66164A', '6FD0E16C-6DFD-48B3-A6DB-805F2E4F9D19', '2023-11-07', 100000, 0.00, N'Thanh toán bằng thẻ', 0, 'E632968F-C249-4D41-B59C-9C57BECAE5F3'),
('BAF53D71-1E5C-4320-9528-D1609D125AE3', '54F0D196-5056-4752-AC10-9920A1745D2F', '2023-11-07', 200000, 0.00, N'Thanh toán bằng thẻ', 1, '6271B6CA-7DBB-4E64-AC17-ABC7209EA02D'),
('B8E66A67-A5AB-4991-8073-DBE68ECAD1C2', 'C819BFED-23E6-417E-9472-CA6043E4E5BA', '2023-11-07', 300000, 0.00, N'Thanh toán bằng thẻ', 0, '06C61B60-F22B-495B-9088-C8344214D496'),
('0F2B8114-CFB5-4FA7-A7EB-E4D226763B75', '7C819F0F-A35A-4D4D-BCC0-CF4236659966', '2023-11-07', 4000760, 0.00, N'Thanh toán bằng thẻ', 1, '0036B75C-8378-4B5C-8C8F-C874EDF4B21E')
--------------------------------------------------------------
