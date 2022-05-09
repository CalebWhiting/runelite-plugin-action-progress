package com.github.calebwhiting.runelite.dev;

class HistoricEvent
{

	private final int tick;

	private final Object event;

	HistoricEvent(int tick, String event)
	{
		this.tick = tick;
		this.event = event;
	}

	public int getTick()
	{
		return this.tick;
	}

	public Object getEvent()
	{
		return this.event;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		HistoricEvent that = (HistoricEvent) o;
		return this.getTick() == that.getTick() && this.getEvent().equals(that.getEvent());
	}

	@Override
	public int hashCode()
	{
		int result = this.getTick();
		result = 31 * result + this.getEvent().hashCode();
		return result;
	}

}
