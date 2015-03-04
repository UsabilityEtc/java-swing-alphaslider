package com.usabilityetc.alphaslider;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.usabilityetc.alphaslider.AlphaSlider.Orientation;

/**
 * This class demonstrates the AlphaSlider class by creating one horizontal and one vertical AlphaSlider.
 * Each AlphaSlider is presented in its own JFrame and enables users to select from a list of color names.
 * The color name is displayed on the command line (or console) when the user changes the current color.
 */
public class AlphaSliderComponentExample {
   /**
     * Create a JFrame with the specified dimensions to hold an AlphaSlider.
     * The orientation of the AlphaSlider is computed from the dimensions.
     *
     * @param width        the width of the JFrame
     * @param height       the height of the JFrame
     * @param orientation  the horizontal or vertical orientation of the AlphaSlider
     */
  public AlphaSliderComponentExample(final int width, final int height) {
    final Orientation orientation = (width > height ? Orientation.HORIZONTAL : Orientation.VERTICAL);
    createTestFrame(width, height, orientation);
  }
 
  /**
   * Create a JFrame to hold an AlphaSlider in the specified orientation.
   *
   * @param width        the width of the JFrame
   * @param height       the height of the JFrame
   * @param orientation  the horizontal or vertical orientation of the AlphaSlider
   */
  private void createTestFrame(final int width, final int height, final Orientation orientation) {
    final JFrame frame = new JFrame("AlphaSlider");
    frame.getContentPane().add(contentComponent(orientation));
    frame.setPreferredSize(new Dimension(width, height));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
  }
 
  /**
   * Create an AlphaSlider with the specified orientation
   * and place it in a JPanel with a 10 pixel border.
   *
   * @param orientation  the horizontal or vertical orientation of the AlphaSlider
   */
  private JComponent contentComponent(final Orientation orientation) {
    final int margin = 10;
    final JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(margin, margin, margin, margin));
    contentPanel.add(alphaSliderComponent(orientation), BorderLayout.CENTER);
    return contentPanel;
  }

  /**
   * Create an AlphaSlider with the specified orientation and add a listener
   * that displays the current AlphaSlider value on the command line (or console)
   *
   * @param orientation  the horizontal or vertical orientation of the AlphaSlider
   */
  private AlphaSlider<String> alphaSliderComponent(final Orientation orientation) {
    String[] colourNames = new String[] {
        "Black",
        "Blue",
        "Cyan",
        "Gray",
        "Green",
        "Magenta",
        "Orange",
        "Pink",
        "Red",
        "White",
        "Yellow"
    };
    final AlphaSlider<String> alphaSliderComponent = new AlphaSlider<>(orientation);
    alphaSliderComponent.setValues(colourNames);
    alphaSliderComponent.addEventListener(new AlphaSliderEventListener<String>() {
      @Override
      public void valueChanged(final AlphaSliderEvent<String> event) {
        System.out.println(event.getCurrentValue());
      }
    });
    return alphaSliderComponent;
  }
  
  public static void main(final String[] args) {
    final int WIDTH = 800;
    final int HEIGHT = 150;
    new AlphaSliderComponentExample(WIDTH, HEIGHT);
    new AlphaSliderComponentExample(HEIGHT, WIDTH);
  }
}
