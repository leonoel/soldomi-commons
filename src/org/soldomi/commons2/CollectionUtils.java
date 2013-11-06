package org.soldomi.commons2;

import java.util.List;
import java.util.ArrayList;

public class CollectionUtils {
    public static <I, O> O reduce(List<I> inputs, Reducer<I, O> reducer, O first) {
	O acc = first;
	for (I i : inputs) {
	    acc = reducer.apply(i, acc);
	}
	return acc;
    }
}
