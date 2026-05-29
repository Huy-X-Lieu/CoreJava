package TestPracticeString;

import com.CoreJava.PracticeString.NormalizeTitleToUrlSlug;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class NormalizeTitleToUrlSlugTest {

    @ParameterizedTest
    @MethodSource("normalizeTitleToUrlSlugCases")
    void normalizeTitleToUrlSlugReturnsExpectedSlug(String title, int maxLength, String expected) {
        assertEquals(expected, assertTimeoutPreemptively(
                Duration.ofMillis(100),
                () -> NormalizeTitleToUrlSlug.normalizeTitleToUrlSlug(title, maxLength)
        ));
    }

    private static Stream<Arguments> normalizeTitleToUrlSlugCases() {
        return Stream.of(
                Arguments.of((String) null, 10, "invalid"),
                Arguments.of("", 10, "invalid"),
                Arguments.of("   \t\n   ", 10, "invalid"),
                Arguments.of("Java", 0, "invalid"),
                Arguments.of("Java", -1, "invalid"),
                Arguments.of("12345", 10, "invalid"),
                Arguments.of("  Java StringBuilder Performance!!! ", 50, "java-stringbuilder-performance"),
                Arguments.of("Java---StringBuilder___Performance", 50, "java-stringbuilder-performance"),
                Arguments.of("  C++ vs Java: String & StringBuilder  ", 30, "c-vs-java-string-stringbuilder"),
                Arguments.of("!!!", 10, "invalid"),
                Arguments.of("Java / / / Strings", 50, "java-strings"),
                Arguments.of("Java StringBuilder Performance", 12, "java-stringb"),
                Arguments.of("Java", 50, "java"),
                Arguments.of("JAVA", 50, "java"),
                Arguments.of("jAvA", 50, "java"),
                Arguments.of("A", 1, "a"),
                Arguments.of("AB", 1, "a"),
                Arguments.of("AB", 2, "ab"),
                Arguments.of("---Java---", 50, "java"),
                Arguments.of(" / Java / String / ", 50, "java-string"),
                Arguments.of("Java     String", 50, "java-string"),
                Arguments.of("Java\nString\tBuilder", 50, "java-string-builder"),
                Arguments.of("Java...String???Builder", 50, "java-string-builder"),
                Arguments.of("J a v a", 50, "j-a-v-a"),
                Arguments.of("C# and C++", 50, "c-and-c"),
                Arguments.of("123 Java 456", 50, "java"),
                Arguments.of("Hello World", 5, "hello"),
                Arguments.of("Hello World", 6, "hello"),
                Arguments.of("Hello World", 7, "hello-w"),
                Arguments.of("A B", 2, "a"),
                Arguments.of("A B", 3, "a-b"),
                Arguments.of("AB CD", 3, "ab")
        );
    }
}
