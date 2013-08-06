package org.soldomi.commons;

public abstract class Result<T> {
    public final Boolean success;

    private Result(Boolean success) {
	this.success = success;
    }

    public abstract T value();
    public abstract String error();

    public static <T> Result<T> success(final T value) {
	return new Result<T>(Boolean.TRUE) {
	    @Override public T value() { return value; }
	    @Override public String error() { throw new UnsupportedOperationException(); }
	};
    }

    public static <T> Result<T> failure(final String error) {
	return new Result<T>(Boolean.FALSE) {
	    @Override public T value() { throw new UnsupportedOperationException(); }
	    @Override public String error() { return error; }
	};
    }
}
