Below i have placed the userNames/passwords of the students/trainers/head master and some comments for the flow of this Project!

The program begins asking the user to state his identity and enter his/her username and password.
The login() method examines if the entries are valid checking the data of the corresponding tables of the
Hogwarts Database.

The methods menuStudent()/menuTrainer()/menuHeadMaster() implement tasks like printing lists (e.g. Students per Course,
Assignments per Student) or creating records in the tables of the database (e.g. the Student enrolls to a new Course).

Inside the UserLogin class, an inner class named PasswordHashing is implemented. Through the method password(), the hashed
passwords are generated and later stored at the database.

Into the OpenCloseConnection class, the open() method creates a connection with the database.
There is no need to implement a close connection method because of the way we connect to the database ( try() syntax ).

All the CrudOn() methods serve the Head Master user role (e.g. CrudOnCourses).

The interface PrintedMenus contains default methods in order to use them whenever it's needed without any obligation to
implement every method of the interface.



------------------------------------------------------USERNAMES & PASSWORDS------------------------------------------------------------------- 
If you are not familiar with Hogwarts, Albus Dumbledore is the Head Master! Try not to delete him from the Database because then
you will not have access to the program as Head Master :)


    STUDENTS
================
userName      password
--------      --------
Potter232     chosenone

Granger121    studyislife

Weasly15      redhead

Lovegood22    weirdone

Riddle13      darklord

 
    TRAINERS
==================
userName      password
--------      ---------
Snape32       thesnake
McGonagall    thecat
 
    HEAD MASTER
=====================
userName          password
--------          ----------
Dumbledore21      thephoenix












