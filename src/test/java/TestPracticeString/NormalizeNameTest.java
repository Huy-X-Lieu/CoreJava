package TestPracticeString;

import com.CoreJava.PracticeString.NormalizeName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class NormalizeNameTest {

    @ParameterizedTest
    @MethodSource("normalizeNameCases")
    void normalizeNameFormatsNameConsistently(String input, String expected) {
        assertEquals(expected, assertTimeoutPreemptively(
                Duration.ofMillis(100),
                () -> NormalizeName.normalizeName(input)
        ));
    }

    @Test
    void normalizeNameRejectsNullInput() {
        assertThrows(NullPointerException.class, () -> NormalizeName.normalizeName(null));
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
                Arguments.of("", ""),
                Arguments.of("   ", "")
        );
    }
}
