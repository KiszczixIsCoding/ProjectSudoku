package pl.comp;

import java.util.Random;

public class Hard extends DifficultyLevel {
    public Hard(String levelName) {
        super(levelName);
        Random random = new Random();
        setFieldsToDelete(random.nextInt(6) + 55);
    }
}
