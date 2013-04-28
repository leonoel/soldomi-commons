package org.soldomi.commons;

public abstract class Relationship<O, T> {
    public final O owner;

    public Relationship(O o) {
	owner = o;
    }

    public void linkTo(Relationship<T, O> target) {
	addTarget(target);
	target.addTarget(this);
    }

    protected abstract void addTarget(Relationship<T, O> target);
}
