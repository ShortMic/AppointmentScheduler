package Application.Repository;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ICachable<E> {

    public static boolean isCached = false;
    ObservableList<?> cache = null;

    public void populateCache() throws SQLException;

    public ObservableList<E> getCache();
}
