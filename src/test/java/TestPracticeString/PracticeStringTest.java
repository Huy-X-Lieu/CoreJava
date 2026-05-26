package TestPracticeString;

import com.CoreJava.PracticeString.PracticeString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PracticeStringTest {

    @ParameterizedTest
    @MethodSource("slugifyAsciiCases")
    void slugifyAsciiConvertsAsciiTextToSlug(String input, String expected) {
        assertEquals(expected, PracticeString.slugifyAscii(input));
    }

    private static Stream<Arguments> slugifyAsciiCases() {
        return Stream.of(
                Arguments.of("Hello World", "hello-world"),
                Arguments.of("  Hello   World  ", "hello-world"),
                Arguments.of("Java_StringBuilder", "java-stringbuilder"),
                Arguments.of("Already--slugified", "already-slugified"),
                Arguments.of("123 ABC xyz", "123-abc-xyz"),
                Arguments.of("Hello, World!", "hello-world"),
                Arguments.of("hello.world/test", "hello-world-test"),
                Arguments.of("-Hello-", "hello"),
                Arguments.of("hello---", "hello"),
                Arguments.of("A1 B2 C3", "a1-b2-c3"),
                Arguments.of("abc123", "abc123"),
                Arguments.of("123", "123"),
                Arguments.of("  123  ", "123"),
                Arguments.of("a--1__B", "a-1-b"),
                Arguments.of("UPPER lower MiXeD", "upper-lower-mixed"),
                Arguments.of(" tabs\tand\nnewlines ", "tabs-and-newlines"),
                Arguments.of("!!!", ""),
                Arguments.of("", "")
        );
    }

    @Test
    void slugifyAsciiRejectsNullInput() {
        assertThrows(NullPointerException.class, () -> PracticeString.slugifyAscii(null));
    }
}
