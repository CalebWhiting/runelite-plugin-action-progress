package com.github.calebwhiting.runelite.api.data;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("OverloadedVarargsMethod")
public class IDs {
    private final Set<Integer> ids;

    @RequiredArgsConstructor
    public static class IDsAdapter extends TypeAdapter<IDs> {

        private final IDQuery database;

        @Override
        public void write(JsonWriter out, IDs value) throws IOException {
            int[] values = value.build();
            out.value(database.getNameString(values));
        }

        @Override
        public IDs read(JsonReader in) throws IOException {
            Pattern regex = Pattern.compile(in.nextString());
            IDQuery database = this.database.copy();
            return database.query(regex).results();
        }

    }

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

    public IDs(int[] of) {
        this((Object) of);
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