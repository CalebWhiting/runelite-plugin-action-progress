package com.github.calebwhiting.runelite.api.data;

import java.util.function.Predicate;
import java.util.stream.Stream;

interface IQuery<T, Y extends IQuery<T, Y>> {

    Y and(Predicate<T> filter);

    Y or(Predicate<T> query);

    Y newOr();

    boolean accepts(T t);

    Stream<T> query();

    Y not();

    default Y and(Y query) {
        return this.and(query::accepts);
    }

    @SuppressWarnings("unchecked")
    default Y andAny(Y... queries) {
        return this.and(i -> {
            for (Y query : queries) {
                if (query.accepts(i)) {
                    return true;
                }
            }
            return false;
        });
    }

    default Y or(Y query) {
        return this.or(query::accepts);
    }

}
