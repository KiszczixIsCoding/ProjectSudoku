package pl.comp;

import java.util.ListResourceBundle;

public class InfoListBundleEng extends ListResourceBundle {
    private Object[][] contents = {
            { "1", "Component Programming"},
            { "2", "session 4"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
