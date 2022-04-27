package myschedulingprogram.graphics;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollbar  extends BasicScrollBarUI {
	private Color myCustomColor;
	protected JButton createZeroButton() {
	    JButton button = new JButton("");
	    Dimension zeroDim = new Dimension(0,0);
	    button.setPreferredSize(zeroDim);
	    button.setMinimumSize(zeroDim);
	    button.setMaximumSize(zeroDim);
	    return button;
	}
	
	public CustomScrollbar(Color c) {
		myCustomColor = c;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
	    return createZeroButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
	    return createZeroButton();
	}
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = myCustomColor;
        this.thumbDarkShadowColor = myCustomColor;
        this.thumbLightShadowColor = myCustomColor;
        this.scrollBarWidth = 10;
    }


}
