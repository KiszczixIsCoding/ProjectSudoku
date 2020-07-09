package pl.comp;

import static pl.comp.App.getStartController;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuBoardDaoFactory;
import pl.comp.model.SudokuSolver;
import pl.comp.model.exceptions.FxmlLoadException;
import pl.comp.model.exceptions.IllegalNameArgumentException;
import pl.comp.model.exceptions.NoSuchFileException;
import pl.comp.model.exceptions.SaveInDatabaseException;


public class GameController implements Initializable {
    @FXML private Label label1; // wprowadzone imie
    @FXML private Label label2; // wybrany poziom trudnosci
    @FXML private Label labelLevel;
    @FXML private Button button1; // nowa gra
    @FXML private Button button2; // reset gry
    @FXML private Button button3; // sprawdznie gry
    @FXML private Button saveButton;
    @FXML private Button saveBaseButton;
    @FXML private TextField saveField;
    @FXML private TextField saveBaseField;
    @FXML private GridPane mainGridPane; // pole do gry
    @FXML private List<GridPane> squaresList =  Arrays.asList(new GridPane[9]);
    private SudokuBoard sudokuBoard = new SudokuBoard();
    private SudokuBoard solutionsBoard = new SudokuBoard();
    private List<IntegerProperty> intPropertiesList = Arrays.asList(new SimpleIntegerProperty[81]);
    private final Logger logger = LogManager.getLogger(GameController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DifficultyLevel selectedLevel = null;
        label1.setText(App.getStartController().getNameField().getText());
        label2.setText(App.getStartController().getMenuButton1().getText());

        SudokuSolver solver = new BacktrackingSudokuSolver();
        if (App.getStartController().getLoadedBoard() == null) {
            solver.solve(sudokuBoard);
            solutionsBoard = sudokuBoard.clone();
            for (DifficultyLevel level : getStartController().getLevelsList()) {
                if (getStartController().getMenuButton1().getText().equals(level
                        .getLevelName())) {
                    selectedLevel = level;
                    deleteValues(level);
                    break;
                }
            }
        } else {
            labelLevel.setVisible(false);
            sudokuBoard = App.getStartController().getLoadedBoard();
            solutionsBoard = sudokuBoard.clone();
            solver.solve(solutionsBoard);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                intPropertiesList.set(i * 9 + j, new SimpleIntegerProperty());
                intPropertiesList.get(i * 9 + j).setValue(sudokuBoard
                        .getSudokuBox(27 * (i / 3) + ((i % 3) * 3))
                        .getElementsList().get(j).getFieldValue());
            }
        }

        fillBoard();

        for (GridPane square : squaresList) {
            for (int j = 0; j < 9; j++) {
                int finalJ = j;
                ((TextField)square.getChildren().get(j)).textProperty()
                        .addListener((observableValue, oldNumber, newNumber) -> {
                        if (!newNumber.matches("[1-9]{0,1}")) {
                            ((TextField) square.getChildren().get(finalJ)).setText(oldNumber);
                        } else {
                            ((TextField) square.getChildren().get(finalJ)).setText(newNumber);
                        }
                });
            }
        }

        /* zdarzenia
        dla przyciskow */
        EventHandler<ActionEvent> event = actionEvent -> {
            try {
                App.setRoot("startController");
            } catch (FxmlLoadException e) {
                e.printStackTrace();
            }
        };
        button1.setOnAction(event);

        EventHandler<ActionEvent> resetEvent = actionEvent -> {
            resetBoard();
        };
        button2.setOnAction(resetEvent);

        EventHandler<ActionEvent> saveEvent = actionEvent -> {
            SudokuBoardDaoFactory sudokuDao = new SudokuBoardDaoFactory();
            if (saveField.getText().isEmpty()) {
                logger.info(resourceBundle.getString("info.path"));
                saveField.setStyle("-fx-background-color: #f2000d;");
                saveField.setOpacity(0.7);
            } else {
                try {
                    sudokuDao.getFileDao(saveField.getText()).write(sudokuBoard);
                    saveField.setStyle("-fx-background-color: #00ff00;");
                    saveField.setOpacity(0.7);
                } catch (NoSuchFileException | SaveInDatabaseException
                        | IllegalNameArgumentException e) {
                    saveField.setStyle("-fx-background-color: #f2000d;");
                    saveField.setOpacity(0.7);
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        };
        saveButton.setOnAction(saveEvent);

        EventHandler<ActionEvent> saveBaseEvt = actionEvent -> {
                try {
                    SudokuBoardDaoFactory sudokuDao = new SudokuBoardDaoFactory();
                    sudokuDao.getJpaDao(saveBaseField.getText()).write(sudokuBoard);
                    saveBaseField.setStyle("-fx-background-color: #00ff00;");
                    saveBaseField.setOpacity(0.7);
                } catch (SaveInDatabaseException | NoSuchFileException
                        | IllegalNameArgumentException e) {
                    saveBaseField.setStyle("-fx-background-color: #f2000d;");
                    saveBaseField.setOpacity(0.7);
                    saveBaseField.clear();
                    logger.error(e);
                    e.printStackTrace();
                }
        };
        saveBaseButton.setOnAction(saveBaseEvt);

        DifficultyLevel finalSelectedLevel = selectedLevel;
        EventHandler<ActionEvent> closeEvent = actionEvent -> {
            button2.setDisable(true);
            labelLevel.setVisible(true);
            labelLevel.setText(resourceBundle.getString("score"));
            if (finalSelectedLevel != null) {
                label2.setText(checkBoard() + "/" + finalSelectedLevel.getFieldsToDelete());
            } else {
                label2.setText(checkBoard() + " " + resourceBundle.getString("score.load"));
            }
        };
        button3.setOnAction(closeEvent);


        // ustawienie listenera na intPropertiesList
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int finalIt = i;
                int finalItJ = j;
                StringConverter<Number> convert = new NumberStringConverter();
                intPropertiesList.get(i * 9 + j)
                        .addListener((observableValue, oldValue, newValue) -> {
                            sudokuBoard.set(27 * (finalIt / 3) + ((finalIt % 3) * 3)
                                            + (finalItJ / 3) * 9 + finalItJ % 3,
                                    Integer.parseInt(convert.toString(newValue)));
                });
            }
        }
    }

    // wypelnienie tablicy
    public void fillBoard() {
        for (int i = 0; i < 9; i++) {
            squaresList.set(i, new GridPane());
            squaresList.set(i, (GridPane) mainGridPane.getChildren().get(i));
            for (int j = 0; j < 9; j++) {
                StringConverter<Number> converter = new NumberStringConverter();
                    Bindings.bindBidirectional(((TextField) squaresList
                                    .get(i).getChildren().get(j)).textProperty(),
                            intPropertiesList.get(i * 9 + j), converter);
                    if (Integer.parseInt(((TextField) squaresList.get(i)
                            .getChildren().get(j)).getText()) != 0) {
                        ((TextField) squaresList.get(i).getChildren().get(j)).setEditable(false);
                    } else {
                        ((TextField) squaresList.get(i).getChildren().get(j)).clear();
                        ((TextField) squaresList.get(i).getChildren().get(j)).setEditable(true);
                        squaresList.get(i).getChildren().get(j)
                                .setStyle("-fx-background-color: #ffffff;");
                    }
            }
        }
    }

    // dostosowanie tablicy do poziomu gry
    public void deleteValues(DifficultyLevel chosenLevel) {
        Random rand = new Random();
        for (int k = 0; k < chosenLevel.getFieldsToDelete(); ) {
            int index = rand.nextInt(81);
            if (sudokuBoard.get(index) != 0) {
                sudokuBoard.set(index,0);
                k++;
            }
        }
    }

    // usuniecie wpisanych pol z tablicy
    public void resetBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (((TextField) squaresList.get(i).getChildren().get(j)).isEditable()) {
                    ((TextField) squaresList.get(i).getChildren().get(j)).clear();
                }
            }
        }
    }

    // sprawdzenie tablicy
    public int checkBoard() {
        int score = 0;
            for (int index1 = 0; index1 < 9; index1++) {
                for (int index2 = 0; index2 < 9; index2++) {
                    if (((TextField) squaresList.get(index1).getChildren()
                            .get(index2)).isEditable()) {
                        int index = 27 * (index1 / 3) + ((index1 % 3) * 3);
                        if (solutionsBoard.getSudokuBox(index).getElementsList()
                                .get(index2).getFieldValue() == sudokuBoard.getSudokuBox(index)
                                .getElementsList().get(index2).getFieldValue()) {
                            squaresList.get(index1).getChildren()
                                    .get(index2).setStyle("-fx-background-color: #00ff00;");
                            score++;
                        } else {
                            squaresList.get(index1).getChildren().get(index2)
                                    .setStyle("-fx-background-color: #f2000d;");
                        }
                        ((TextField) squaresList.get(index1).getChildren()
                                .get(index2)).setEditable(false);
                    }
                }
            }
            return score;
        }
}
