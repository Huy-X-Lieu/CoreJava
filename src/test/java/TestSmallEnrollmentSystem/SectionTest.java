package TestSmallEnrollmentSystem;

import com.CoreJava.smallEnrollmentSystem.Course;
import com.CoreJava.smallEnrollmentSystem.Section;
import com.CoreJava.smallEnrollmentSystem.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SectionTest {

    @Test
    void constructorStoresWellFormattedArguments() {
        Course course = createCourse();
        Section section = new Section(course, "001", "Jane Smith", 30);

        assertSame(course, section.getCourse());
        assertEquals("001", section.getSectionNumber());
        assertEquals("Jane Smith", section.getInstructor());
        assertEquals(30, section.getCapacity());
    }

    @ParameterizedTest
    @MethodSource("validSectionNumbers")
    void constructorAcceptsWellFormattedSectionNumbers(String sectionNumber) {
        Section section = new Section(createCourse(), sectionNumber, "Jane Smith", 30);

        assertEquals(sectionNumber, section.getSectionNumber());
    }

    @ParameterizedTest
    @MethodSource("invalidSectionNumbers")
    void constructorRejectsMalformedSectionNumbers(String sectionNumber) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Section(createCourse(), sectionNumber, "Jane Smith", 30)
        );
    }

    @Test
    void constructorRejectsNullSectionNumber() {
        assertThrows(
                NullPointerException.class,
                () -> new Section(createCourse(), null, "Jane Smith", 30)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidInstructorNames")
    void constructorRejectsMalformedInstructorNames(String instructorName) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Section(createCourse(), "001", instructorName, 30)
        );
    }

    @Test
    void constructorRejectsNullInstructorName() {
        assertThrows(
                NullPointerException.class,
                () -> new Section(createCourse(), "001", null, 30)
        );
    }

    @Test
    void constructorRejectsNullCourse() {
        assertThrows(
                NullPointerException.class,
                () -> new Section(null, "001", "Jane Smith", 30)
        );
    }

    @Test
    void constructorRejectsInvalidCapacity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Section(createCourse(), "001", "Jane Smith", 0)
        );
    }

    @Test
    void setInstructorStoresWellFormattedName() {
        Section section = createSection();

        section.setInstructor("John Doe");

        assertEquals("John Doe", section.getInstructor());
    }

    @ParameterizedTest
    @MethodSource("invalidInstructorNames")
    void setInstructorRejectsMalformedNameAndPreservesCurrentValue(String instructorName) {
        Section section = createSection();

        assertThrows(IllegalArgumentException.class, () -> section.setInstructor(instructorName));
        assertEquals("Jane Smith", section.getInstructor());
    }

    @Test
    void setInstructorRejectsNullNameAndPreservesCurrentValue() {
        Section section = createSection();

        assertThrows(NullPointerException.class, () -> section.setInstructor(null));
        assertEquals("Jane Smith", section.getInstructor());
    }

    @Test
    void setCapacityRejectsInvalidCapacityAndPreservesCurrentValue() {
        Section section = createSection();

        assertThrows(IllegalArgumentException.class, () -> section.setCapacity(0));
        assertEquals(30, section.getCapacity());
    }

    @Test
    void setCapacityStoresValueEqualToCurrentEnrollmentCount() {
        Section section = new Section(createCourse(), "001", "Jane Smith", 3);

        assertTrue(section.addEnrolledStudent(createStudent("S001")));
        assertTrue(section.addEnrolledStudent(createStudent("S002")));
        section.setCapacity(2);

        assertEquals(2, section.getCapacity());
        assertTrue(section.isSectionFull());
    }

    @Test
    void setCapacityRejectsCapacityBelowCurrentEnrollmentCountAndPreservesCurrentValue() {
        Section section = new Section(createCourse(), "001", "Jane Smith", 3);

        assertTrue(section.addEnrolledStudent(createStudent("S001")));
        assertTrue(section.addEnrolledStudent(createStudent("S002")));

        assertThrows(IllegalArgumentException.class, () -> section.setCapacity(1));

        assertEquals(3, section.getCapacity());
        assertEquals(2, section.getEnrolledStudents().size());
    }

    @Test
    void addEnrolledStudentAddsNewStudentAndReturnsTrue() {
        Section section = createSection();
        Student student = createStudent("S001");

        assertTrue(section.addEnrolledStudent(student));

        assertEquals(1, section.getEnrolledStudents().size());
        assertTrue(section.getEnrolledStudents().contains(student));
    }

    @Test
    void addEnrolledStudentRejectsNullStudentAndPreservesEnrollments() {
        Section section = createSection();

        assertThrows(NullPointerException.class, () -> section.addEnrolledStudent(null));

        assertEquals(0, section.getEnrolledStudents().size());
    }

    @Test
    void addEnrolledStudentReturnsFalseForDuplicateStudentAndPreservesEnrollmentCount() {
        Section section = createSection();
        Student student = createStudent("S001");
        Student duplicateStudent = new Student("  s001  ", "Jane Smith",
                "jane.smith@example.com");

        assertTrue(section.addEnrolledStudent(student));
        assertFalse(section.addEnrolledStudent(duplicateStudent));

        assertEquals(1, section.getEnrolledStudents().size());
        assertTrue(section.getEnrolledStudents().contains(student));
    }

    @Test
    void addEnrolledStudentReturnsFalseWhenSectionIsFullAndPreservesEnrollments() {
        Section section = new Section(createCourse(), "001", "Jane Smith", 1);
        Student firstStudent = createStudent("S001");
        Student secondStudent = createStudent("S002");

        assertTrue(section.addEnrolledStudent(firstStudent));
        assertFalse(section.addEnrolledStudent(secondStudent));

        assertEquals(1, section.getEnrolledStudents().size());
        assertTrue(section.getEnrolledStudents().contains(firstStudent));
        assertFalse(section.getEnrolledStudents().contains(secondStudent));
    }

    @Test
    void addStudentToWaitListAddsNewStudentAndReturnsTrue() throws Exception {
        Section section = createSection();
        Student student = createStudent("S001");

        assertTrue(section.addStudentToWaitList(student));

        assertEquals(List.of(student), waitlistSnapshot(section));
    }

    @Test
    void addStudentToWaitListRejectsNullStudentAndPreservesWaitlist() throws Exception {
        Section section = createSection();
        Student student = createStudent("S001");

        assertTrue(section.addStudentToWaitList(student));
        assertThrows(NullPointerException.class, () -> section.addStudentToWaitList(null));

        assertEquals(List.of(student), waitlistSnapshot(section));
    }

    @Test
    void addStudentToWaitListReturnsFalseForDuplicateStudentIdAndPreservesOriginalStudent()
            throws Exception {
        Section section = createSection();
        Student student = createStudent("S001");
        Student duplicateStudent = new Student("  s001  ", "Jane Smith",
                "jane.smith@example.com");

        assertTrue(section.addStudentToWaitList(student));
        assertFalse(section.addStudentToWaitList(duplicateStudent));

        List<Student> waitlistedStudents = waitlistSnapshot(section);
        assertEquals(1, waitlistedStudents.size());
        assertSame(student, waitlistedStudents.get(0));
    }

    @Test
    void addStudentToWaitListPreservesInsertionOrder() throws Exception {
        Section section = createSection();
        Student firstStudent = createStudent("S001");
        Student secondStudent = createStudent("S002");
        Student thirdStudent = createStudent("S003");

        assertTrue(section.addStudentToWaitList(firstStudent));
        assertTrue(section.addStudentToWaitList(secondStudent));
        assertTrue(section.addStudentToWaitList(thirdStudent));

        assertEquals(List.of(firstStudent, secondStudent, thirdStudent),
                waitlistSnapshot(section));
    }

    @Test
    void addStudentToWaitListAddsStudentWhenSectionIsFullWithoutChangingEnrollments()
            throws Exception {
        Section section = new Section(createCourse(), "001", "Jane Smith", 1);
        Student enrolledStudent = createStudent("S001");
        Student waitlistedStudent = createStudent("S002");

        assertTrue(section.addEnrolledStudent(enrolledStudent));
        assertTrue(section.isSectionFull());
        assertTrue(section.addStudentToWaitList(waitlistedStudent));

        assertEquals(1, section.getEnrolledStudents().size());
        assertTrue(section.getEnrolledStudents().contains(enrolledStudent));
        assertFalse(section.getEnrolledStudents().contains(waitlistedStudent));
        assertEquals(List.of(waitlistedStudent), waitlistSnapshot(section));
    }

    private static Stream<Arguments> validSectionNumbers() {
        return Stream.of(
                Arguments.of("001"),
                Arguments.of("A01"),
                Arguments.of("LAB1")
        );
    }

    private static Stream<Arguments> invalidSectionNumbers() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of(" 001"),
                Arguments.of("001 "),
                Arguments.of("\t001"),
                Arguments.of("001\n"),
                Arguments.of("00 1"),
                Arguments.of("001-002"),
                Arguments.of("001_002"),
                Arguments.of("001.002")
        );
    }

    private static Stream<Arguments> invalidInstructorNames() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of(" Jane Smith"),
                Arguments.of("Jane Smith "),
                Arguments.of("\tJane Smith"),
                Arguments.of("Jane Smith\n")
        );
    }

    private static Section createSection() {
        return new Section(createCourse(), "001", "Jane Smith", 30);
    }

    private static Student createStudent(String studentId) {
        return new Student(studentId, "John Doe", studentId.toLowerCase() + "@example.com");
    }

    private static Course createCourse() {
        return new Course("CSC 400", "Introduction to Java", 3);
    }

    @SuppressWarnings("unchecked")
    private static List<Student> waitlistSnapshot(Section section) throws Exception {
        Field waitlistField = Section.class.getDeclaredField("waitlist");
        waitlistField.setAccessible(true);
        Queue<Student> waitlist = (Queue<Student>) waitlistField.get(section);

        return List.copyOf(waitlist);
    }
}
