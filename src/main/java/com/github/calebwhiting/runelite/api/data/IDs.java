package com.github.calebwhiting.runelite.api.data;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("OverloadedVarargsMethod")
public class IDs {
    private final Set<Integer> ids;

    @SuppressWarnings("ChainOfInstanceofChecks")
    private static int[] objectToInts(Object o) {
        if (o instanceof int[]) {
            return (int[]) o;
        }
        if (o instanceof Integer) {
            return new int[]{(int) o};
        }
        if (o instanceof IDs) {
            return ((IDs) o).build();
        }
        throw new IllegalArgumentException(String.format("Unsupported type: %s", o.getClass()));
    }

    public IDs(Set<Integer> ids) {
        this.ids = Collections.unmodifiableSet(ids);
    }

    // map various types to a single set of integers
    // handles int/int[]/IDs
    public IDs(Object... of) {
        this(Stream.of(of).map(IDs::objectToInts).flatMapToInt(IntStream::of).boxed().collect(Collectors.toSet()));
    }

    public boolean contains(int id) {
        return this.ids.contains(id);
    }

    public int[] build() {
        return this.ids.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public String toString() {
        return "IDs{" +
                "" + ids +
                '}';
    }
}