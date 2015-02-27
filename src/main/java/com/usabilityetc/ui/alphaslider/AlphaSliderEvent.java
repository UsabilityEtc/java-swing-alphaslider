package com.usabilityetc.ui.alphaslider;

import java.util.EventObject;

public class AlphaSliderEvent<T> extends EventObject {
	private static final long serialVersionUID = 252598499080834503L;
	private T currentValue = null;

	public AlphaSliderEvent(final AlphaSlider<T> source, final T currentValue) {
		super(source);
		this.currentValue = currentValue;
	}

	public T getCurrentValue() {
		return currentValue;
	}
}
