package org.soldomi.commons;

import java.util.Set;
import java.util.HashSet;

public class MultipleRelationship<O, T> extends Relationship<O, T> {
    private final Set<Relationship<T, O>> m_targets = new HashSet<Relationship<T, O>>();

    public MultipleRelationship(O owner) {
	super(owner);
    }

    public Set<T> group() {
	Set<T> result = new HashSet<T>();
	for (Relationship<T, O> r : m_targets) {
	    result.add(r.owner);
	}
	return result;
    }

    @Override protected void addTarget(Relationship<T, O> target) {
	m_targets.add(target);
    }
}
