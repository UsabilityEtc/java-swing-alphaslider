package com.usabilityetc.alphaslider;

import java.util.*;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.event.*;

/**
 * An AlphaSlider enables users to select from a list of values of any type T.
 * The AlphaSlider uses the return value of the toString method of each instance
 * of T as the label for that instance.
 *
 * AlphaSlider clients register AlphaSliderEventListener objects to receive update
 * AlphaSliderEvent objects when the value of the slider changes.
 * 
 * @author Jeffrey Morgan
 *
 */
public class AlphaSlider<T> extends JPanel implements ChangeListener {
  /**
   * Constants that specify whether an AlphaSlider
   * has horizontal or vertical orientation.
   */
  public enum Orientation {
    HORIZONTAL,
    VERTICAL
  }

  private static final long serialVersionUID = 1L;

  // The listeners registered for update events when the slider value changes
  private final Set<AlphaSliderEventListener<T>> eventListeners = new HashSet<>();

  // The values presented by the AlphaSlider
  private final List<T> values = new ArrayList<>();

  // The horizontal or vertical orientation of the AlphaSlider
  private final Orientation orientation;

  // The underlying JSlider on which the AlphaSlider is based
  private final JSlider slider;

  /**
   * Create an empty AlphaSlider with the default horizontal orientation.
   */
  public AlphaSlider() {
    this(Orientation.HORIZONTAL);
  }
  
  /**
   * Create an empty AlphaSlider with the specified orientation.
   * 
   * @param orientation            the orientation of the AlhpaSlider
   * @throws NullPointerException  if orientation is null
   */
  public AlphaSlider(final Orientation orientation) {
    this(new ArrayList<>(), orientation);
  }

  /**
   * Create an AlphaSlider with the default horizontal 
   * orientation and populate it with the specified list of values.
   * 
   * @param sliderValues           the AlhpaSlider values
   * @throws NullPointerException  if sliderValues is null
   */
  public AlphaSlider(final List<T> sliderValues) {
    this(sliderValues, Orientation.HORIZONTAL);
  }

  /**
   * Create an AlphaSlider with the specified orientation
   * and populate it with the specified list of values.
   * 
   * @param sliderValues           the AlhpaSlider values
   * @param orientation            the orientation of the AlhpaSlider
   * @throws NullPointerException  if sliderValues is null
   * @throws NullPointerException  if orientation is null
   */
  public AlphaSlider(final List<T> sliderValues, final Orientation orientation) {
    if (sliderValues == null) {
      throw new NullPointerException("sliderValues cannot be null");
    }
    if (orientation == null) {
      throw new NullPointerException("orientation cannot be null");
    }
    this.orientation = orientation;
    slider = new JSlider(jSliderOrientation(orientation), 0, 1, 0);
    configureSlider();
    createAlphaSliderUI();
    setValues(sliderValues);
  }

  private void configureSlider() {
    slider.addChangeListener(this);
    slider.setSnapToTicks(true);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(1);
    slider.setEnabled(false);
    slider.setEnabled(true);
  }

  /**
   * Return the Swing constant used by JSlider that corresponds to the Orientation value.
   */
  private int jSliderOrientation(final Orientation orientation) {
    return (orientation == Orientation.HORIZONTAL ? JSlider.HORIZONTAL : JSlider.VERTICAL);
  }
  
  private void createAlphaSliderUI() {
    setLayout(new BorderLayout());
    add(slider, BorderLayout.CENTER);
  }

  /**
   * Populate the AlphaSlider with the specified array of values.
   * 
   * @param sliderValues  the AlhpaSlider values
   */
  public void setValues(final T[] sliderValues) {
    if (sliderValues == null) {
      throw new NullPointerException("sliderValues cannot be null");
    }
    setValues(Arrays.asList(sliderValues));
  }

  /**
   * Populate the AlphaSlider with the specified list of values.
   * 
   * @param sliderValues  the AlhpaSlider values
   */
  public void setValues(final List<T> sliderValues) {
    if (sliderValues == null) {
      throw new NullPointerException("sliderValues cannot be null");
    }
    if (sliderValues.isEmpty()) return;
    
    // Set the minimum and maximum values of the slider
    int minimumValue = 0;
    int maximumValue = sliderValues.size() - 1;
    int initialValue = minimumValue;
    
    // Reverse the list of data values in the vertical orientation
    if (orientation == Orientation.VERTICAL) {
      Collections.reverse(sliderValues);
      initialValue = maximumValue;
    }
    
    // Remove the old slider values (if any) and add the new values
    values.clear();
    values.addAll(sliderValues);
    
    // Update the slider with the new values
    slider.setMinimum(minimumValue);
    slider.setMaximum(maximumValue);
    slider.setValue(initialValue);
    slider.setLabelTable(labelsTable());
    slider.setPaintLabels(true);
  }
  
  /**
   * The underlying JSlider enables users to select from a range of numeric values.
   * The JSlider maps its numeric values to the corresponding JLabels that present
   * the values. However, the AlphaSlider contains values of any type T, which may
   * or may not be numeric. Therefore, for each AlphaSlider value, we need to
   * generate a numeric value for the JSlider, which is an integer from 0 to one
   * less that the numnber of values.
   */
  private Hashtable<Integer,JLabel> labelsTable() {
    final Hashtable<Integer,JLabel> labelTables = new Hashtable<>();
    int numericValueOfLabel = 0;
    for (T value : values) {
      labelTables.put(numericValueOfLabel, new JLabel(value.toString()));
      numericValueOfLabel += 1;
    }
    return labelTables;
  }

  /**
   * Set the current value of the AlphaSlider to the specified value.
   * 
   * @param currentValue               the new value of the slider
   * @throws NullPointerException      if currentValue is null
   * @throws IllegalArgumentException  if currentValue is not an AlphaSlider value
   */
  public void setCurrentValue(final T currentValue) {
    if (currentValue == null) {
      throw new NullPointerException("currentValue cannot be null");
    }
    final int currentValueIndex = values.indexOf(currentValue);
    if (currentValueIndex >= 0) {
      slider.setValue(currentValueIndex);
    } else {
      throw new IllegalArgumentException(
        String.format("%s is not an AlhpaSlider value", currentValue.toString()));
    }
  }

  /**
   * Return the current value of the AlphaSlider.
   * 
   * @return The current AlphaSlider value
   */
  public T getCurrentValue() {
    final int currentValueIndex = slider.getValue();
    return values.get(currentValueIndex);
  }

  /**
   * Set the current value of the AlphaSlider to the AlphaSlider's first value.
   */
  public void setToMinimumValue() {
    slider.setValue(slider.getMinimum());
  }

  /**
   * Set the current value of the AlphaSlider to the AlphaSlider's last value.
   */
  public void setToMaximumValue() {
    slider.setValue(slider.getMaximum());
  }

  /**
   * Register an AlphaSliderEventListener object to receive update
   * AlphaSliderEvent objects when the value of the slider changes.
   * 
   * @param listener               the event listener to add
   * @throws NullPointerException  if listener is null
   */
  public void addEventListener(final AlphaSliderEventListener<T> listener) {
    if (listener == null) {
      throw new NullPointerException("listener cannot be null");
    }
    eventListeners.add(listener);
  }

  /**
   * Stop the specified AlphaSliderEventListener object from receiving update
   * AlphaSliderEvent objects when the value of the slider changes.
   * 
   * @param listener               the event listener to remove
   * @throws NullPointerException  if listener is null
   */
  public void removeEventListener(final AlphaSliderEventListener<T> listener) {
    if (listener == null) {
      throw new NullPointerException("listener cannot be null");
    }
    eventListeners.remove(listener);
  }

  @Override
  public void stateChanged(final ChangeEvent event) {
    final JSlider slider = (JSlider)event.getSource();
    final int sliderValue = slider.getValue();
    final T value = values.get(sliderValue);
    final AlphaSliderEvent<T> alphaSliderEvent = new AlphaSliderEvent<>(this, value);
    notifyEventListeners(alphaSliderEvent);
  }
  
  void notifyEventListeners(final AlphaSliderEvent<T> event) {
    for (AlphaSliderEventListener<T> listener : eventListeners) {
      listener.valueChanged(event);
    }
  }
}
