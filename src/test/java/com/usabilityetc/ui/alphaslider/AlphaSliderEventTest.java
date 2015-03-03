package com.usabilityetc.ui.alphaslider;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for the AlphaSliderEvent class.
 *
 * @author Jeffrey Morgan
 *
 */
public class AlphaSliderEventTest {
	private final AlphaSlider<String> alphaslider = new AlphaSlider<>();
	private final String value = "orange";

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullValue() {
		new AlphaSliderEvent<>(alphaslider, null);
	}

	@Test
	public void testGetCurrentValue() {
		final AlphaSliderEvent<String> alphasliderEvent = new AlphaSliderEvent<>(alphaslider, value);
		assertEquals(alphasliderEvent.getCurrentValue(), value);
	}
}
