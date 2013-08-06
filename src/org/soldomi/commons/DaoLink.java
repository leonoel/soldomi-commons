package org.soldomi.commons;

import java.sql.Connection;
import java.util.Iterator;

public class DaoLink<I, T> extends DaoAction<I> {
    private final DaoAction<T> m_action;
    private final Function<I, Edge<I, T>> m_metaEdge;

    public DaoLink(DaoAction<T> action,
		   Function<I, Edge<I, T>> metaEdge) {
	m_action = action;
	m_metaEdge = metaEdge;
    }

    public Result<Void> run(final Connection connection,
			    final I i) {
	Result<Void> result = Result.<Void>success(null);
	Iterator<T> it = m_metaEdge.apply(i).iterator();
	while(result.success && it.hasNext()) {
	    result = m_action.run(connection, it.next());
	}
	return result;
    }

}

