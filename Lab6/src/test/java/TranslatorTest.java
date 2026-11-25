import lab6.Translator;
import lab6.Translator.AddStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TranslatorTest {
    private Translator translator;
    @BeforeEach
    void setUp() {
        translator = new Translator();
        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("java", "джава");
    }
    @Test
    @DisplayName("Переклад окремих слів зі словника")
    void testTranslateSingleWords() {
        assertEquals("привіт", translator.translatePhrase("hello"));
        assertEquals("світ", translator.translatePhrase("world"));
        assertEquals("джава", translator.translatePhrase("java"));
    }
    @Test
    @DisplayName("Нечутливість до регістру")
    void testCaseInsensitivityAndCapitalization() {
        assertEquals("привіт", translator.translatePhrase("hello"));
        assertEquals("Привіт", translator.translatePhrase("Hello"));
        assertEquals("Привіт", translator.translatePhrase("HELLO"));
        assertEquals("Джава", translator.translatePhrase("Java"));
    }
    @Test
    @DisplayName("Переклад фрази зі збереженням розділових знаків")
    void testTranslatePhraseWithPunctuation() {
        assertEquals("Привіт, світ! Джава.", translator.translatePhrase("Hello, world! Java."));
        assertEquals("(Привіт) - \"світ\"?!", translator.translatePhrase("(Hello) - \"world\"?!"));
    }
    @Test
    @DisplayName("Невідомі слова залишаються без змін")
    void testUnknownWordsRemainUnchanged() {
        assertEquals("Привіт unknown світ", translator.translatePhrase("Hello unknown world"));
    }
    @Test
    @DisplayName("Перевірка статусів додавання: ADDED, DUPLICATE та UPDATED")
    void testAddWordStatuses() {
        Translator localTranslator = new Translator();
        assertEquals(AddStatus.ADDED, localTranslator.addWord("hello", "привіт"));
        assertEquals(AddStatus.DUPLICATE, localTranslator.addWord("  HELLO  ", "привіт"));
        assertEquals(AddStatus.UPDATED, localTranslator.addWord("hello", "Привіт"));
    }
    @Test
    @DisplayName("Перевірка помилкового введення")
    void testInvalidWordsThrowException() {
        assertThrows(IllegalArgumentException.class, () -> translator.addWord(null, "привіт"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello", null));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("   ", "привіт"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello", "   "));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("Hello, world!", "Привіт, світ!"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello1", "привіт"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello", "привіт!"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello", "hello"));
    }
    @Test
    @DisplayName("Переклад порожньої фрази, null та нормалізація пробілів")
    void testEmptyBlankAndExtraSpacesPhrase() {
        assertEquals("", translator.translatePhrase(null));
        assertEquals("", translator.translatePhrase(""));
        assertEquals("", translator.translatePhrase("    "));
        assertEquals("Привіт світ джава", translator.translatePhrase("   Hello     world    java   "));
    }
}