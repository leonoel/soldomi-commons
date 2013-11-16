package org.soldomi.commons;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.TextNode;

public class JsonNodeReader<T> {
    private interface Mapping<T> {
	public void feed(JsonNode jsonNode, T t) throws JsonParsingException;
    }

    private final List<Mapping<T>> m_mappings = new ArrayList<Mapping<T>>();

    public JsonNodeReader() {
    }

    public <P> JsonNodeReader<T> map(final String name, final Function<T, Property<P>> metaProperty) {
	m_mappings.add(new Mapping<T>() {
		@Override public void feed(JsonNode jsonNode, T t) throws JsonParsingException {
		    JsonNode value = jsonNode.get(name);
		    metaProperty.apply(t).fromJsonNode(value);
		}
	    });
	return this;
    }

    public <P> JsonNodeReader<T> chain(final String name, final Function<T, Edge<T, P>> metaEdge, final JsonNodeReader<P> reader) {
	m_mappings.add(new Mapping<T>() {
		@Override public void feed(JsonNode jsonNode, T t) throws JsonParsingException {
		    JsonNode value = jsonNode.get(name);
		    if (!value.isArray()) {
			throw new JsonParsingException("Error parsing relation (" + name + ") (Expected : Array)");
		    }
		    EdgePopulator<P> populator = metaEdge.apply(t).populator();
		    for(JsonNode item : value) {
			reader.fromJsonNode(item, populator.next());
		    }
		}
	    });
	return this;
    }

    public void fromJsonNode(JsonNode jsonNode, T t) throws JsonParsingException {
	for (Mapping<T> mapping : m_mappings) {
	    mapping.feed(jsonNode, t);
	}
    }

}
