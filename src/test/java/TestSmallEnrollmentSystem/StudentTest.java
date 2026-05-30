package TestSmallEnrollmentSystem;

import com.CoreJava.smallEnrollmentSystem.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentTest {

    @ParameterizedTest
    @MethodSource("normalizeStudentIDCases")
    void normalizeStudentIDUppercasesAndPreservesLettersAndDigits(String input, String expected)
            throws Exception {
        assertEquals(expected, callNormalizeStudentID(input));
    }

    @ParameterizedTest
    @MethodSource("invalidNormalizeStudentIDCases")
    void normalizeStudentIDRejectsInvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> callNormalizeStudentID(input));
    }

    @Test
    void normalizeStudentIDRejectsNullInput() {
        assertThrows(NullPointerException.class, () -> callNormalizeStudentID(null));
    }

    @ParameterizedTest
    @MethodSource("normalizeNameCases")
    void normalizeNameCapitalizesEachWordAndNormalizesSpaces(String input, String expected)
            throws Exception {
        assertEquals(expected, callNormalizeName(input));
    }

    @ParameterizedTest
    @MethodSource("invalidNormalizeNameCases")
    void normalizeNameRejectsNonLetterNonSpaceCharacters(String input) {
        assertThrows(IllegalArgumentException.class, () -> callNormalizeName(input));
    }

    @Test
    void normalizeNameAllowsSingleLetterWords() throws Exception {
        assertEquals("A B C", callNormalizeName("a b c"));
    }

    private static Stream<Arguments> normalizeStudentIDCases() {
        return Stream.of(
                Arguments.of("S001", "S001"),
                Arguments.of("s001", "S001"),
                Arguments.of("  s001  ", "S001"),
                Arguments.of("abc123", "ABC123"),
                Arguments.of("a1b2c3", "A1B2C3")
        );
    }

    private static Stream<Arguments> invalidNormalizeStudentIDCases() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("S 001"),
                Arguments.of("S-001"),
                Arguments.of("S_001"),
                Arguments.of("S.001"),
                Arguments.of("S/001"),
                Arguments.of("S001!")
        );
    }

    private static Stream<Arguments> normalizeNameCases() {
        return Stream.of(
                Arguments.of("john", "John"),
                Arguments.of("jOhN", "John"),
                Arguments.of("john doe", "John Doe"),
                Arguments.of("  john doe  ", "John Doe"),
                Arguments.of("john   doe", "John Doe"),
                Arguments.of("  jOhN   dOE  ", "John Doe"),
                Arguments.of("ALICE BOB CAROL", "Alice Bob Carol"),
                Arguments.of("a", "A"),
                Arguments.of("  a  b  ", "A B"),
                Arguments.of("josé álvarez", "José Álvarez"),
                Arguments.of("ÉLODIE BRONTË", "Élodie Brontë"),
                Arguments.of("john\u00A0doe", "John Doe"),
                Arguments.of("john\u2003doe", "John Doe")
        );
    }

    private static Stream<Arguments> invalidNormalizeNameCases() {
        return Stream.of(
                Arguments.of("john2"),
                Arguments.of("mary-jane"),
                Arguments.of("o'connor"),
                Arguments.of("john.doe"),
                Arguments.of("john_doe"),
                Arguments.of("john, doe"),
                Arguments.of("john/doe"),
                Arguments.of("john\tdoe"),
                Arguments.of("john\ndoe"),
                Arguments.of("john\rdoe"),
                Arguments.of("john\u200Bdoe")
        );
    }

    private static String callNormalizeStudentID(String studentID) throws Exception {
        try {
            Method method = Student.class.getDeclaredMethod("normalizeStudentID", String.class);
            method.setAccessible(true);
            Student student = new Student("s001", "placeholder", "student@example.com");

            return (String) method.invoke(student, studentID);
        } catch (ReflectiveOperationException exception) {
            if (exception.getCause() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw exception;
        }
    }

    private static String callNormalizeName(String name) throws Exception {
        try {
            Method method = Student.class.getDeclaredMethod("normalizeName", String.class);
            method.setAccessible(true);
            Student student = new Student("s001", "placeholder", "student@example.com");

            return (String) method.invoke(student, name);
        } catch (ReflectiveOperationException exception) {
            if (exception.getCause() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw exception;
        }
    }
}
