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
    @MethodSource("formatNameCases")
    void formatNameCapitalizesEachWordAndNormalizesSpaces(String input, String expected)
            throws Exception {
        assertEquals(expected, callFormatName(input));
    }

    @ParameterizedTest
    @MethodSource("invalidFormatNameCases")
    void formatNameRejectsNonLetterNonSpaceCharacters(String input) {
        assertThrows(IllegalArgumentException.class, () -> callFormatName(input));
    }

    @Test
    void formatNameAllowsSingleLetterWords() throws Exception {
        assertEquals("A B C", callFormatName("a b c"));
    }

    private static Stream<Arguments> formatNameCases() {
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

    private static Stream<Arguments> invalidFormatNameCases() {
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

    private static String callFormatName(String name) throws Exception {
        try {
            Method method = Student.class.getDeclaredMethod("formatName", String.class);
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
