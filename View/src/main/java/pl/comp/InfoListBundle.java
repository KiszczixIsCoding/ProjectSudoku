package pl.comp;

import java.util.ListResourceBundle;

public class InfoListBundle extends ListResourceBundle {
    private Object[][] contents = {
            { "1", "Programowanie komponentowe"},
            { "2", "semestr 4"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
