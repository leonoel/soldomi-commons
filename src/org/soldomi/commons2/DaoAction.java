package org.soldomi.commons2;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public abstract class DaoAction<I, O> {

    public DaoAction() {}

    public <T> DaoAction<I, T> chain(final DaoAction<O, T> other) {
	return new DaoAction<I, T>() {
	    @Override public Result<T> run(Connection connection, I i) {
		Result<O> result = DaoAction.this.run(connection, i);
		if (result.success) 
		    return other.run(connection, result.value());
		else
		    return Result.<T>failure(result.error());
	    }
	};
    }

    public <T> DaoAction<I, T> map(final Function1<O, T> fct) {
	return new DaoAction<I, T>() {
	    @Override public Result<T> run(Connection connection, I i) {
		return DaoAction.this.run(connection, i).map(fct);
	    }	    
	};
    }

    public static <I, O> Result<List<O>> runAll(Connection connection, final List<I> items, final DaoAction<I, O> itemAction) {
	return CollectionUtils.reduce(items, new Reducer<I, DaoAction<Void, List<O>>>() {
		@Override public DaoAction<Void, List<O>> apply(final I item, DaoAction<Void, List<O>> acc) {
		    return acc.chain(new DaoAction<List<O>, List<O>>() {
			    @Override public Result<List<O>> run(Connection connection, final List<O> oldList) {
				return itemAction.run(connection, item).map(new Function1<O, List<O>>() {
					@Override public List<O> apply(O newItem) {
					    List<O> newList = new ArrayList<O>(oldList);
					    newList.add(newItem);
					    return newList;
					}
				    });
			    }
			});
		}
	    }, new DaoAction<Void, List<O>>() {
		@Override public Result<List<O>> run(Connection connection, Void v) {
		    return Result.<List<O>>success(new ArrayList<O>());
		}
	    }).run(connection, null);
    }

    public abstract Result<O> run(Connection connection,
				  I i);

    public Result<O> runInTransaction(Connection connection,
				      I i) {
	Result<O> result;
	try {
	    connection.setAutoCommit(false);
	    result = run(connection, i);
	    connection.commit();
	} catch (SQLException e1) {
	    try {
		connection.rollback();
	    } catch (SQLException e2) {
		e2.printStackTrace();
	    }
	    result = Result.<O>failure(e1.toString());
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e2) {
		e2.printStackTrace();
	    }
	}
	return result;
    }

}
