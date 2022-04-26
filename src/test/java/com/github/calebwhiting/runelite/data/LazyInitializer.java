package com.github.calebwhiting.runelite.data;

public abstract class LazyInitializer<T> {

    private boolean initialized = false;
    private T value;

    public T get() {
        if (!initialized) {
            this.value = this.create();
            this.initialized = true;
        }
        return this.value;
    }

    protected abstract T create();

}
