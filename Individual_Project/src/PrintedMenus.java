public interface PrintedMenus {

    //CREATION OF DEFAULT METHODS IN AN INTERFACE
    //IN THIS CASE I CAN CALL THE METHOD WHEREVER I NEED AND I DON'T HAVE TO IMPLEMENT TO WHOLE INTERFACE
    //I TRIED TO AVOID AN EXTRA INHERITANCE RELATIONSHIP FOR THE PRINTING OF THE MENUS

    default void crudOnSchedulePerCourseMenu() {
        System.out.println("You are in the Schedule per Courses menu!\nPress -1- to print the schedule\nPress " +
                "-2- to add a course to the schedule\nPress -3- to remove a course from" +
                " the schedule\nPress -4- to update the Schedule\nPress -5- to exit the Schedule per Courses menu");
        System.out.println();
    }

    default void headMasterMenu() {
        System.out.println("Press -1- if you want to enter the Course's menu\nPress -2- if you want to enter the Student's menu\n"+
        "Press -3- if you want to enter the Assignment's menu\nPress -4- if you want to enter the Trainer's menu\n"+
        "Press -5- if you want to enter the Students per Courses menu\nPress -6- if you want to enter the Trainers per Courses menu\n"+
        "Press -7- if you want to enter the Assignments per Courses menu\nPress -8- if you want to enter the Schedule per Courses menu\n" +
                "Press -9 if you want to leave the Head Master's menu");
    }

    default void crudOnCoursesMenu() {
        System.out.println();
        System.out.println("You are in the Course's menu!\nPress -1- to print a  quick list of all " +
                "the Courses\nPress -2- to print all the data of every Course of Hogwarts\nPress " +
                "-3- to Update any Course\nPress -4- to create a new Course\nPress -5- to delete a Course\n" +
                "Press -6- to exit the Course's menu");
        System.out.println();
    }

    default void crudOnStudentsMenu() {
        System.out.println();
        System.out.println("You are in the Student's menu!\nPress -1- to print a  quick list of all " +
                "the Students\nPress -2- to print all the data of every Student of Hogwarts\nPress " +
                "-3- to Update any Student\nPress -4- to create a new Student\nPress -5- to delete a Student\n" +
                "Press -6- to exit the Student's menu");
        System.out.println();
    }

    default void crudOnAssignmentsMenu() {
        System.out.println();
        System.out.println("You are in the Assignments' menu!\nPress -1- to print a  quick list of " +
                "the Assignments a course can have\nPress -2- to print all the data of the available " +
                "assignments\nPress " + "-3- to Update any Assignment\nPress -4- to create a new Assignment" +
                "\nPress -5- to delete an Assignment\nPress -6- to exit the Assignment's menu");
        System.out.println();
    }

    default void crudOnTrainersMenu() {
        System.out.println();
        System.out.println("You are in the Trainer's menu!\nPress -1- to print a  quick list of " +
                "the Trainers\nPress -2- to print all the data of the available " +
                "Trainers\nPress " + "-3- to Update any Trainer\nPress -4- to create a new Trainer" +
                "\nPress -5- to delete a Trainer\nPress -6- to exit the Trainer's menu");
        System.out.println();
    }

    default void crudOnStudentsPerCourseMenu() {
        System.out.println();
        System.out.println("You are in the Students per Courses menu!\nPress -1- to print a list of every student " +
                "of Hogwarts by the course he/she is enrolled\nPress -2- to appoint any student to a course that " +
                "he/she is not enrolled \nPress -3- to unenroll a student from a course\nPress -4- to exit the " +
                "Students per Courses menu\n*** If you need to Update any Student per Course record, it is recommended" +
                " to unenroll\nthe Student from the Course first (choice -3-), and appoint him/her to the Course of your choice after" +
                " (choice -2-) ***");
        System.out.println();
    }

    default void crudOnTrainersPerCourseMenu() {
        System.out.println();
        System.out.println("You are in the Trainers per Courses menu!\nPress -1- to print a list of every Trainer " +
                "of Hogwarts by the Course he/she is appointed\nPress -2- to appoint any Trainer to a course that " +
                "he is not enrolled in\nPress -3- to unenroll a Trainer from a course\nPress -4- to exit the " +
                "Trainers per Courses menu\n*** If you need to Update any Trainer per Course record, it is recommended" +
                " to release\nthe Trainer from the Course first (choice -3-), and appoint him/her to the Course of your choice after" +
                " (choice -2-) ***");
        System.out.println();
    }

    default void crudOnAssignmentsPerCourseMenu() {
        System.out.println();
        System.out.println("You are in the Assignments per Courses menu!\nPress -1- to print a list of the available" +
                " assignments\nPress -2- to appoint an Assignment to a course\nPress -3- to remove an Assignment from a" +
                " course\nPress -4- to exit the Assignments per Courses menu\n*** If you need to Update any Assignment per Course record, it is recommended" +
                " to remove\nthe Assignment from the Course first (choice -3-), and appoint it to the Course of your choice after" +
                " (choice -2-) ***");
        System.out.println();
    }

    default void userStudentMenu() {
        System.out.println();
        System.out.println("Press -1- if you want to enroll to a new course\nPress -2- if you want to see your daily " +
                "schedule per course\nPress -3- if you want to see the dates of submission of the Assignments per Course\n" +
                "Press -4- if you want to submit any assignments\nPress -5- if you want to exit the menu. ");
        System.out.println();
    }

    default void userTrainerMenu() {
        System.out.println();
        System.out.println("Press -1- if you want to view all the courses you are enrolled\nPress -2- to view all " +
                "the students per course\nPress -3- if you want to view all the students of the courses that" +
                " you instruct\nPress -4- to view all the assignments per student per course for the courses that you teach" +
                "\nPress -5- to mark all the assignments of your courses\nPress -6- to exit the menu");
        System.out.println();
    }
}
