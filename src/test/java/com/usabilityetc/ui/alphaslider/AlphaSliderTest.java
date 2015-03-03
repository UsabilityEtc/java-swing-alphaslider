package com.usabilityetc.ui.alphaslider;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import com.usabilityetc.ui.alphaslider.AlphaSlider.Orientation;

/**
 * Unit tests for the AlphaSlider class.
 *
 * @author Jeffrey Morgan
 *
 */
public class AlphaSliderTest {
	private AlphaSlider<String> alphaslider;

	@Before
	public void setUp() {
		alphaslider = new AlphaSlider<>(listOf(colors()));
	}
	
	private List<String> listOf(final String[] array) {
		return Arrays.asList(array);
	}

	private String[] colors() {
		return new String[] { "red", "green", "blue", "orange", "purple" };
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullOrientationOnly() {
		new AlphaSlider<>((Orientation)null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullListOnly() {
		new AlphaSlider<>((ArrayList<String>)null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullSliderValues() {
		new AlphaSlider<>(null, Orientation.HORIZONTAL);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullOrientation() {
		new AlphaSlider<>(new ArrayList<String>(), null);
	}

	@Test(expected = NullPointerException.class)
	public void testSetValuesWithNull() {
		alphaslider.setValues((String [])null);
	}

	@Test
	public void testSetToMinimumValue() {
		alphaslider.setToMinimumValue();
		assertEquals(alphaslider.getCurrentValue(), firstValue());
	}

	private String firstValue() {
		return colors()[0];
	}

	@Test
	public void testSetToMaximumValue() {
		alphaslider.setToMaximumValue();
		assertEquals(alphaslider.getCurrentValue(), lastValue());
	}

	private String lastValue() {
		return colors()[colors().length - 1];
	}

	@Test
	public void testSetAndGetCurrentValue() {
		alphaslider.setCurrentValue(aValue());
		assertEquals(alphaslider.getCurrentValue(), aValue());
	}

	private String aValue() {
		return colors()[2];
	}

	@Test(expected = NullPointerException.class)
	public void testSetCurrentValueNull() {
		alphaslider.setCurrentValue(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCurrentValueMissingValue() {
		alphaslider.setCurrentValue("yellow");
	}

	@Test(expected = NullPointerException.class)
	public void testAddNullEventListener() {
		alphaslider.addEventListener(null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveNullEventListener() {
		alphaslider.removeEventListener(null);
	}

	@Test
	public void testNotifyEventListeners() {
		final TestAlphaSliderEventListener<String> listener = new TestAlphaSliderEventListener<>();
		alphaslider.addEventListener(listener);
		alphaslider.notifyEventListeners(alphaSliderEvent());
		assertTrue(listener.alphaSliderListenerWasInvoked);
	}

	private AlphaSliderEvent<String> alphaSliderEvent() {
		return new AlphaSliderEvent<>(alphaslider, aValue());
	}

	private class TestAlphaSliderEventListener<String> implements AlphaSliderEventListener<String> {
		boolean alphaSliderListenerWasInvoked = false;
		
		@Override
		public void valueChanged(final AlphaSliderEvent<String> event) {
			alphaSliderListenerWasInvoked = true;
		}
	}
}
