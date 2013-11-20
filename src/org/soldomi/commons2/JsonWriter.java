package org.soldomi.commons2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.List;
import java.util.Arrays;

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

    protected final Property property(String name, Integer value) {
	return new Property(name, new IntNode(value));
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

    protected final JsonNode object(List<Property> properties) {
	ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
	for (Property property : properties) {
	    objectNode.put(property.name, property.value);
	}
	return objectNode;
    }

    protected final JsonNode object(Property... properties) {
	return object(Arrays.asList(properties));
    }

    protected final <U> JsonNode array(List<U> values, JsonWriter<U> writer) {
	ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
	for (U value : values) {
	    arrayNode.add(writer.write(value));
	}
	return arrayNode;
    }
}
