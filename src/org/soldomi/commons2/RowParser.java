package org.soldomi.commons2;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RowParser<T> {
    public abstract T parseRow(ResultSet resultSet) throws SQLException;
}
