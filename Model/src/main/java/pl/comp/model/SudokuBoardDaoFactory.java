package pl.comp.model;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String fileName) {
        Dao<SudokuBoard> dao = new FileSudokuBoard(fileName);
        return dao;
    }

    public  Dao<SudokuBoard> getJpaDao(String id) {
        Dao<SudokuBoard> dao = new JpaSudokuBoard(id);
        return dao;
    }
}
