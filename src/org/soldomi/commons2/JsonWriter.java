package org.soldomi.commons2;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.LongNode;
import org.codehaus.jackson.node.TextNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;

import java.util.List;

public abstract class JsonWriter<T> {
    public abstract JsonNode write(T value);

    public static class Property {
	public final String name;
	public final JsonNode value;

	public Property(String name, JsonNode value) {
	    this.name = name;
	    this.value = value;
	}
    }

    protected final Property property(String name, Long value) {
	return new Property(name, new LongNode(value));
    }

    protected final Property property(String name, String value) {
	return new Property(name, new TextNode(value));
    }

    protected final Property property(String name, JsonNode value) {
	return new Property(name, value);
    }

    protected final JsonNode object(Property... properties) {
	ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
	for (Property property : properties) {
	    objectNode.put(property.name, property.value);
	}
	return objectNode;
    }

    protected final <U> JsonNode array(List<U> values, JsonWriter<U> writer) {
	ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
	for (U value : values) {
	    arrayNode.add(writer.write(value));
	}
	return arrayNode;
    }
}
