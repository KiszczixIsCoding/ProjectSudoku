package pl.comp;

import java.util.Random;

public class Expert extends DifficultyLevel {
    public Expert(String levelName) {
        super(levelName);
        Random random = new Random();
        setFieldsToDelete(random.nextInt(6) + 60);
    }
}
