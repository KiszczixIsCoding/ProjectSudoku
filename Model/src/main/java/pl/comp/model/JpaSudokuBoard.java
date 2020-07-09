package pl.comp.model;

import java.util.Random;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pl.comp.model.exceptions.IllegalNameArgumentException;
import pl.comp.model.exceptions.SaveInDatabaseException;

public class JpaSudokuBoard implements Dao<SudokuBoard> {
    String boardName;
    EntityManager manager;

    public JpaSudokuBoard(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public SudokuBoard read() {
        EntityManagerFactory factory  = Persistence.createEntityManagerFactory("SudokuBoards");
        manager = factory.createEntityManager();
        SudokuBoard board = manager.createQuery("select a from SudokuBoard a WHERE a.name = :name",
                SudokuBoard.class).setParameter("name", boardName).getSingleResult();
        return board;
    }

    @Override
    public void write(SudokuBoard board) throws SaveInDatabaseException,
            IllegalNameArgumentException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language_version");
        EntityManagerFactory factory  = Persistence
                .createEntityManagerFactory("SudokuBoards");
        manager = factory.createEntityManager();

        if (boardName.isEmpty()) {
            board.setName(String.valueOf(new Random().nextLong()));
        } else {
            board.setName(boardName);
        }

        for (int k = 0; k < manager.createQuery("select a from SudokuBoard a")
                .getResultList().size(); k++) {
            if (board.getName().equals(((SudokuBoard) manager
                    .createQuery("select a from SudokuBoard a")
                    .getResultList().get(k)).getName())) {
                throw new IllegalNameArgumentException(resourceBundle
                        .getString("exception.illegalName"));
            }
        }
            try {
                manager.getTransaction().begin();
                manager.persist(board);
                manager.getTransaction().commit();
            } catch (Exception e) {
                throw new SaveInDatabaseException(resourceBundle
                        .getString("exception.saveInDatabase"));
            }
        }
    }
