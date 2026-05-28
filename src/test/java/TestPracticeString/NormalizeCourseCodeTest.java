package TestPracticeString;

import com.CoreJava.PracticeString.NormalizeCourseCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class NormalizeCourseCodeTest {

    @ParameterizedTest
    @MethodSource("normalizeCourseCodeCases")
    void normalizeCourseCodeReturnsExpectedValue(String input, String expected) {
        assertEquals(expected, assertTimeoutPreemptively(
                Duration.ofMillis(100),
                () -> NormalizeCourseCode.normalizeCourseCode(input)
        ));
    }

    private static Stream<Arguments> normalizeCourseCodeCases() {
        return Stream.of(
                Arguments.of(" cs 110 ", "CS-110"),
                Arguments.of("CS-110", "CS-110"),
                Arguments.of("cSc / 210", "CSC-210"),
                Arguments.of(" math:151 ", "MATH-151"),
                Arguments.of("ENG101", "ENG-101"),
                Arguments.of("   ", "INVALID"),
                Arguments.of("CS", "INVALID"),
                Arguments.of("123CS", "INVALID"),
                Arguments.of("CS-abc", "INVALID"),
                Arguments.of("CS--110", "INVALID"),
                Arguments.of("CS/:110", "INVALID"),
                Arguments.of("CS - / 110", "INVALID"),
                Arguments.of("CS : - 110", "INVALID"),
                Arguments.of("CS   -   - 110", "INVALID"),
                Arguments.of("CS - 110", "CS-110"),
                Arguments.of("CS   -   110", "CS-110"),
                Arguments.of("MATH : 151", "MATH-151"),
                Arguments.of("CSC / 210", "CSC-210"),
                Arguments.of("cs 110", "CS-110"),
                Arguments.of("math   151", "MATH-151"),
                Arguments.of("C S 110", "INVALID"),
                Arguments.of("CS 1 10", "INVALID")
        );
    }
}
