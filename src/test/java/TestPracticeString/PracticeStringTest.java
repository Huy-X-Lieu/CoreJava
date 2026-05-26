package TestPracticeString;

import com.CoreJava.PracticeString.PracticeString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PracticeStringTest {

    @ParameterizedTest
    @MethodSource("slugifyAsciiCases")
    void slugifyAsciiConvertsAsciiTextToSlug(String input, String expected) {
        assertEquals(expected, PracticeString.slugifyAscii(input));
    }

    @ParameterizedTest
    @MethodSource("toCsvRowCases")
    void toCsvRowFormatsFieldsAsCsvRow(String[] fields, String expected) {
        assertEquals(expected, PracticeString.toCsvRow(fields));
    }

    @ParameterizedTest
    @MethodSource("doesFieldNeedDoubleQuotesCases")
    void doesFieldNeedDoubleQuotesReturnsExpectedBoolean(String field, boolean expected) throws ReflectiveOperationException {
        assertEquals(expected, callDoesFieldNeedDoubleQuotes(field));
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

    @Test
    void toCsvRowRejectsNullArray() {
        assertThrows(NullPointerException.class, () -> PracticeString.toCsvRow(null));
    }

    private static Stream<Arguments> toCsvRowCases() {
        return Stream.of(
                Arguments.of(new String[] {"a", "b", "c"}, "a,b,c"),
                Arguments.of(new String[] {"a,b", "c"}, "\"a,b\",c"),
                Arguments.of(new String[] {"He said \"hi\""}, "\"He said \"\"hi\"\"\""),
                Arguments.of(new String[] {"", null, "x"}, ",,x"),
                Arguments.of(new String[] {"line1\nline2", "ok"}, "\"line1\nline2\",ok"),
                Arguments.of(new String[] {"line1\rline2", "ok"}, "\"line1\rline2\",ok"),
                Arguments.of(new String[] {"tab\tseparated", "ok"}, "\"tab\tseparated\",ok"),
                Arguments.of(new String[] {" leading", "trailing ", " both "}, "\" leading\",\"trailing \",\" both \""),
                Arguments.of(new String[] {" "}, "\" \""),
                Arguments.of(new String[] {"\t"}, "\"\t\""),
                Arguments.of(new String[] {"a\"b,c", "x"}, "\"a\"\"b,c\",x"),
                Arguments.of(new String[] {"first", "", "last"}, "first,,last"),
                Arguments.of(new String[] {null, null}, ","),
                Arguments.of(new String[] {"a", null}, "a,"),
                Arguments.of(new String[] {}, "")
        );
    }

    private static Stream<Arguments> doesFieldNeedDoubleQuotesCases() {
        return Stream.of(
                Arguments.of("abc", false),
                Arguments.of("a,b", true),
                Arguments.of("He said \"hi\"", true),
                Arguments.of("line1\nline2", true),
                Arguments.of("line1\rline2", true),
                Arguments.of("tab\tseparated", true),
                Arguments.of(" leading", true),
                Arguments.of("trailing ", true),
                Arguments.of(",", true),
                Arguments.of("\"", true),
                Arguments.of(" ", true),
                Arguments.of("\t", true)
        );
    }

    private static boolean callDoesFieldNeedDoubleQuotes(String field) throws ReflectiveOperationException {
        Method method = PracticeString.class.getDeclaredMethod("doesFieldNeedDoubleQuotes", String.class);
        method.setAccessible(true);
        return (boolean) method.invoke(null, field);
    }
}
