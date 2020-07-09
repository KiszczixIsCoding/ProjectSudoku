package pl.comp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuBoardDaoFactory;
import pl.comp.model.exceptions.FxmlLoadException;
import pl.comp.model.exceptions.IllegalNameArgumentException;
import pl.comp.model.exceptions.IncorrectDataSize;
import pl.comp.model.exceptions.NoDataException;
import pl.comp.model.exceptions.NoSuchFileException;
import pl.comp.model.exceptions.UnparsableStringToIntException;

public class StartController implements Initializable {
    @FXML private Button button1; // rozpocznij gre
    @FXML private Button loadButton; // wczytaj gre
    @FXML private Button loadBaseButton; // wczytaj z bazy danych
    @FXML private TextField loadField;
    @FXML private TextField loadBaseField;
    @FXML private TextField nameField; // wpisanie imienia
    @FXML private Spinner spinLanguage; // wybor jezyka
    @FXML private MenuButton menuButton1; // wybor poziomu gry
    @FXML private Label labelSubject;
    @FXML private Label labelSemester;
    private SudokuBoard loadedBoard;
    private List<DifficultyLevel> levelsList =  new ArrayList<>();
    private final Logger logger = LogManager.getLogger(StartController.class);
    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        ObservableList<String> list;
        if (Locale.getDefault().getLanguage().equals("pl")) {
            list = FXCollections.observableArrayList(resourceBundle.getString("language.pl"),
                    resourceBundle.getString("language.ang"));
        } else {
            list = FXCollections.observableArrayList(resourceBundle.getString("language.ang"),
                    resourceBundle.getString("language.pl"));
        }

        spinLanguage.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory(list));
        spinLanguage.valueProperty().addListener((x) -> {
            if (spinLanguage.getValue() == resourceBundle.getString("language.pl")) {
                App.setLanguageVersion("pl");
                try {
                    App.setRoot("StartController");
                } catch (FxmlLoadException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            } else {
                App.setLanguageVersion("eng");
                Locale.setDefault(new Locale("eng"));
                try {
                    App.setRoot("StartController");
                } catch (FxmlLoadException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        levelsList.add(new Easy(resourceBundle.getString("level.easy")));
        levelsList.add(new Medium(resourceBundle.getString("level.medium")));
        levelsList.add(new Hard(resourceBundle.getString("level.hard")));
        levelsList.add(new Expert(resourceBundle.getString("level.expert")));
        menuButton1.getItems().forEach((element) -> {
            element.setText(levelsList.get(menuButton1.getItems().indexOf(element)).getLevelName());
        });

        // zdarzenie dla wyboru poziomu gry z listy
        EventHandler<ActionEvent> evHandler = actionEvent -> {
            Object obj = actionEvent.getSource();
            menuButton1.setText(((MenuItem)obj).getText());
        };

        // przypisanie zdarzenia dla wszystkich poziomow
        for (MenuItem item : menuButton1.getItems()) {
            item.setOnAction(evHandler);
        }

        setFooter();

        // zachowanie dla nacisniecia rozpocznij gre
        EventHandler<MouseEvent> evMouse = mouseEvent -> {
            try {
                setAppRoot("gameController");
            } catch (FxmlLoadException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        };
        button1.setOnMouseClicked(evMouse);

        // zdarzenie dla wczytania gry z pliku(nie trzeba podawać poziomu)
        EventHandler<MouseEvent> mouseEvent = mouseEvent1 -> {
            try {
                setAppRoot2("gameController");
            } catch (FxmlLoadException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        };
        loadButton.setOnMouseClicked(mouseEvent);

        // zdarzenie dla wczytania gry z bazy danych
        EventHandler<MouseEvent> mouseEvt = mouseEvent1 -> {
            try {
                setAppRoot3("gameController");
            } catch (FxmlLoadException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        };
        loadBaseButton.setOnMouseClicked(mouseEvt);
    }

    public void setAppRoot(String className) throws FxmlLoadException {
        if (menuButton1.getText().isEmpty() && nameField.getText().isEmpty()) {
            logger.info(resourceBundle.getString("info.name")
                    + ". " + resourceBundle.getString("info.level"));
            menuButton1.setStyle("-fx-background-color: #f2000d;");
            menuButton1.setOpacity(0.7);
            nameField.setStyle("-fx-background-color: #f2000d;");
            nameField.setOpacity(0.7);
        } else if (nameField.getText().isEmpty()) {
            logger.info(resourceBundle.getString("info.name"));
            nameField.setStyle("-fx-background-color: #f2000d;");
            nameField.setOpacity(0.7);
            menuButton1.setStyle("-fx-background-color: #ffffff;");
            menuButton1.setOpacity(1);
        } else if (menuButton1.getText().isEmpty()) {
            logger.info(resourceBundle.getString("info.level"));
            menuButton1.setStyle("-fx-background-color: #f2000d;");
            menuButton1.setOpacity(0.7);
            nameField.setStyle("-fx-background-color: #ffffff;");
            nameField.setOpacity(1);
        } else {
            App.setRoot(className);
        }
    }

    public void setAppRoot2(String className) throws FxmlLoadException {
        if (nameField.getText().isEmpty() && (loadField.getText().isEmpty())) {
            logger.info(resourceBundle.getString("info.name")
                    + ". " + resourceBundle.getString("info.path"));
            loadField.setStyle("-fx-background-color: #f2000d;");
            loadField.setOpacity(0.7);
            nameField.setStyle("-fx-background-color: #f2000d;");
            nameField.setOpacity(0.7);
        } else if (nameField.getText().isEmpty()) {
            logger.info(resourceBundle.getString("info.name"));
            nameField.setStyle("-fx-background-color: #f2000d;");
            nameField.setOpacity(0.7);
            loadField.setStyle("-fx-background-color: #ffffff;");
            loadField.setOpacity(1);
        } else if (loadField.getText().isEmpty()) {
            logger.info(resourceBundle.getString("info.path"));
            loadField.setStyle("-fx-background-color: #f2000d;");
            loadField.setOpacity(0.7);
            nameField.setStyle("-fx-background-color: #ffffff;");
            nameField.setOpacity(0.7);
        } else {
            SudokuBoardDaoFactory sbDao = new SudokuBoardDaoFactory();
                try {
                    loadedBoard = sbDao.getFileDao(loadField.getText()).read();
                    App.setRoot(className);
                } catch (UnparsableStringToIntException | NoSuchFileException | NoDataException
                        | IncorrectDataSize exception) {
                    loadField.setStyle("-fx-background-color: #f2000d;");
                    loadField.setOpacity(0.7);
                    logger.error(exception.getMessage());
                    exception.printStackTrace();
                }
        }
    }

    public void setAppRoot3(String className) throws FxmlLoadException {
        if (nameField.getText().isEmpty() && (loadBaseField.getText().isEmpty())) {
            logger.info(resourceBundle.getString("info.name")
                    + ". " + resourceBundle.getString("info.path"));
            loadBaseField.setStyle("-fx-background-color: #f2000d;");
            loadBaseField.setOpacity(0.7);
            nameField.setStyle("-fx-background-color: #f2000d;");
            nameField.setOpacity(0.7);
        } else if (nameField.getText().isEmpty()) {
            logger.info(resourceBundle.getString("info.name"));
            nameField.setStyle("-fx-background-color: #f2000d;");
            nameField.setOpacity(0.7);
            loadBaseField.setStyle("-fx-background-color: #ffffff;");
            loadBaseField.setOpacity(1);
        } else if (loadBaseField.getText().isEmpty()) {
            logger.info(resourceBundle.getString("info.path"));
            loadBaseField.setStyle("-fx-background-color: #f2000d;");
            loadBaseField.setOpacity(0.7);
            nameField.setStyle("-fx-background-color: #ffffff;");
            nameField.setOpacity(0.7);
        } else {
            SudokuBoardDaoFactory sbDao = new SudokuBoardDaoFactory();
            try {
                loadedBoard = sbDao.getJpaDao(loadBaseField.getText()).read();
                App.setRoot(className);
            } catch (UnparsableStringToIntException | NoSuchFileException
                    | NoDataException | IncorrectDataSize ex) {
                logger.error(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public MenuButton getMenuButton1() {
        return menuButton1;
    }

    public TextField getNameField() {
        return nameField;
    }

    public List<DifficultyLevel> getLevelsList() {
        return levelsList;
    }

    public SudokuBoard getLoadedBoard() {
        return loadedBoard;
    }

    private void setFooter() {
        if (Locale.getDefault().getLanguage().equals("pl")) {
            labelSubject.setText((String) new InfoListBundle().getObject("1"));
            labelSemester.setText((String) new InfoListBundle().getObject("2"));
        } else {
            labelSubject.setText((String) new InfoListBundleEng().getObject("1"));
            labelSemester.setText((String) new InfoListBundleEng().getObject("2"));
        }
    }
}
