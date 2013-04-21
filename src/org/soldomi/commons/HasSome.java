package org.soldomi.commons;

import java.util.Set;
import java.util.HashSet;

public class HasSome<P, Q> {
    public final P owner;
    private final Set<Q> m_values = new HashSet<Q>();

    public HasSome(P owner) {
	this.owner = owner;
    }

    public void add(Property<Q, P> property) {
	m_values.add(property.owner);
	property.set(owner);
    }

    public void add(HasSome<Q, P> hasSome) {
	m_values.add(hasSome.owner);
	hasSome.m_values.add(owner);
    }
}
