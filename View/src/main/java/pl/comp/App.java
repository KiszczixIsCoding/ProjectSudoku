package pl.comp;

import java.io.Closeable;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.comp.model.exceptions.FxmlLoadException;
import pl.comp.model.exceptions.FxmlLoadRuntimeException;


public class App extends Application implements Closeable {

    private static Scene scene;
    private static Stage stage;
    private static StartController startController;
    private static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) {
        Logger logger = LogManager.getLogger(App.class);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.language_version");
        App.stage = stage;

        try {
            scene = new Scene(loadFxml("startController"));
        } catch (IllegalStateException | IOException e) {
            logger.fatal(resourceBundle.getString("exception.fxml")
                    + " " + "startController.fxml");
            throw new FxmlLoadRuntimeException(resourceBundle.getString("exception.fxml")
                    + " " + "startController.fxml", e);
        }
        startController = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws FxmlLoadException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.language_version");
        try {
            if (fxmlLoader.getController() instanceof StartController) {
                App.startController = fxmlLoader.getController();
            }
            scene.setRoot(loadFxml(fxml));
        } catch (IllegalStateException | IOException e) {
            throw new FxmlLoadException(resourceBundle
                    .getString("exception.fxml") + " " + fxml + ".fxml", e);
        }
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    public static Scene getScene() {
        return scene;
    }

    private static Parent loadFxml(String fxml) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.language_version");
        fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setResources(resourceBundle);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static StartController getStartController() {
        return startController;
    }

    public static void setLanguageVersion(String version) {
        Locale.setDefault(new Locale(version));
    }

    @Override
    public void close() {

    }
}