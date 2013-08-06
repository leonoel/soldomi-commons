package org.soldomi.commons;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.LongNode;
import org.codehaus.jackson.node.TextNode;
import org.codehaus.jackson.map.util.StdDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.text.ParseException;

public abstract class Property<P> {
    private P value;

    protected Property() {}

    public P get() {
	return value;
    }

    public void set(P value) {
	this.value = value;
    }

    public static Property<Long> makeLong() {
	return new Property<Long>() {

	    @Override public JsonNode toJsonNode() {
		return new LongNode(get());
	    }

	    @Override public void fromJsonNode(JsonNode value) throws JsonParsingException {
		if (!value.isLong()) {
		    throw new JsonParsingException("Error parsing property (Expected : Long)");
		}
		set(value.getLongValue());
	    }

	    @Override public void feedStatement(PreparedStatement statement, Integer index) throws SQLException {
		if (get() == null) {
		    statement.setNull(index, Types.BIGINT);
		} else {
		    statement.setLong(index, get());
		}
	    }

	    @Override public void fromResultSet(ResultSet resultSet, Integer index) throws SQLException {
		set(resultSet.getLong(index));
	    }
	};
    }

    public static Property<String> makeString() {
	return new Property<String>() {

	    @Override public JsonNode toJsonNode() {
		return new TextNode(get());
	    }

	    @Override public void fromJsonNode(JsonNode value) throws JsonParsingException {
		if (!value.isTextual()) {
		    throw new JsonParsingException("Error parsing property (Expected : Text)");
		}
		set(value.getTextValue());
	    }

	    @Override public void feedStatement(PreparedStatement statement, Integer index) throws SQLException {
		if (get() == null) {
		    statement.setNull(index, Types.VARCHAR);
		} else {
		    statement.setString(index, get());
		}
	    }

	    @Override public void fromResultSet(ResultSet resultSet, Integer index) throws SQLException {
		set(resultSet.getString(index));
	    }
	};
    }

    public static Property<Date> makeDate() {
	return new Property<Date>() {

	    @Override public JsonNode toJsonNode() {
		return new TextNode(StdDateFormat.instance.format(get()));
	    }

	    @Override public void fromJsonNode(JsonNode value) throws JsonParsingException {
		if (!value.isTextual()) {
		    throw new JsonParsingException("Error parsing date property : Expected Text");
		}
		try {
		    set(StdDateFormat.instance.parse(value.getTextValue()));
		} catch (ParseException e) {
		    throw new JsonParsingException("Error parsing date property : Bad format.");
		}
	    }

	    @Override public void feedStatement(PreparedStatement statement, Integer index) throws SQLException {
		if (get() == null) {
		    statement.setNull(index, Types.DATE);
		} else {
		    statement.setDate(index, new java.sql.Date(get().getTime()));
		}
	    }

	    @Override public void fromResultSet(ResultSet resultSet, Integer index) throws SQLException {
		set(resultSet.getDate(index));
	    }

	};

    }
    
    public abstract JsonNode toJsonNode();
    public abstract void fromJsonNode(JsonNode value) throws JsonParsingException;
    public abstract void feedStatement(PreparedStatement statement, Integer index) throws SQLException;
    public abstract void fromResultSet(ResultSet resultSet, Integer index) throws SQLException;

}
