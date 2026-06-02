package TestSmallEnrollmentSystem;

import com.CoreJava.smallEnrollmentSystem.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseTest {

    @Test
    void constructorNormalizesCourseCode() {
        Course course = new Course("  csc 400  ", "Introduction to Java", 3);

        assertEquals("CSC 400", course.getCourseCode());
    }

    @Test
    void constructorRejectsHyphenatedCourseCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Course("CSC 400-001", "Introduction to Java", 3)
        );
    }

    @ParameterizedTest
    @MethodSource("normalizeCourseCodeCases")
    void normalizeCourseCodeUppercasesDepartmentAndPreservesValidNumber(
            String input, String expected
    ) throws Exception {
        assertEquals(expected, callNormalizeCourseCode(input));
    }

    @ParameterizedTest
    @MethodSource("invalidNormalizeCourseCodeCases")
    void normalizeCourseCodeRejectsInvalidCourseCodes(String input) {
        assertThrows(IllegalArgumentException.class, () -> callNormalizeCourseCode(input));
    }

    @Test
    void normalizeCourseCodeRejectsNullInput() {
        assertThrows(NullPointerException.class, () -> callNormalizeCourseCode(null));
    }

    @ParameterizedTest
    @MethodSource("normalizeTitleCases")
    void normalizeTitleNormalizesWhitespaceAndPreservesCallerProvidedText(
            String input, String expected
    ) throws Exception {
        assertEquals(expected, callNormalizeTitle(input));
    }

    @ParameterizedTest
    @MethodSource("blankNormalizeTitleCases")
    void normalizeTitleRejectsBlankTitles(String input) {
        assertThrows(IllegalArgumentException.class, () -> callNormalizeTitle(input));
    }

    @Test
    void normalizeTitleRejectsNullInput() {
        assertThrows(NullPointerException.class, () -> callNormalizeTitle(null));
    }

    private static Stream<Arguments> normalizeCourseCodeCases() {
        return Stream.of(
                Arguments.of("CSC 400", "CSC 400"),
                Arguments.of("csc 400", "CSC 400"),
                Arguments.of("  math 335  ", "MATH 335"),
                Arguments.of("CS 110", "CS 110"),
                Arguments.of("ENGL 101", "ENGL 101"),
                Arguments.of("BIO 101", "BIO 101")
        );
    }

    private static Stream<Arguments> invalidNormalizeCourseCodeCases() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("CSC"),
                Arguments.of("CSC400"),
                Arguments.of("CSC  400"),
                Arguments.of("CSC\t400"),
                Arguments.of("C 400"),
                Arguments.of("TOOLONG 400"),
                Arguments.of("CS1 400"),
                Arguments.of("CS_ 400"),
                Arguments.of("CSC ABC"),
                Arguments.of("CSC 400A"),
                Arguments.of("CSC -400"),
                Arguments.of("MATH 335-003"),
                Arguments.of("CS 110-02"),
                Arguments.of("BIO 101-1"),
                Arguments.of("CSC 400-"),
                Arguments.of("CSC 400-A01"),
                Arguments.of("CSC 400-001-002")
        );
    }

    private static Stream<Arguments> normalizeTitleCases() {
        return Stream.of(
                Arguments.of("Introduction to Java", "Introduction to Java"),
                Arguments.of("  Introduction to Java  ", "Introduction to Java"),
                Arguments.of("\tData Structures and Algorithms\n", "Data Structures and Algorithms"),
                Arguments.of("CSC 110: Programming Fundamentals", "CSC 110: Programming Fundamentals"),
                Arguments.of("intro to JAVA", "intro to JAVA"),
                Arguments.of("Intro  to  Java", "Intro to Java"),
                Arguments.of("  introduction      to      AI    ", "introduction to AI")
        );
    }

    private static Stream<Arguments> blankNormalizeTitleCases() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("\t\n")
        );
    }

    private static String callNormalizeCourseCode(String courseCode) throws Exception {
        try {
            Method method = Course.class.getDeclaredMethod("normalizeCourseCode", String.class);
            method.setAccessible(true);
            Course course = createValidCourse();

            return (String) method.invoke(course, courseCode);
        } catch (ReflectiveOperationException exception) {
            if(exception.getCause() instanceof RuntimeException runtimeException)
                throw runtimeException;

            throw exception;
        }
    }

    private static String callNormalizeTitle(String title) throws Exception {
        try {
            Method method = Course.class.getDeclaredMethod("normalizeTitle", String.class);
            method.setAccessible(true);
            Course course = createValidCourse();

            return (String) method.invoke(course, title);
        } catch (ReflectiveOperationException exception) {
            if(exception.getCause() instanceof RuntimeException runtimeException)
                throw runtimeException;

            throw exception;
        }
    }

    private static Course createValidCourse() {
        return new Course("CSC 400", "Introduction to Java", 3);
    }
}
