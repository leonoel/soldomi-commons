package org.soldomi.commons2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldSet6<F1, F2, F3, F4, F5, F6> {
    private final FieldParser<F1> f1;
    private final FieldParser<F2> f2;
    private final FieldParser<F3> f3;
    private final FieldParser<F4> f4;
    private final FieldParser<F5> f5;
    private final FieldParser<F6> f6;

    public FieldSet6(FieldParser<F1> f1,
		     FieldParser<F2> f2,
		     FieldParser<F3> f3,
		     FieldParser<F4> f4,
		     FieldParser<F5> f5,
		     FieldParser<F6> f6
		     ) {
	this.f1 = f1;
	this.f2 = f2;
	this.f3 = f3;
	this.f4 = f4;
	this.f5 = f5;
	this.f6 = f6;
    }

    public <T> RowParser<T> toRowParser(final Function6<F1, F2, F3, F4, F5, F6, T> constructor) {
	return new RowParser<T>() {
	    @Override public T parseRow(ResultSet resultSet) throws SQLException {
		return constructor.apply(f1.parse(resultSet),
					 f2.parse(resultSet),
					 f3.parse(resultSet),
					 f4.parse(resultSet),
					 f5.parse(resultSet),
					 f6.parse(resultSet)
					 );
	    }
	};
    }

    public FieldSet7<F1, F2, F3, F4, F5, F6, Integer> addInt() {
	return new FieldSet7<F1, F2, F3, F4, F5, F6, Integer>(f1, f2, f3, f4, f5, f6, FieldParser.asInt(7));
    }

    public FieldSet7<F1, F2, F3, F4, F5, F6, Long> addLong() {
	return new FieldSet7<F1, F2, F3, F4, F5, F6, Long>(f1, f2, f3, f4, f5, f6, FieldParser.asLong(7));
    }

    public FieldSet7<F1, F2, F3, F4, F5, F6, String> addString() {
	return new FieldSet7<F1, F2, F3, F4, F5, F6, String>(f1, f2, f3, f4, f5, f6, FieldParser.asString(7));
    }

}
