package TestSmallEnrollmentSystem;

import com.CoreJava.smallEnrollmentSystem.Course;
import com.CoreJava.smallEnrollmentSystem.Section;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    private static Course createCourse() {
        return new Course("CSC 400", "Introduction to Java", 3);
    }
}
