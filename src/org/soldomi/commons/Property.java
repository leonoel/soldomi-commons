package org.soldomi.commons;

public class Property<T> {
    private T m_value;

    public void set(T value) {
	m_value = value;
    }

    public T get() {
	return m_value;
    }
    
}
