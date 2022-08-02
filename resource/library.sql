CREATE DATABASE library;
USE library;
--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
CREATE TABLE IF NOT EXISTS `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(225) NOT NULL,
  `lastname` varchar(225) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`id`, `firstname`, `lastname`) VALUES
(3, 'Mcsween', 'Pierre-Yves'),
(5, 'Niang', 'Thione'),
(9, 'Djomo', 'Wilfried'),
(16, 'Hill', 'Napoleon');

-- --------------------------------------------------------

--
-- Table structure for table `textbook`
--

DROP TABLE IF EXISTS `textbook`;
CREATE TABLE IF NOT EXISTS `textbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(225) NOT NULL,
  `author_id` int(11) NOT NULL,
  `isbn` int(11) NOT NULL,
  `editor` varchar(225) NOT NULL,
  `publication_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `textbook`
--
ALTER TABLE `textbook`
  ADD CONSTRAINT `textbook_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;


CREATE DATABASE librarytest;
use librarytest;

DROP TABLE IF EXISTS `author`;
CREATE TABLE IF NOT EXISTS `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(225) NOT NULL,
  `lastname` varchar(225) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`id`, `firstname`, `lastname`) VALUES
(1, 'Liticia', 'Anze'),
(4, 'Diva', 'Kouam'),
(23, 'Liti', 'AKL'),
(54, 'Walter', 'Obrian');

-- --------------------------------------------------------

--
-- Table structure for table `textbook`
--

DROP TABLE IF EXISTS `textbook`;
CREATE TABLE IF NOT EXISTS `textbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(225) NOT NULL,
  `author_id` int(11) NOT NULL,
  `isbn` int(11) NOT NULL,
  `editor` varchar(225) NOT NULL,
  `publication_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `textbook`
--
ALTER TABLE `textbook`
  ADD CONSTRAINT `textbook_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE;

