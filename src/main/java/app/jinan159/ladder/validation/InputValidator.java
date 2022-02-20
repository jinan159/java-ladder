package app.jinan159.ladder.validation;

import app.jinan159.ladder.config.GameConfig;

import java.util.Set;

public class InputValidator {

    private final String ALERT_VALIDATION_FAILED = "(주의) 입력하신 항목을 다시한번 확인해 주세요.";

    private final GameConfig config;

    private InputValidator(GameConfig config) {
        this.config = config;
    }

    public static InputValidator createWithConfig(GameConfig config) {
        return new InputValidator(config);
    }

    public void validateIsPositive(int input) throws IllegalArgumentException {
        if (input <= 0) {
            throw new IllegalArgumentException(ALERT_VALIDATION_FAILED);
        }
    }

    public void validateAllNamesLength(String[] names) throws IllegalArgumentException {
        if (names == null) throw new IllegalArgumentException(ALERT_VALIDATION_FAILED);

        for (String name : names) {
            validateNameLength(name);
        }
    }

    public void validateNamesCount(String[] names, int requiredLength) throws IllegalArgumentException {
        if (names == null || names.length != requiredLength) throw new IllegalArgumentException(ALERT_VALIDATION_FAILED);
    }

    public void validateNameDuplicated(String[] names) throws IllegalArgumentException {
        Set.of(names);
    }

    private void validateNameLength(String name) throws IllegalArgumentException {
        if (name.length() > config.getNameLength()) {
            throw new IllegalArgumentException(ALERT_VALIDATION_FAILED);
        }
    }
}
