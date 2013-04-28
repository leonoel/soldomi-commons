package org.soldomi.commons;

public class SingleRelationship<O, T> extends Relationship<O, T> {
    private Relationship<T, O> m_target;

    public SingleRelationship(O owner) {
	super(owner);
    }

    public T target() {
	return m_target.owner;
    }

    @Override protected void addTarget(Relationship<T, O> target) {
	m_target = target;
    }
}
