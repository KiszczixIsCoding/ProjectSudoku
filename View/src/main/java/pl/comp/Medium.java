package pl.comp;

import java.util.Random;

public class Medium extends DifficultyLevel {
    public Medium(String levelName) {
        super(levelName);
        Random random = new Random();
        setFieldsToDelete(random.nextInt(6) + 50);
    }
}
