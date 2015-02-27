package com.usabilityetc.ui.alphaslider;

import java.util.*;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.event.*;

public class AlphaSlider<T> extends JPanel implements ChangeListener {
	public enum Orientation {
		HORIZONTAL,
		VERTICAL
	}
	private static final long serialVersionUID = 1L;
	private final List<AlphaSliderEventListener<T>> eventListeners = new ArrayList<>();
	private final List<T> values = new ArrayList<>();
	private final Orientation orientation;
	private final JSlider slider;

	public AlphaSlider() {
		this(Orientation.HORIZONTAL);
	}
	
	public AlphaSlider(final Orientation orientation) {
		this(new ArrayList<>(), orientation);
	}

	public AlphaSlider(final List<T> sliderValues) {
		this(sliderValues, Orientation.HORIZONTAL);
	}

	public AlphaSlider(final List<T> sliderValues, final Orientation orientation) {
		this.orientation = orientation;
		final int jSliderOrientation =
				(orientation == Orientation.HORIZONTAL ? JSlider.HORIZONTAL : JSlider.VERTICAL);
		slider = new JSlider(jSliderOrientation, 0, 1, 0);
		createAlphaSliderUI();
		setValues(sliderValues);
	}
	
	private void createAlphaSliderUI() {
		setLayout(new BorderLayout());
		add(slider, BorderLayout.CENTER);
	}
	
	public void setValues(final T[] sliderValues) {
		setValues(Arrays.asList(sliderValues));
	}
	
	public void setValues(final List<T> sliderValues) {
		if (sliderValues.isEmpty()) return;
		
		// Set the minimum and maximum values of the slider
		int minimumValue = 0;
		int maximumValue = sliderValues.size() - 1;
		int initialValue = minimumValue;
		
		// Reverse the list of data values in vertical orientation
		if (orientation == Orientation.VERTICAL) {
			Collections.reverse(sliderValues);
			initialValue = maximumValue;
		}
		
		values.clear();
		values.addAll(sliderValues);
		
		configureSlider(minimumValue, maximumValue, initialValue);
	}
	
	public void configureSlider(
			final int minimumValue,
			final int maximumValue,
			final int initialValue) {
		slider.addChangeListener(this);
		slider.setMinimum(minimumValue);
		slider.setMaximum(maximumValue);
		slider.setValue(initialValue);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setEnabled(false);
		slider.setEnabled(true);
		slider.setLabelTable(labelsTable());
		slider.setPaintLabels(true);
	}
	
	private Hashtable<Integer,JLabel> labelsTable() {
		final Hashtable<Integer,JLabel> labelTables = new Hashtable<>();
		for (int i = 0; i < values.size(); i += 1) {
			labelTables.put(i, new JLabel(values.get(i).toString()));
		}
		return labelTables;
	}

	public void setCurrentValue(final int currentValue) {
		slider.setValue(currentValue);
	}

	public void setToMinimumValue() {
		slider.setValue(slider.getMinimum());
	}

	public void setToMaximumValue() {
		slider.setValue(slider.getMaximum());
	}

	public void addEventListener(final AlphaSliderEventListener<T> listener) {
		eventListeners.add(listener);
	}

	public void removeEventListener(final AlphaSliderEventListener<T> listener) {
		eventListeners.remove(listener);
	}

	public void stateChanged(final ChangeEvent event) {
		final JSlider slider = (JSlider)event.getSource();
		final int sliderValue = slider.getValue();
		final T value = values.get(sliderValue);
		final AlphaSliderEvent<T> alphaSliderEvent = new AlphaSliderEvent<>(this, value);
		notifyEventListeners(alphaSliderEvent);
	}
	
	private void notifyEventListeners(final AlphaSliderEvent<T> event) {
		for (AlphaSliderEventListener<T> listener : eventListeners) {
			listener.valueChanged(event);
		}
	}
}
