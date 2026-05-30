package TestSmallEnrollmentSystem;

import com.CoreJava.smallEnrollmentSystem.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentTest {

    @Test
    void gettersReturnNormalizedConstructorValues() {
        Student student = new Student("  s001  ", "  jOhN   dOE  ", "  STUDENT@EXAMPLE.COM  ");

        assertEquals("S001", student.getStudentId());
        assertEquals("John Doe", student.getName());
        assertEquals("student@example.com", student.getEmail());
    }

    @Test
    void equalsReturnsTrueForStudentsWithSameNormalizedStudentId() {
        Student firstStudent = new Student("  s001  ", "John Doe", "john@example.com");
        Student secondStudent = new Student("S001", "Jane Smith", "jane@example.com");

        assertTrue(firstStudent.equals(secondStudent));
        assertEquals(firstStudent, secondStudent);
        assertEquals(firstStudent.hashCode(), secondStudent.hashCode());
    }

    @Test
    void equalsReturnsFalseForStudentsWithDifferentStudentIds() {
        Student firstStudent = new Student("S001", "John Doe", "john@example.com");
        Student secondStudent = new Student("S002", "John Doe", "john@example.com");

        assertFalse(firstStudent.equals(secondStudent));
    }

    @Test
    void equalsReturnsFalseForNullAndDifferentObjectTypes() {
        Student student = new Student("S001", "John Doe", "john@example.com");

        assertFalse(student.equals(null));
        assertFalse(student.equals("S001"));
    }

    @Test
    void equalsReturnsTrueForSameObjectReference() {
        Student student = new Student("S001", "John Doe", "john@example.com");

        assertTrue(student.equals(student));
    }

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

    @ParameterizedTest
    @MethodSource("normalizeEmailCases")
    void normalizeEmailLowercasesAndStripsValidEmailAddresses(String input, String expected)
            throws Exception {
        assertEquals(expected, callNormalizeEmail(input));
    }

    @ParameterizedTest
    @MethodSource("invalidNormalizeEmailCases")
    void normalizeEmailRejectsInvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> callNormalizeEmail(input));
    }

    @Test
    void normalizeEmailRejectsNullInput() {
        assertThrows(NullPointerException.class, () -> callNormalizeEmail(null));
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

    private static Stream<Arguments> normalizeEmailCases() {
        return Stream.of(
                Arguments.of("student@example.com", "student@example.com"),
                Arguments.of("STUDENT@EXAMPLE.COM", "student@example.com"),
                Arguments.of("  student@example.com  ", "student@example.com"),
                Arguments.of("\tstudent@example.com\n", "student@example.com"),
                Arguments.of("first.last@example.com", "first.last@example.com"),
                Arguments.of("first_last@example.com", "first_last@example.com"),
                Arguments.of("first-last@example.com", "first-last@example.com"),
                Arguments.of("first+tag@example.com", "first+tag@example.com"),
                Arguments.of("a@b.co", "a@b.co"),
                Arguments.of("student@mail.example.co.uk", "student@mail.example.co.uk"),
                Arguments.of("student@example-domain.com", "student@example-domain.com"),
                Arguments.of("student@example123.com", "student@example123.com")
        );
    }

    private static Stream<Arguments> invalidNormalizeEmailCases() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("studentexample.com"),
                Arguments.of("@example.com"),
                Arguments.of("student@"),
                Arguments.of("student@example"),
                Arguments.of("student@example.c"),
                Arguments.of("student@example.123"),
                Arguments.of("student@@example.com"),
                Arguments.of("student @example.com"),
                Arguments.of("student@example .com"),
                Arguments.of("student@exa_mple.com"),
                Arguments.of(".student@example.com"),
                Arguments.of("student.@example.com"),
                Arguments.of("student..name@example.com"),
                Arguments.of("student__name@example.com"),
                Arguments.of("student++tag@example.com"),
                Arguments.of("student--name@example.com"),
                Arguments.of("student@-example.com"),
                Arguments.of("student@example-.com"),
                Arguments.of("student@example..com"),
                Arguments.of("student@.example.com"),
                Arguments.of("student@example.com."),
                Arguments.of("student#tag@example.com"),
                Arguments.of("student@example,com"),
                Arguments.of("student@example.com extra")
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

    private static String callNormalizeEmail(String email) throws Exception {
        try {
            Method method = Student.class.getDeclaredMethod("normalizeEmail", String.class);
            method.setAccessible(true);
            Student student = new Student("s001", "placeholder", "student@example.com");

            return (String) method.invoke(student, email);
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
