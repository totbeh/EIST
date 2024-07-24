package de.tum.in.ase.eist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseTest {

    // TODO 1: Test getCourseTitle()
    @Test
    void testGetCourseTitle() {
        String title = "FPV";
        Course course = new Course(title);
        assertEquals(title, course.getTitle());
    }

    // TODO 2: Test getNumberOfAttendees()
    @Test
    void testNoAttendees() {
        Course course = new Course("FPV");
        assertEquals(0, course.getNumberOfAttendees());
    }

    @Test
    void testThreeAttendees() {
        Course course = new Course("FPV");
        Student student1 = new Student("alo", "alo", "2121", "121", "222");
        Student student2 = new Student("alo", "alo", "2121", "121", "222");
        Student student3 = new Student("alo", "alo", "2121", "121", "222");
        course.addAttendee(student1);
        course.addAttendee(student2);
        course.addAttendee(student3);
        assertEquals(3, course.getNumberOfAttendees());
    }

}
