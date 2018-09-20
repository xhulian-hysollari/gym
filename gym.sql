-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 31, 2018 at 04:31 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gym`
--

-- --------------------------------------------------------

--
-- Table structure for table `subscriptions`
--

CREATE TABLE `subscriptions` (
  `id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `subscription_type` int(11) NOT NULL,
  `starting_date` varchar(50) NOT NULL,
  `ending_date` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `total_paid_by_client` double NOT NULL,
  `paid` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subscriptions`
--

INSERT INTO `subscriptions` (`id`, `first_name`, `last_name`, `email`, `phone_number`, `subscription_type`, `starting_date`, `ending_date`, `price`, `total_paid_by_client`, `paid`, `created_by`) VALUES
(1, 'First', 'Client', 'Client@mail.com', '0696969699', 2, 'Sat Sep 01 02:00:00 CEST 2018', 'Thu Nov 01 02:00:00 CET 2018', 80, 80, 1, 'Dejvis'),
(2, 'Another', 'Subscription', 'another@mail.com', '0696969698', 3, 'Fri Aug 31 02:00:00 CEST 2018', 'Fri Nov 30 02:00:00 CET 2018', 110, 300, 1, 'Dejvis'),
(3, 'Finished', 'Subscription', 'finished@mail.com', '0696969697', 3, 'Mon Jan 01 01:00:00 CET 2018', 'Sun Apr 01 01:00:00 CEST 2018', 110, 110, 1, 'Dejvis'),
(4, 'Employee', 'Subscription', 'added_by_employee@mail.com', '0696869865', 4, 'Sat Sep 01 02:00:00 CEST 2018', 'Fri Mar 01 02:00:00 CET 2019', 200, 0, 0, 'employee');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `username`, `password`, `role`) VALUES
(1, 'Dejvis', 'Projku', 'Dejvis', 'dejvis123', 'admin'),
(4, 'First', 'Employee', 'employee', 'employee', 'Employee');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `subscriptions`
--
ALTER TABLE `subscriptions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `subscriptions`
--
ALTER TABLE `subscriptions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
