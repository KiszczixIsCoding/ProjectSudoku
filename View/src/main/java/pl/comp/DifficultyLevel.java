package pl.comp;

public class DifficultyLevel {
    private int fieldsToDelete;
    private String levelName;

    public DifficultyLevel(String levelName) {
        this.levelName = levelName;
    }

    public void setFieldsToDelete(int fieldsToDelete) {
        this.fieldsToDelete = fieldsToDelete;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getFieldsToDelete() {
        return fieldsToDelete;
    }

    public String getLevelName() {
        return levelName;
    }
}
