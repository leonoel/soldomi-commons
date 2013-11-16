package org.soldomi.commons;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class JsonNodeWriter<T> {
    private interface Mapping<T> {
	public void feed(ObjectNode objectNode, T t);
    }

    private final List<Mapping<T>> m_mappings = new ArrayList<Mapping<T>>();

    public JsonNodeWriter() {
    }

    public <P> JsonNodeWriter<T> map(final String name, final Function<T, Property<P>> metaProperty) {
	m_mappings.add(new Mapping<T>() {
		@Override public void feed(ObjectNode objectNode, T t) {
		    objectNode.put(name, metaProperty.apply(t).toJsonNode());
		}
	    });
	return this;
    }

    public <P> JsonNodeWriter<T> chain(final String name, final Function<T, Edge<T, P>> metaEdge, final JsonNodeWriter<P> writer) {
	m_mappings.add(new Mapping<T>() {
		@Override public void feed(ObjectNode objectNode, T t) {
		    final ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
		    metaEdge.apply(t).forEach(new Function<P, Void>() {
			    @Override public Void apply(P p) {
				arrayNode.add(writer.toJsonNode(p));
				return null;
			    }
			});
		    objectNode.put(name, arrayNode);
		}
	    });
	return this;
    }

    public JsonNode toJsonNode(T t) {
	ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
	for (Mapping<T> mapping : m_mappings) {
	    mapping.feed(objectNode, t);
	}
	return objectNode;
    }
}
