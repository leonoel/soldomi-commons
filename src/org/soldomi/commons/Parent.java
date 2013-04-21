package org.soldomi.commons;

public class Parent<P, Q> {
    public final P owner;

    public Parent(P owner) {
	this.owner = owner;
    }
}
