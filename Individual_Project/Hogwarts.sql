CREATE SCHEMA `hogwarts` DEFAULT CHARACTER SET utf8 ;

USE hogwarts;

CREATE TABLE `Courses`
(
 `id`        integer NOT NULL AUTO_INCREMENT ,
 `title`     varchar(45) NOT NULL ,
 `stream`    varchar(45) ,
 `type`      varchar(45) ,
 `startDate` datetime ,
 `endDate`   datetime ,
PRIMARY KEY (`id`)
);


CREATE TABLE `Assignments`
(
 `id`             integer NOT NULL AUTO_INCREMENT,
 `title`          varchar(45) NOT NULL ,
 `description`    varchar(45) ,
 `oralMark`       varchar(30) ,
 `totalMark`      varchar(30) ,
 `submissionDate` datetime NOT NULL ,
PRIMARY KEY (`id`)
);


CREATE TABLE `Trainers`
(
 `id`      integer NOT NULL AUTO_INCREMENT,
 `fName`   varchar(30) NOT NULL ,
 `lName`   varchar(30) NOT NULL ,
 `subject` varchar(45) ,
PRIMARY KEY (`id`)
);



CREATE TABLE `Students`
(
 `id`          integer NOT NULL AUTO_INCREMENT,
 `fName`       varchar(30) NOT NULL ,
 `lName`       varchar(30) NOT NULL ,
 `dOfBirth`    date NOT NULL ,
 `tuitionFees` decimal NOT NULL ,
PRIMARY KEY (`id`)
);


CREATE TABLE `hogwarts`.`studentspercourse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fk_St_id` INT NOT NULL,
  `fk_C_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_St_id_idx` (`fk_St_id` ASC) VISIBLE,
  INDEX `fk_C_id_idx` (`fk_C_id` ASC) VISIBLE,
  CONSTRAINT `fk_St_id`
    FOREIGN KEY (`fk_St_id`)
    REFERENCES `hogwarts`.`students` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_C_id`
    FOREIGN KEY (`fk_C_id`)
    REFERENCES `hogwarts`.`courses` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);
    
    
  CREATE TABLE `hogwarts`.`trainerspercourse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fk_Tr_id` INT NOT NULL,
  `fk_C_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Tr_id_idx` (`fk_Tr_id` ASC) VISIBLE,
  INDEX `fk_C_id_idx` (`fk_C_id` ASC) VISIBLE,
  CONSTRAINT `fk_Tr_id`
    FOREIGN KEY (`fk_Tr_id`)
    REFERENCES `hogwarts`.`trainers` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_Co_id`
    FOREIGN KEY (`fk_C_id`)
    REFERENCES `hogwarts`.`courses` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);
    
    
    CREATE TABLE `hogwarts`.`assignmentspercourse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fk_Ass_id` INT NOT NULL,
  `fk_C_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Ass_id_idx` (`fk_Ass_id` ASC) VISIBLE,
  INDEX `fk_Cou_id_idx` (`fk_C_id` ASC) VISIBLE,
  CONSTRAINT `fk_Ass_id`
    FOREIGN KEY (`fk_Ass_id`)
    REFERENCES `hogwarts`.`assignments` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_Cou_id`
    FOREIGN KEY (`fk_C_id`)
    REFERENCES `hogwarts`.`courses` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);
    

CREATE TABLE `hogwarts`.`assignmentsperstudent` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fk_apc_id` INT NOT NULL,
  `fk_spc_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_spc_id_idx` (`fk_spc_id` ASC) VISIBLE,
  INDEX `fk_apc_id_idx` (`fk_apc_id` ASC) VISIBLE,
  CONSTRAINT `fk_spc_id`
    FOREIGN KEY (`fk_spc_id`)
    REFERENCES `hogwarts`.`studentspercourse` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_apc_id`
    FOREIGN KEY (`fk_apc_id`)
    REFERENCES `hogwarts`.`assignmentspercourse` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);
    
CREATE TABLE `hogwarts`.`marksoftheassignments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fk_apc_id` INT NOT NULL,
  `fk_spc_id` INT NOT NULL,
  `fk_tr_id` INT NOT NULL,
  `mark` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_apc2_id_idx` (`fk_apc_id` ASC) VISIBLE,
  INDEX `fk_spc2_id_idx` (`fk_spc_id` ASC) VISIBLE,
  INDEX `fk_Tr5_id_idx` (`fk_tr_id` ASC) VISIBLE,
  CONSTRAINT `fk_apc2_id`
    FOREIGN KEY (`fk_apc_id`)
    REFERENCES `hogwarts`.`assignmentspercourse` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_spc2_id`
    FOREIGN KEY (`fk_spc_id`)
    REFERENCES `hogwarts`.`studentspercourse` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_Tr5_id`
    FOREIGN KEY (`fk_tr_id`)
    REFERENCES `hogwarts`.`trainers` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);

-- COLUMN ADDITION AT THE marksoftheassignments TABLE
ALTER TABLE `hogwarts`.`marksoftheassignments` ADD `turnedIn` BOOLEAN;

    
CREATE TABLE `hogwarts`.`studentusers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  `fk_St_id` INT NOT NULL,
  `type` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  INDEX `fk_St6_id_idx` (`fk_St_id` ASC) VISIBLE,
  CONSTRAINT `fk_St6_id`
    FOREIGN KEY (`fk_St_id`)
    REFERENCES `hogwarts`.`students` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

    CREATE TABLE `hogwarts`.`trainerusers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  `fk_Tr_id` INT NOT NULL,
  `type` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  INDEX `fk_Tr6_id_idx` (`fk_Tr_id` ASC) VISIBLE,
  CONSTRAINT `fk_Tr6_id`
    FOREIGN KEY (`fk_Tr_id`)
    REFERENCES `hogwarts`.`trainers` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
    CREATE TABLE `hogwarts`.`weekdays` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `day` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `hogwarts`.`classhours` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `startEndTime` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `hogwarts`.`schedulepercourse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fk_Day_id` INT NOT NULL,
  `fk_C_id` INT NOT NULL,
  `fk_Ch_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Day_id_idx` (`fk_Day_id` ASC) VISIBLE,
  INDEX `fk_C1_id_idx` (`fk_C_id` ASC) VISIBLE,
  INDEX `fk_Ch_id_idx` (`fk_Ch_id` ASC) VISIBLE,
  CONSTRAINT `fk_Day_id`
    FOREIGN KEY (`fk_Day_id`)
    REFERENCES `hogwarts`.`weekdays` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_C1_id`
    FOREIGN KEY (`fk_C_id`)
    REFERENCES `hogwarts`.`courses` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_Ch_id`
    FOREIGN KEY (`fk_Ch_id`)
    REFERENCES `hogwarts`.`classhours` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);
    
    
INSERT INTO students(fName, lName, dOfBirth, tuitionFees) VALUES('Harry', 'Potter', '2010-5-20', 3000),
 ('Hermione', 'Granger', '2009-12-31', 0), ('Ron', 'Weasly', '2009-4-8', 3000), ('Luna', 'Lovegood', '2010-5-29', 3000),
 ('Tom', 'Riddle', '1980-8-7', 0);

  INSERT INTO trainers(fName, lName, subject) VALUES('Albus', 'Dumbledore', 'Defense against the dark arts');
  INSERT INTO trainers(fName, lName, subject) VALUES('Severus', 'Snape', 'Potions'), ('Minerva', 'McGonagall', 'Transfiguration');

INSERT INTO assignments(title, description, oralMark, totalMark, submissionDate) VALUES('Early Assignment', 'First assignment of the semester', 
'20% of the total mark', 'Oral and writing performance', '2019-06-05 23-59-59'),
('Final assignment', 'Dissertation', '20% of the total mark', 'Oral and writing performance', '2019-08-07 23-59-59');


INSERT INTO courses(title, stream, type, startDate, endDate) VALUES('Defence against the dark arts', 'Dark magic', 'Defensive', '2019-03-25 09-00-00', '2019-07-28 15-00-00'),
('Potions', 'Craft', 'Miscellaneous', '2019-03-25 09-00-00', '2019-07-28 15-00-00'), ('Transfiguration', 'Neutral magic', 'Animagus', '2019-03-25 09-00-00', '2019-07-28 15-00-00');


INSERT INTO studentspercourse(fk_St_id, fk_C_id) VALUES(1, 1), (2,1), (3,1), (4,1), (1,2), (2,2), (4,2),
(1,3), (2,3), (3,3), (5,3);

INSERT INTO trainerspercourse(fk_Tr_id, fk_C_id) VALUES(1,1), (2,1), (2,2), (3,3);

INSERT INTO assignmentspercourse(fk_Ass_id, fk_C_id) VALUES(1,1), (2,1), (2,2), (1,3);

INSERT INTO assignmentsperstudent(fk_apc_id, fk_spc_id) VALUES(1, 1), (1, 2), (1, 3), (1,4), (2,1), (2,2),(2, 3), (2,4), (3,5), (3,6), (3,7),
(4,8), (4,9), (4,10), (4,11);


INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('1', '1', '1', '2');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('2', '1', '2', '2');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('3', '1', '3', '2');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('4', '1', '4', '2');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('5', '2', '1', '1');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('6', '2', '2', '1');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('7', '2', '3', '1');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('8', '2', '4', '1');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('9', '3', '5', '2');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('10', '3', '6', '2');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('11', '3', '7', '2');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('12', '4', '8', '3');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('13', '4', '9', '3');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('14', '4', '10', '3');
INSERT INTO `hogwarts`.`marksoftheassignments` (`id`, `fk_apc_id`, `fk_spc_id`, `fk_tr_id`) VALUES ('15', '4', '11', '3');


INSERT INTO `hogwarts`.`studentusers` (`id`, `username`, `password`, `fName`, `lName`, `fk_St_id`, `type`) VALUES ('1', 'Potter232', '7c23ec20a474662846e22944ce745bdb', 'Harry', 'Potter', '1', 'student');
INSERT INTO `hogwarts`.`studentusers` (`id`, `username`, `password`, `fName`, `lName`, `fk_St_id`, `type`) VALUES ('2', 'Granger121', '79c60f9f5534f23f5e3c924b2848a99f', 'Hermione', 'Granger', '2', 'student');
INSERT INTO `hogwarts`.`studentusers` (`id`, `username`, `password`, `fName`, `lName`, `fk_St_id`, `type`) VALUES ('3', 'Weasly15', '8797e3bdbe5547a13c73b7e11ccfa479', 'Ron', 'Weasly', '3', 'student');
INSERT INTO `hogwarts`.`studentusers` (`id`, `username`, `password`, `fName`, `lName`, `fk_St_id`, `type`) VALUES ('4', 'Lovegood22', '5c885b9cccfc2de17f82ce1701e217c9', 'Luna', 'Lovegood', '4', 'student');
INSERT INTO `hogwarts`.`studentusers` (`id`, `username`, `password`, `fName`, `lName`, `fk_St_id`, `type`) VALUES ('5', 'Riddle13', 'ad9b6a6b4bc1865099696a6a68ac6b87', 'Tom', 'Riddle', '5', 'student');


INSERT INTO `hogwarts`.`trainerusers` (`id`, `username`, `password`, `fName`, `lName`, `fk_Tr_id`, `type`) VALUES ('1', 'Dumbledore21', '6106248817ec4d35967fa554d5c9c891', 'Albus', 'Dumbledore', '1', 'headmaster');
INSERT INTO `hogwarts`.`trainerusers` (`id`, `username`, `password`, `fName`, `lName`, `fk_Tr_id`, `type`) VALUES ('2', 'Snape32', 'a2bfa83715717ff3f9915e406c1e3914', 'Severus', 'Snape', '2', 'trainer');
INSERT INTO `hogwarts`.`trainerusers` (`id`, `username`, `password`, `fName`, `lName`, `fk_Tr_id`, `type`) VALUES ('3', 'McGonagall', '9b65d12c7410d1576de545c60bc19f3b', 'Minerva', 'McGonagall', '3', 'trainer');

INSERT INTO `hogwarts`.`weekdays` (`id`, `day`) VALUES ('1', 'Monday');
INSERT INTO `hogwarts`.`weekdays` (`id`, `day`) VALUES ('2', 'Tuesday');
INSERT INTO `hogwarts`.`weekdays` (`id`, `day`) VALUES ('3', 'Wednesday');
INSERT INTO `hogwarts`.`weekdays` (`id`, `day`) VALUES ('4', 'Thursday');
INSERT INTO `hogwarts`.`weekdays` (`id`, `day`) VALUES ('5', 'Friday');

INSERT INTO `hogwarts`.`classhours` (`id`, `startEndTime`) VALUES ('1', '9:00 - 11:45 am');
INSERT INTO `hogwarts`.`classhours` (`id`, `startEndTime`) VALUES ('2', '12:00 - 2:45 pm');
INSERT INTO `hogwarts`.`classhours` (`id`, `startEndTime`) VALUES ('3', '15:00 - 17:45 pm');

INSERT INTO `hogwarts`.`schedulepercourse` (`id`, `fk_Day_id`, `fk_C_id`, `fk_Ch_id`) VALUES ('1', '1', '1', '1');
INSERT INTO `hogwarts`.`schedulepercourse` (`id`, `fk_Day_id`, `fk_C_id`, `fk_Ch_id`) VALUES ('2', '1', '3', '2');
INSERT INTO `hogwarts`.`schedulepercourse` (`id`, `fk_Day_id`, `fk_C_id`, `fk_Ch_id`) VALUES ('3', '2', '2', '2');
INSERT INTO `hogwarts`.`schedulepercourse` (`id`, `fk_Day_id`, `fk_C_id`, `fk_Ch_id`) VALUES ('4', '3', '1', '1');
INSERT INTO `hogwarts`.`schedulepercourse` (`id`, `fk_Day_id`, `fk_C_id`, `fk_Ch_id`) VALUES ('5', '3', '2', '2');
INSERT INTO `hogwarts`.`schedulepercourse` (`id`, `fk_Day_id`, `fk_C_id`, `fk_Ch_id`) VALUES ('6', '4', '3', '1');
INSERT INTO `hogwarts`.`schedulepercourse` (`id`, `fk_Day_id`, `fk_C_id`, `fk_Ch_id`) VALUES ('7', '5', '1', '3');


-- STUDENTS
SELECT CONCAT(fname, ' ', lname) fullName FROM students;

-- TRAINERS
SELECT CONCAT(fname, ' ', lname) fullName FROM trainers;

-- ASSIGNMENTS
SELECT title FROM assignments;

-- COURSES
SELECT title FROM courses;

-- STUDENTS PER COURSE
SELECT CONCAT(fname, ' ', lname) fullName,  title course FROM students s INNER JOIN studentspercourse spc ON s.id = spc.fk_St_id INNER JOIN courses c ON c.id = spc.fk_C_id;

-- TRAINERS PER COURSE
SELECT CONCAT(fname, ' ', lname) fullName, title course FROM trainers t INNER JOIN trainerspercourse tpc ON t.id = tpc.fk_Tr_id INNER JOIN courses c ON c.id = tpc.fk_C_id;

-- ASSIGNMENT PER COURSE
SELECT a.title Assignment, c.title Course FROM assignments a INNER JOIN assignmentspercourse apc ON a.id = apc.fk_Ass_id INNER JOIN courses c ON c.id = apc.fk_C_id;

-- ASSIGNMENTS PER COURSE PER STUDENT ----> INNER JOIN 1st SOLUTION
SELECT a.title Assignment , c.title Course , CONCAT(fname, ' ', lname) fullName FROM assignments a INNER JOIN assignmentspercourse apc ON a.id = apc.fk_Ass_id
INNER JOIN courses c ON c.id = apc.fk_C_id INNER JOIN studentspercourse spc ON c.id = spc.fk_C_id INNER JOIN students s On s.id = spc.fk_St_id;

-- ASSIGNMENTS PER COURSE PER STUDENT ----> 2nd SOLUTION USING THE ASSIGNMENTSPERCOURSE TABLE
SELECT a.title Assignment , c.title Course , CONCAT(fname, ' ', lname) fullName FROM assignments a, courses c, students s, studentspercourse spc, assignmentspercourse apc, assignmentsperstudent aps
WHERE a.id = apc.fk_Ass_id AND apc.id = aps.fk_apc_id AND spc.id = aps.fk_spc_id AND s.id = spc.fk_St_id AND c.id = spc.fk_C_id ;

-- THE LIST OF STUDENTS THAT BELONG TO MORE THAN ONE COURSES
SELECT CONCAT(fname, ' ', lname) fullName FROM students s INNER JOIN studentspercourse spc ON s.id = spc.fk_St_id 
INNER JOIN courses c ON c.id = spc.fk_C_id GROUP BY fname, lname HAVING COUNT(fk_C_id) > 1 ;

SELECT * FROM studentspercourse;

-- ASSIGNMENTS PER COURSE PER STUDENT PER TRAINER  ------------------------------------------------- CORRECT
SELECT a.title Assignment , c.title Course , CONCAT(s.fname, ' ', s.lname) St_fullName, CONCAT(t.fName,' ', t.lName) Tr_fullName, moa.turnedIn FROM assignments a, courses c, students s, trainers t, studentspercourse spc, assignmentspercourse apc, marksoftheassignments moa, trainerspercourse tpc
WHERE a.id = apc.fk_Ass_id AND apc.id = moa.fk_apc_id AND spc.id = moa.fk_spc_id AND s.id = spc.fk_St_id AND c.id = spc.fk_C_id AND t.id = tpc.fk_Tr_id AND tpc.fk_C_id = c.id AND t.id = moa.fk_tr_id;

    
SELECT title, day, startEndTime FROM courses c, classhours ch, weekdays w, schedulepercourse sch WHERE c.id = sch.fk_C_id AND ch.id = sch.fk_Ch_id 
AND w.id = sch.fk_Day_id AND c.title = 'Potions';

-- DATES OF SUBMISSION OF ASSIGNMENTS
SELECT a.title Assignment , c.title Course , CONCAT(fname, ' ', lname) fullName, a.submissionDate FROM assignments a, courses c, students s, studentspercourse spc, assignmentspercourse apc, assignmentsperstudent aps
WHERE a.id = apc.fk_Ass_id AND apc.id = aps.fk_apc_id AND spc.id = aps.fk_spc_id AND s.id = spc.fk_St_id AND c.id = spc.fk_C_id AND s.lname = 'Potter';

-- COURSES PER STUDENT
SELECT c.title, CONCAT(s.fname,' ', s.lname) fullname FROM courses c, students s, studentspercourse spc WHERE c.id = spc.fk_C_id AND s.id = spc.fk_St_id AND s.lname = 'Potter';


-- ASSIGNMENTS PER COURSE PER TRAINER PER SPECIFIC STUDENT
SELECT a.title Assignment , c.title Course , CONCAT(s.fname, ' ', s.lname) St_fullName, CONCAT(t.fName,' ', t.lName) Tr_fullName, moa.turnedIn FROM assignments a, courses c, students s, trainers t, studentspercourse spc, assignmentspercourse apc, marksoftheassignments moa, trainerspercourse tpc
WHERE a.id = apc.fk_Ass_id AND apc.id = moa.fk_apc_id AND spc.id = moa.fk_spc_id AND s.id = spc.fk_St_id AND c.id = spc.fk_C_id AND t.id = tpc.fk_Tr_id AND tpc.fk_C_id = c.id AND t.id = moa.fk_tr_id AND t.lName = 'Snape';


select * from assignmentsperstudent;

select * from students;

select * from assignmentspercourse;

select * from schedulepercourse;

select * from studentspercourse;

SELECT * FROM trainers;

select * from marksoftheassignments;

select * from assignments;

select * from courses;

select * from classhours;

select * from trainerspercourse;

select * from trainerusers;

