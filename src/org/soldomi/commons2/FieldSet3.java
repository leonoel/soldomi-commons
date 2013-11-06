package org.soldomi.commons2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldSet3<F1, F2, F3> {
    private final FieldParser<F1> f1;
    private final FieldParser<F2> f2;
    private final FieldParser<F3> f3;

    public FieldSet3(FieldParser<F1> f1,
		     FieldParser<F2> f2,
		     FieldParser<F3> f3) {
	this.f1 = f1;
	this.f2 = f2;
	this.f3 = f3;
    }

    public <T> RowParser<T> toRowParser(final Function3<F1, F2, F3, T> constructor) {
	return new RowParser<T>() {
	    @Override public T parseRow(ResultSet resultSet) throws SQLException {
		return constructor.apply(f1.parse(resultSet),
					 f2.parse(resultSet),
					 f3.parse(resultSet));
	    }
	};
    }

}
