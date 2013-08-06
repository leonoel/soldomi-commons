package org.soldomi.commons;

import java.util.Iterator;
import java.lang.Iterable;
import java.util.Set;
import java.util.HashSet;

public abstract class Edge<T, P> implements Iterable<P> {

    public final T parent;
    public final Function<Void, P> buddyConstructor;
    public final Function<P, Edge<P, T>> buddyEdge;

    protected Edge(T parent,
		   Function<Void, P> buddyConstructor,
		   Function<P, Edge<P, T>> buddyEdge) {
	this.parent = parent;
	this.buddyConstructor = buddyConstructor;
	this.buddyEdge = buddyEdge;
    }

    public static <T, U, P> Function<T, P> alias(final Function<T, Edge<T, U>> edge,
						 final Function<U, P> target) {
	return new Function<T, P>() {
	    @Override public P apply(T t) {
		Iterator<U> it = edge.apply(t).iterator();
		if(it.hasNext()) {
		    return target.apply(it.next());
		} else {
		    throw new RuntimeException();
		}
	    }
	};
    }


    public static <T> Function<T, Edge<T, T>> loop() {
	return new Function<T, Edge<T, T>>() {
	    @Override public Edge<T, T> apply(T t) {
		Edge<T, T> edge =  makeOne(t,
					   new Function<Void, T>() {
					       @Override public T apply(Void value) {
						   throw new UnsupportedOperationException();
					       }
					   },
					   this);
		edge.bindSingle(t);
		return edge;
	    };
	};
    }

    public static <T, P> Edge<T, P> makeMany(T parent,
					     Function<Void, P> buddyConstructor,
					     Function<P, Edge<P, T>> buddyEdge) {
	return new Edge<T, P>(parent,
			      buddyConstructor,
			      buddyEdge) {
	    final Set<P> m_targets = new HashSet<P>();

	    @Override public int count() {
		return m_targets.size();
	    }
	    
	    @Override public void bindSingle(P target) {
		m_targets.add(target);
	    }

	    @Override public EdgePopulator<P> populator() {
		return new EdgePopulator<P>() {
		    private final Iterator<P> targetIterator = m_targets.iterator();

		    @Override public P next() {
			if (targetIterator.hasNext()) {
			    return targetIterator.next();
			} else {
			    P p = buddyConstructor.apply(null);
			    bind(p);
			    return p;
			}
		    }
		};
	    }

	    @Override public Iterator<P> iterator() {
		return m_targets.iterator();
	    }
	};
    }


    public static <T, P> Edge<T, P> makeOne(T parent,
					    Function<Void, P> buddyConstructor,
					    Function<P, Edge<P, T>> buddyEdge) {
	return new Edge<T, P>(parent,
			      buddyConstructor,
			      buddyEdge) {
	    P m_target;

	    @Override public int count() {
		return m_target == null ? 0 : 1;
	    }

	    @Override public void bindSingle(P target) {
		m_target = target;
	    }

	    @Override public EdgePopulator<P> populator() {
		return new EdgePopulator<P>() {
		    private boolean end = false;

		    @Override public P next() {
			if (end) {
			    throw new UnsupportedOperationException("ModelOne.nextOrNew : cannot make more than one.");
			}
			if (m_target == null) {
			    bind(buddyConstructor.apply(null));
			}
			end = true;
			return m_target;
		    }
		};
	    }

	    @Override public Iterator<P> iterator() {
		return new Iterator<P>() {
		    private boolean end = false;

		    @Override public boolean hasNext() {
			return !end;
		    }

		    @Override public P next() {
			if (end) {
			    throw new UnsupportedOperationException();
			} else {
			    end = true;
			    return m_target;
			}
		    }

		    @Override public void remove() {
			throw new UnsupportedOperationException();
		    }
		};
	    }
	};
    }
    
    public void bind(P p) {
	bindSingle(p);
	buddyEdge.apply(p).bindSingle(parent);
    }

    public void forEach(Function<P, Void> f) {
	for(P p : this) {
	    f.apply(p);
	}
    }

    public <R> R reduce(Reducer<P, R> reducer, R result) {
	for(P p : this) {
	    result = reducer.foldIn(p, result);
	}
	return result;
    }

    public abstract int count();
    public abstract void bindSingle(P other);
    public abstract EdgePopulator<P> populator();
    public abstract Iterator<P> iterator();

}
