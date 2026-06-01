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

    @ParameterizedTest
    @MethodSource("normalizeCourseCodeCases")
    void normalizeCourseCodeUppercasesDepartmentAndPreservesValidNumberAndSection(
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

    private static Stream<Arguments> normalizeCourseCodeCases() {
        return Stream.of(
                Arguments.of("CSC 400", "CSC 400"),
                Arguments.of("csc 400", "CSC 400"),
                Arguments.of("  math 335-003  ", "MATH 335-003"),
                Arguments.of("CS 110-02", "CS 110-02"),
                Arguments.of("ENGL 101", "ENGL 101"),
                Arguments.of("BIO 101-1", "BIO 101-1")
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
                Arguments.of("CSC 400-"),
                Arguments.of("CSC 400-A01"),
                Arguments.of("CSC 400-001-002")
        );
    }

    private static String callNormalizeCourseCode(String courseCode) throws Exception {
        try {
            Method method = Course.class.getDeclaredMethod("normalizeCourseCode", String.class);
            method.setAccessible(true);
            Course course = new Course();

            return (String) method.invoke(course, courseCode);
        } catch (ReflectiveOperationException exception) {
            if(exception.getCause() instanceof RuntimeException runtimeException)
                throw runtimeException;

            throw exception;
        }
    }
}
