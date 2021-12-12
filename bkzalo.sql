-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 06, 2021 lúc 09:32 AM
-- Phiên bản máy phục vụ: 10.4.21-MariaDB
-- Phiên bản PHP: 7.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `bkzalo`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`id`, `username`, `password`) VALUES
(12, 'thanhsang', '123'),
(13, 'tanhau', '123'),
(14, 'ngocduy', '123'),
(15, 'aaa', '123'),
(16, 'bbb', '123'),
(17, 'abc', '123');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `idUserSend` int(11) NOT NULL,
  `idUserReceive` int(11) NOT NULL,
  `type` int(11) NOT NULL DEFAULT 1,
  `text` text COLLATE utf8_bin NOT NULL DEFAULT '',
  `filepath` varchar(500) COLLATE utf8_bin NOT NULL DEFAULT '',
  `filename` varchar(500) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `message`
--

INSERT INTO `message` (`id`, `idUserSend`, `idUserReceive`, `type`, `text`, `filepath`, `filename`) VALUES
(1, 12, 13, 1, 'hello man', '', ''),
(2, 12, 14, 1, 'aloooooooooooooooooooo', '', ''),
(3, 13, 12, 1, 'sao vaayj bro', '', ''),
(4, 12, 13, 1, 'hoi cai nay chut', '', ''),
(5, 13, 14, 1, 'ee duy', '', ''),
(6, 12, 14, 1, 'allo', '', ''),
(7, 12, 13, 2, '1', '', ''),
(8, 12, 13, 2, '2', '', ''),
(9, 12, 13, 2, '3', '', ''),
(10, 12, 13, 2, '4', '', ''),
(11, 12, 13, 2, '5', '', ''),
(12, 13, 12, 2, '24', '', ''),
(13, 13, 12, 2, '24', '', ''),
(43, 12, 13, 4, '', 'E:\\PBL4\\BServer\\server_data\\43.jpg', '1.jpg'),
(44, 12, 13, 3, '', 'E:\\PBL4\\BServer\\server_data\\44.docx', '1.docx'),
(45, 13, 12, 1, 'hey you', '', ''),
(46, 12, 13, 1, 'một hai 3 4', '', ''),
(47, 13, 12, 1, 'lô lô ***', '', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `gender` tinyint(4) NOT NULL DEFAULT 0,
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `username`, `gender`, `image`, `status`) VALUES
(12, 'thanhsang', 0, '', 0),
(13, 'tanhau', 0, '', 0),
(14, 'ngocduy', 0, '', 0),
(15, 'aaa', 0, '', 0),
(16, 'bbb', 0, '', 0),
(17, 'abc', 0, '', 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Chỉ mục cho bảng `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `usersend_message` (`idUserSend`),
  ADD KEY `userreceive_message` (`idUserReceive`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT cho bảng `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `userreceive_message` FOREIGN KEY (`idUserReceive`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usersend_message` FOREIGN KEY (`idUserSend`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_account` FOREIGN KEY (`id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
