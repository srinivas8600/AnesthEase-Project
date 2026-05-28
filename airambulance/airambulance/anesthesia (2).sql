-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 18, 2024 at 09:52 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `anesthesia`
--

-- --------------------------------------------------------

--
-- Table structure for table `adddoctor`
--

CREATE TABLE `adddoctor` (
  `did` varchar(20) NOT NULL,
  `name` text NOT NULL,
  `phno` text NOT NULL,
  `pass` text NOT NULL,
  `gender` text NOT NULL,
  `speciality` text NOT NULL,
  `img` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `adddoctor`
--

INSERT INTO `adddoctor` (`did`, `name`, `phno`, `pass`, `gender`, `speciality`, `img`) VALUES
('Ajay1', 'Ajay', '7989898988', 'Ajay', 'Male', 'orthoo', 'doctor_images/Ajay1.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` text NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `password`) VALUES
('saveetha123', 'welcome123');

-- --------------------------------------------------------

--
-- Table structure for table `adminprofile`
--

CREATE TABLE `adminprofile` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phno` varchar(12) NOT NULL,
  `email` varchar(50) NOT NULL,
  `gender` varchar(8) NOT NULL,
  `age` varchar(4) NOT NULL,
  `img` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `adminprofile`
--

INSERT INTO `adminprofile` (`id`, `name`, `phno`, `email`, `gender`, `age`, `img`) VALUES
('saveetha123', 'ajay kumar', '8989898980', '123@gmail.co', 'mal', '21', 'admin/C40EEA15-5273-484B-874F-AAE0E21AD791.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `a_profile`
--

CREATE TABLE `a_profile` (
  `did` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `speciality` varchar(50) NOT NULL,
  `Gender` varchar(50) NOT NULL,
  `gmail` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `a_profile`
--

INSERT INTO `a_profile` (`did`, `name`, `speciality`, `Gender`, `gmail`) VALUES
(',fpxiv', 'name', 'speciality', 'm/f', '@gmail.com'),
('123', 'name', 'speciality', 'm/f', '@gmail.com'),
('123h', 'name', 'speciality', 'm/f', '@gmail.com'),
('A123', 'name', 'speciality', 'm/f', '@gmail.com'),
('A12378', 'name', 'speciality', 'm/f', '@gmail.com'),
('aadi12', 'name', 'speciality', 'm/f', '@gmail.com'),
('Ajay12', 'name', 'speciality', 'm/f', '@gmail.com'),
('ajay123', 'name', 'speciality', 'm/f', '@gmail.com'),
('Am12', 'name', 'speciality', 'm/f', '@gmail.com'),
('Anu21', 'name', 'speciality', 'm/f', '@gmail.com'),
('Anu23', 'name', 'speciality', 'm/f', '@gmail.com'),
('Anuroop21', 'anu', 'knee', 'male', ''),
('Anuroop22', 'anu', 'knee', 'male', ''),
('B123', 'name', 'speciality', 'm/f', '@gmail.com'),
('bsndbx', 'name', 'speciality', 'm/f', '@gmail.com'),
('C123', 'name', 'speciality', 'm/f', '@gmail.com'),
('cavba558', 'name', 'speciality', 'm/f', '@gmail.com'),
('cdyju', 'name', 'speciality', 'm/f', '@gmail.com'),
('D123', 'name', 'speciality', 'm/f', '@gmail.com'),
('d12342', 'name', 'speciality', 'm/f', '@gmail.com'),
('D127', 'name', 'speciality', 'm/f', '@gmail.com'),
('Dha12', 'name', 'speciality', 'm/f', '@gmail.com'),
('dha342', 'name', 'speciality', 'm/f', '@gmail.com'),
('Dhanesh12', 'name', 'speciality', 'm/f', '@gmail.com'),
('doc1', 'name', 'speciality', 'm/f', '@gmail.com'),
('fhk56', 'name', 'speciality', 'm/f', '@gmail.com'),
('fjnv558', 'name', 'speciality', 'm/f', '@gmail.com'),
('funk', 'name', 'speciality', 'm/f', '@gmail.com'),
('gh566', 'name', 'speciality', 'm/f', '@gmail.com'),
('ghg5', 'name', 'speciality', 'm/f', '@gmail.com'),
('gopal1234', 'name', 'speciality', 'm/f', '@gmail.com'),
('h123', 'name', 'speciality', 'm/f', '@gmail.com'),
('hehxhdh', 'name', 'speciality', 'm/f', '@gmail.com'),
('hkjjhhh558', 'name', 'speciality', 'm/f', '@gmail.com'),
('k123', 'name', 'speciality', 'm/f', '@gmail.com'),
('Kim', 'name', 'speciality', 'm/f', '@gmail.com'),
('kinkikoo1', 'anu', 'knee', 'male', ''),
('knee1', 'name', 'speciality', 'm/f', '@gmail.com'),
('M67', 'name', 'speciality', 'm/f', '@gmail.com'),
('Mah43', 'name', 'speciality', 'm/f', '@gmail.com'),
('mah4321', 'name', 'speciality', 'm/f', '@gmail.com'),
('Male', 'name', 'speciality', 'm/f', '@gmail.com'),
('mjk112', 'name', 'speciality', 'm/f', '@gmail.com'),
('mjk113', 'name', 'speciality', 'm/f', '@gmail.com'),
('mjk114', 'name', 'speciality', 'm/f', '@gmail.com'),
('mklu1', 'name', 'speciality', 'm/f', '@gmail.com'),
('mnjk12', 'name', 'speciality', 'm/f', '@gmail.com'),
('N123', 'name', 'speciality', 'm/f', '@gmail.com'),
('nagma123', 'name', 'speciality', 'm/f', '@gmail.com'),
('Nare1234', 'name', 'speciality', 'm/f', '@gmail.com'),
('p12345', 'name', 'speciality', 'm/f', '@gmail.com'),
('pu1', 'name', 'speciality', 'm/f', '@gmail.com'),
('pu34', 'name', 'speciality', 'm/f', '@gmail.com'),
('qwewqf', 'name', 'speciality', 'm/f', '@gmail.com'),
('Sam21', 'name', 'speciality', 'm/f', '@gmail.com'),
('sam213', 'name', 'speciality', 'm/f', '@gmail.com'),
('Samsung', 'name', 'speciality', 'm/f', '@gmail.com'),
('Select Gender', 'name', 'speciality', 'm/f', '@gmail.com'),
('ssu122', 'name', 'speciality', 'm/f', '@gmail.com'),
('sus122', 'name', 'speciality', 'm/f', '@gmail.com'),
('welcome123', 'name', 'speciality', 'm/f', '@gmail.com'),
('welcome1234', 'name', 'speciality', 'm/f', '@gmail.com'),
('xgh45', 'name', 'speciality', 'm/f', '@gmail.com'),
('xvkk', 'name', 'speciality', 'm/f', '@gmail.com'),
('yehegvv2', 'name', 'speciality', 'm/f', '@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `doctorlogin`
--

CREATE TABLE `doctorlogin` (
  `id` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctorlogin`
--

INSERT INTO `doctorlogin` (`id`, `password`) VALUES
('pu1', 'pi123'),
('', ''),
('yehegvv2', '890uu'),
('ghg5', 'ghik'),
('funk', 'jhg'),
('Anu21', 'Anu21'),
('Ajay12', '123456'),
('Sam21', 'Sam12'),
('hehxhdh', 'hsbs'),
('bsndbx', 'bdxb'),
('welcome123', 's123'),
('welcome1234', 's123'),
('Male', '123'),
('Select Gender', ''),
('fjnv558', 'vvb888'),
('cavba558', 'habab859'),
('fhk56', 'yhbft698'),
('cdyju', 'yxshnc'),
('xvkk', 'vhk'),
('A123', 'a123'),
('xgh45', 'gjj57'),
('Ajay123', 'a1234'),
('Mah43', 'mah45'),
('ssu122', '24557'),
('pu34', 'pu54'),
('A12378', 'a123'),
('Dha12', 'd34D'),
('sam213', 'sam12'),
('M67', 'M567'),
('Anu23', 'Anu43'),
('p12345', 'p123'),
('gh566', 'hhgg5688'),
('N123', 'Naveen'),
('k123', 'kaberi123'),
('Am12', '1234'),
('mjk112', ''),
('mjk113', ''),
('mjk114', 'mjk113'),
('doc1', '1'),
('hkjjhhh558', 'gHbVs'),
('qwewqf', 'fade'),
('mah4321', 'mah4321'),
('aadi12', 'aadi123'),
('h123', '123'),
('123h', '123'),
(',fpxiv', ',gcuj'),
('mnjk12', 'mnjk12'),
('knee1', 'knee1'),
('mklu1', 'mklu1'),
('Samsung', 'Samsung'),
('Kim', 'Kim'),
('Anuroop21', 'anu76'),
('Anuroop22', 'anu76'),
('kinkikoo1', 'anu76'),
('123', '123'),
('B123', '1234'),
('C123', '1234'),
('D123', '1234'),
('D127', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `pdetails`
--

CREATE TABLE `pdetails` (
  `pid` varchar(20) NOT NULL,
  `name` text NOT NULL,
  `phno` text NOT NULL,
  `age` int(3) NOT NULL,
  `gender` text NOT NULL,
  `height` float NOT NULL,
  `weight` float NOT NULL,
  `bmi` float NOT NULL,
  `date` text NOT NULL,
  `img` text NOT NULL,
  `Crhm` int(3) NOT NULL,
  `Cwt` int(3) NOT NULL,
  `Cds` int(3) NOT NULL,
  `Cmmp` int(3) NOT NULL,
  `Cmoi` int(3) NOT NULL,
  `Ctmd` int(3) NOT NULL,
  `Ctmj` int(3) NOT NULL,
  `Cnm` int(3) NOT NULL,
  `Cnc` int(3) NOT NULL,
  `Cbmi` int(3) NOT NULL,
  `Ushb` int(3) NOT NULL,
  `Utt` int(3) NOT NULL,
  `Uttd` int(3) NOT NULL,
  `Uhd` int(3) NOT NULL,
  `Uhsk` int(3) NOT NULL,
  `Uesk` int(3) NOT NULL,
  `Uask` int(3) NOT NULL,
  `Upr` int(3) NOT NULL,
  `Uva` int(3) NOT NULL,
  `Ctotal` int(3) NOT NULL,
  `Utotal` int(3) NOT NULL,
  `total` int(3) NOT NULL,
  `atype` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pdetails`
--

INSERT INTO `pdetails` (`pid`, `name`, `phno`, `age`, `gender`, `height`, `weight`, `bmi`, `date`, `img`, `Crhm`, `Cwt`, `Cds`, `Cmmp`, `Cmoi`, `Ctmd`, `Ctmj`, `Cnm`, `Cnc`, `Cbmi`, `Ushb`, `Utt`, `Uttd`, `Uhd`, `Uhsk`, `Uesk`, `Uask`, `Upr`, `Uva`, `Ctotal`, `Utotal`, `total`, `atype`, `timestamp`) VALUES
('Nag12', 'Nagarjuna', '7890895668', 23, 'Male', 5.8, 68, 2.02, '2024-07-04', 'img/Nag12.jpg', 2, 2, 5, 3, 2, 2, 2, 3, 2, 2, 0, 1, 1, 1, 0, 0, 0, 2, 0, 25, 5, 30, 'Likely to be difficult airway', '2024-07-18 07:36:20'),
('Jay', 'Jaykumar', '8986558888', 32, 'Male', 8.9, 56, 0.71, '2024-07-04', 'img/Jay.jpg', 2, 2, 5, 3, 2, 1, 2, 2, 2, 2, 0, 1, 1, 1, 1, 1, 1, 4, 2, 23, 12, 35, 'Difficult airway', '2024-07-18 07:50:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adminprofile`
--
ALTER TABLE `adminprofile`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `a_profile`
--
ALTER TABLE `a_profile`
  ADD PRIMARY KEY (`did`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
