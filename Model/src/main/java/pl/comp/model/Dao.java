package pl.comp.model;

import pl.comp.model.exceptions.IllegalNameArgumentException;
import pl.comp.model.exceptions.IncorrectDataSize;
import pl.comp.model.exceptions.NoDataException;
import pl.comp.model.exceptions.NoSuchFileException;
import pl.comp.model.exceptions.SaveInDatabaseException;
import pl.comp.model.exceptions.UnparsableStringToIntException;

public interface Dao<T> {

    T read() throws NoSuchFileException, NoDataException,
            UnparsableStringToIntException, IncorrectDataSize;

    void write(T board) throws NoSuchFileException,
            SaveInDatabaseException, IllegalNameArgumentException;
}
