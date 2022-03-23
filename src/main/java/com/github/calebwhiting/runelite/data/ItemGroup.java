package com.github.calebwhiting.runelite.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ItemGroup {

    private final Set<Integer> ids;

    public ItemGroup(Set<Integer> ids) {
        this.ids = Collections.unmodifiableSet(ids);
    }

    public ItemGroup(int... ids) {
        this(IntStream.of(ids).boxed().collect(Collectors.toSet()));
    }

    public ItemGroup and(ItemGroup... others) {
        Set<Integer> ids = new HashSet<>(this.ids);
        for (ItemGroup other : others)
            ids.addAll(other.ids);
        return new ItemGroup(ids);
    }

    public ItemGroup and(int... other) {
        Set<Integer> ids = new HashSet<>(this.ids);
        IntStream.of(other).forEach(ids::add);
        return new ItemGroup(ids);
    }

    public int[] build() {
        return this.ids.stream().mapToInt(Integer::intValue).toArray();
    }

}
