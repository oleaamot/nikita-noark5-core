package nikita.util;

import javax.validation.constraints.NotNull;

public class ModelNames {

    private String englishNameDatabase;
    private String englishNameObject;

    public ModelNames(@NotNull String englishNameDatabase,
                      @NotNull String englishNameObject) {
        this.englishNameDatabase = englishNameDatabase;
        this.englishNameObject = englishNameObject;
    }

    public String getEnglishNameDatabase() {
        return englishNameDatabase;
    }

    public void setEnglishNameDatabase(String englishNameDatabase) {
        this.englishNameDatabase = englishNameDatabase;
    }

    public String getEnglishNameObject() {
        return englishNameObject;
    }

    public void setEnglishNameObject(String englishNameObject) {
        this.englishNameObject = englishNameObject;
    }
}
