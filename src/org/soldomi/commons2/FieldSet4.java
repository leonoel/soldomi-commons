package org.soldomi.commons2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldSet4<F1, F2, F3, F4> {
    private final FieldParser<F1> f1;
    private final FieldParser<F2> f2;
    private final FieldParser<F3> f3;
    private final FieldParser<F4> f4;

    public FieldSet4(FieldParser<F1> f1,
		     FieldParser<F2> f2,
		     FieldParser<F3> f3,
		     FieldParser<F4> f4) {
	this.f1 = f1;
	this.f2 = f2;
	this.f3 = f3;
	this.f4 = f4;
    }

    public <T> RowParser<T> toRowParser(final Function4<F1, F2, F3, F4, T> constructor) {
	return new RowParser<T>() {
	    @Override public T parseRow(ResultSet resultSet) throws SQLException {
		return constructor.apply(f1.parse(resultSet),
					 f2.parse(resultSet),
					 f3.parse(resultSet),
					 f4.parse(resultSet)
					 );
	    }
	};
    }

    public FieldSet5<F1, F2, F3, F4, Integer> addInt() {
	return new FieldSet5<F1, F2, F3, F4, Integer>(f1, f2, f3, f4, FieldParser.asInt(5));
    }

    public FieldSet5<F1, F2, F3, F4, Long> addLong() {
	return new FieldSet5<F1, F2, F3, F4, Long>(f1, f2, f3, f4, FieldParser.asLong(5));
    }

    public FieldSet5<F1, F2, F3, F4, String> addString() {
	return new FieldSet5<F1, F2, F3, F4, String>(f1, f2, f3, f4, FieldParser.asString(5));
    }
}
