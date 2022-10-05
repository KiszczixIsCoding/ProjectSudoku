# ProjectSudoku   [![Status](https://img.shields.io/badge/status-finished-success.svg)]()
The application ProjectSudoku provides implementation of Sudoku with answers verification. 
User interface consists of 2 views: start menu and game view.

In the start menu user can:
* choose language (polish / english)
* choose level of difficulty (easy / medium / hard / expert)
* upload saved state of any previous game from filesystem or database

After setting configuration and clicking confirmation button, sudoku game starts. 
During the game, user puts numbers from 1 to 9 into white empty squares in order to sudoku rules. The game ends when user clicks "save" button. After that, the score is checked by backtracking algorithm, correct and incorrect answers are marked and score of the game is displayed. 

# Technologies
Project is created with:
* Java 11 - programming language
* Maven - build automation tool
* JavaFX - GUI library

# Launch
```maven
mvn clean javafx:run
```
# Illustrations
### Start menu
<img src="https://user-images.githubusercontent.com/68016136/194176750-9ed4690c-0ca1-4f3c-8ad9-b3e19da92f20.png" width="400" height="350" />

### Game view
<img src="https://user-images.githubusercontent.com/68016136/194176817-7c83de70-e652-4db1-b8ac-5bb06fbbb852.png" width="600" height="350" />

### Game view with a result
<img src="https://user-images.githubusercontent.com/68016136/194176859-0d84dfc5-8b3d-4e3c-a686-18d0f5c62591.png" width="600" height="350" />
