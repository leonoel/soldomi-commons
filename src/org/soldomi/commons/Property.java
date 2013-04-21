package org.soldomi.commons;

public class Property<P, Q> {
    public final P owner;
    private Q m_value = null;

    public Property(P owner) {
	this.owner = owner;
    }
    
    public void set(Q value) {
	m_value = value;
    }

    public Q get() {
	return m_value;
    }
}
