-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2022 at 01:20 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `electricgrid_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `c_id` int(11) NOT NULL,
  `c_name` varchar(255) NOT NULL,
  `account_no` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`c_id`, `c_name`, `account_no`, `email`) VALUES
(2, 'Kavi Ratnapala', '003945513', 'kr45@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `e_id` int(11) NOT NULL,
  `employee_name` varchar(255) NOT NULL,
  `salary` double NOT NULL,
  `department_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`e_id`, `employee_name`, `salary`, `department_name`) VALUES
(2, 'kanishka', 100000, 'Accounts');

-- --------------------------------------------------------

--
-- Table structure for table `paymentsdetails`
--

CREATE TABLE `paymentsdetails` (
  `id` int(11) NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `totalAmountDue` double NOT NULL,
  `no_of_days` int(11) NOT NULL,
  `c_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `powerconsumptiondetails`
--

CREATE TABLE `powerconsumptiondetails` (
  `bill_id` int(11) NOT NULL,
  `unitAmount` double NOT NULL,
  `t_hrs` int(11) NOT NULL,
  `killowatt` int(11) NOT NULL,
  `c_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `powerconsumptiondetails`
--

INSERT INTO `powerconsumptiondetails` (`bill_id`, `unitAmount`, `t_hrs`, `killowatt`, `c_id`) VALUES
(2, 134, 100, 100, 2),
(3, 134, 234, 10, 2);

-- --------------------------------------------------------

--
-- Table structure for table `powerfailuredetails`
--

CREATE TABLE `powerfailuredetails` (
  `complaint_id` int(255) NOT NULL,
  `cust_name` varchar(255) NOT NULL,
  `nic` varchar(12) NOT NULL,
  `area` varchar(255) NOT NULL,
  `grid_name` varchar(255) NOT NULL,
  `complaint` text NOT NULL,
  `status` varchar(255) NOT NULL,
  `date` date NOT NULL DEFAULT current_timestamp(),
  `time` time NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `powerfailuredetails`
--

INSERT INTO `powerfailuredetails` (`complaint_id`, `cust_name`, `nic`, `area`, `grid_name`, `complaint`, `status`, `date`, `time`) VALUES
(1, 'Mahinda Rajapakshe', '779564532V', 'colombo 3', 'A', 'Power Cut', 'solved', '2022-04-25', '16:36:43'),
(3, 'Dudley Sirisena', '239564532V', 'Polonnaruwa', 'C', 'Power Cut', 'pending', '2022-04-25', '16:37:59');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`c_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`e_id`);

--
-- Indexes for table `paymentsdetails`
--
ALTER TABLE `paymentsdetails`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cus_id_fk_3` (`c_id`);

--
-- Indexes for table `powerconsumptiondetails`
--
ALTER TABLE `powerconsumptiondetails`
  ADD PRIMARY KEY (`bill_id`),
  ADD KEY `c_id_fk_2` (`c_id`);

--
-- Indexes for table `powerfailuredetails`
--
ALTER TABLE `powerfailuredetails`
  ADD PRIMARY KEY (`complaint_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `c_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `e_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `paymentsdetails`
--
ALTER TABLE `paymentsdetails`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `powerconsumptiondetails`
--
ALTER TABLE `powerconsumptiondetails`
  MODIFY `bill_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `powerfailuredetails`
--
ALTER TABLE `powerfailuredetails`
  MODIFY `complaint_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `paymentsdetails`
--
ALTER TABLE `paymentsdetails`
  ADD CONSTRAINT `cus_id_fk_3` FOREIGN KEY (`c_id`) REFERENCES `customer` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `powerconsumptiondetails`
--
ALTER TABLE `powerconsumptiondetails`
  ADD CONSTRAINT `c_id_fk_2` FOREIGN KEY (`c_id`) REFERENCES `customer` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
