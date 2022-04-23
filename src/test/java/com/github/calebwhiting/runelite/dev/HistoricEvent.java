package com.github.calebwhiting.runelite.dev;

class HistoricEvent  {

    private final int tick;
    private final Object event;

    public int getTick() {
        return tick;
    }

    public Object getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HistoricEvent that = (HistoricEvent) o;
        return getTick() == that.getTick() &&
                getEvent().equals(that.getEvent());
    }

    @Override
    public int hashCode() {
        int result = getTick();
        result = 31 * result + getEvent().hashCode();
        return result;
    }

    HistoricEvent(int tick, String event) {
        this.tick = tick;
        this.event = event;
    }

}
