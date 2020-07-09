package pl.comp;

import java.util.Random;

public class Easy extends DifficultyLevel {

    public Easy(String levelName) {
          super(levelName);
          Random random = new Random();
          setFieldsToDelete(random.nextInt(6) + 45);
    }
}
