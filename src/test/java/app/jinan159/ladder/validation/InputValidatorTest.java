package app.jinan159.ladder.validation;

import app.jinan159.ladder.config.GameConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class InputValidatorTest {

    @Nested
    @DisplayName("validateIsPositive 메소드는")
    class ValidateIsPositiveTest{
        @Test
        @DisplayName("음수를 넣으면 IllegalArgumentException 예외가 발생한다.")
        void negativeNumberWillBeFail() {
            // given
            InputValidator validator = InputValidator.createWithConfig(null);

            // then
            assertThrows(IllegalArgumentException.class, () -> validator.validateIsPositive(-1));
        }

        @Test
        @DisplayName("0을 넣으면 IllegalArgumentException 예외가 발생한다.")
        void zeroWillBeFail() {
            // given
            InputValidator validator = InputValidator.createWithConfig(null);

            // then
            assertThrows(IllegalArgumentException.class, () -> validator.validateIsPositive(0));
        }

        @Test
        @DisplayName("양수를 넣으면 아무일도 일어나지 않는다.")
        void positiveNumberWillBeSuccess() {
            // given
            InputValidator validator = InputValidator.createWithConfig(null);

            // then
            try {
                validator.validateIsPositive(1);
            } catch (Exception e) {
                fail();
            }
        }
    }

    @Nested
    @DisplayName("validateAllNamesLength 메소드는")
    class ValidateAllNamesLengthTest{
        @Test
        @DisplayName("names 가 빈 배열이면 아무일도 일어나지 않는다.")
        void emptyArraySuccess() {
            // given
            GameConfig config = GameConfig.createWithNameLength(5);
            InputValidator validator = InputValidator.createWithConfig(config);
            String[] names = {};

            // then
            try {
                validator.validateAllNamesLength(names);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        @DisplayName("names 가 null 이면 IllegalArgumentException 예외가 발생한다.")
        void validateSuccess() {
            // given
            GameConfig config = GameConfig.createWithNameLength(5);
            InputValidator validator = InputValidator.createWithConfig(config);

            // then
            assertThrows(IllegalArgumentException.class, () -> validator.validateAllNamesLength(null));
        }

        @Test
        @DisplayName("이름 길이 설정이 5이고, 배열의 모든 요소가 5 이하면 성공한다.")
        void arrayValidateSuccess() {
            // given
            GameConfig config = GameConfig.createWithNameLength(5);
            InputValidator validator = InputValidator.createWithConfig(config);
            String[] names = {"", "a", "abcde"};

            // then
            try {
                validator.validateAllNamesLength(names);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        @DisplayName("이름 길이 설정이 5이고, 배열의 모든 요소중 하나만 5 이상이어도 IllegalArgumentException 예외가 발생한다.")
        void arrayValidateFailed() {
            // given
            GameConfig config = GameConfig.createWithNameLength(5);
            InputValidator validator = InputValidator.createWithConfig(config);
            String[] names = {"", "a", "abcde", "123456"};

            // then
            assertThrows(IllegalArgumentException.class, () -> validator.validateAllNamesLength(names));
        }
    }

    @Nested
    @DisplayName("validateNamesCount 메소드는")
    class ValidateNamesCountTest{
        @Test
        @DisplayName("names 와 requiredLength 가 같으면 아무일도 일어나지 않는다.")
        void lengthIsSame() {
            // given
            InputValidator validator = InputValidator.createWithConfig(null);
            String[] names = {"hello", "world"};
            int requiredLength = names.length;

            // then
            try {
                validator.validateNamesCount(names, requiredLength);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        @DisplayName("names 와 requiredLength 가 다르면 IllegalArgumentException 예외가 발생한다.")
        void lengthIsDifferent() {
            // given
            InputValidator validator = InputValidator.createWithConfig(null);
            String[] names = {"hello", "world"};
            int requiredLength = names.length - 1;

            // then
            assertThrows(IllegalArgumentException.class, () -> validator.validateNamesCount(names, requiredLength));

        }

        @Test
        @DisplayName("names 가 null 이면 IllegalArgumentException 예외가 발생한다.")
        void namesIsNull() {
            // given
            InputValidator validator = InputValidator.createWithConfig(null);
            String[] names = null;
            int requiredLength = 0;

            // then
            assertThrows(IllegalArgumentException.class, () -> validator.validateNamesCount(names, requiredLength));
        }
    }

    @Nested
    @DisplayName("validateNameDuplicated 메소드는")
    class ValidateNameDuplicatedTest{
        @Test
        @DisplayName("중복된 이름이 있으면 IllegalArgumentException 예외가 발생한다.")
        void nameDuplicated() {
            // given
            InputValidator validator = InputValidator.createWithConfig(null);
            String[] names = {"hello", "world", "hello"};

            // then
            assertThrows(IllegalArgumentException.class, () -> validator.validateNameDuplicated(names));
        }

        @Test
        @DisplayName("중복된 이름이 없으면 아무일도 일어나지 않는다.")
        void nameIsNotDuplicated() {
            // given
            InputValidator validator = InputValidator.createWithConfig(null);
            String[] names = {"hello", "world", "jay"};

            // then
            try {
                validator.validateNameDuplicated(names);
            } catch (Exception e) {
                fail();
            }
        }
    }
}