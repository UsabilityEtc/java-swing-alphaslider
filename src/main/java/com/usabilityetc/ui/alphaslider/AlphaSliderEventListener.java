package com.usabilityetc.ui.alphaslider;

import java.util.EventListener;

public interface AlphaSliderEventListener<T> extends EventListener {
	public void valueChanged(AlphaSliderEvent<T> event);
}
