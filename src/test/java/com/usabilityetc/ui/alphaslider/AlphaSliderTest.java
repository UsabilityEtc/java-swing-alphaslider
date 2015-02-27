package com.usabilityetc.ui.alphaslider;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.usabilityetc.ui.alphaslider.AlphaSlider.Orientation;

public class AlphaSliderTest {
	public AlphaSliderTest(final int width, final int height) {
		final Orientation orientation = (width > height ? Orientation.HORIZONTAL : Orientation.VERTICAL);
		createTestFrame(width, height, orientation);
	}
	
	private void createTestFrame(final int width, final int height, final Orientation orientation) {
		final JFrame frame = new JFrame("AlphaSlider");
		frame.getContentPane().add(contentComponent(orientation));
		frame.setPreferredSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	
	private JComponent contentComponent(final Orientation orientation) {
		final int margin = 10;
		final JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(margin, margin, margin, margin));
		contentPanel.add(alphaSliderComponent(orientation), BorderLayout.CENTER);
		return contentPanel;
	}
	
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
		new AlphaSliderTest(WIDTH, HEIGHT);
		new AlphaSliderTest(HEIGHT, WIDTH);
	}
}
