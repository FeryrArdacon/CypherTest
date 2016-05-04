/**
 * @author Marv & Feryr
 *
 * changes the Desing of the UI
 */
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.AbstractRegionPainter;

public class LookFeel
{
	private UIDefaults def = UIManager.getLookAndFeelDefaults();
	
	private static LookFeel instance = null;
	
	/**
	 * gets the default UI settings
	 * 
	 * @return the UIDefaults
	 */
	public static UIDefaults getUIDefaults()
	{
		return instance.def;
	}
	
	@SuppressWarnings("static-access")
	public LookFeel()
	{
		this.instance = this;
		setLookAndFeel();
	}
	
	/**
	 * set Nimbus as Look-and-Feel and prepare the default UI settings
	 */
	private void setLookAndFeel()
	{
		// http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html#primary
		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			
			// refelction for getting the PaintContext
			AbstractRegionPainter abstractPainter = (AbstractRegionPainter) UIManager
					.get("ToolTip[Enabled].backgroundPainter");
			Class<?> clazz = abstractPainter.getClass();
			Method protectedMethod = clazz.getDeclaredMethod("getPaintContext");
			protectedMethod.setAccessible(true);
			
			UIDefaults def = UIManager.getLookAndFeelDefaults();
			
			// defaults Button
			def.put("Button.background", new Color(50, 50, 50));
			def.put("Button.font", new Font("Tahoma", Font.BOLD, 12));
			def.put("Button.textForeground", Color.WHITE);
			
			// defaults ComboBox
			def.put("ComboBox.font", new Font("Tahoma", Font.BOLD, 12));
			
			// defaults ProgressBar
			def.put("ProgressBar.font", new Font("Tahoma", Font.BOLD, 12));
			
			// defaults ToolTip
			def.put("ToolTip.textForeground", Color.WHITE);
			def.put("ToolTip[Enabled].backgroundPainter", new MyTooltipPainter(
					protectedMethod.invoke(abstractPainter), 1));
			// UIManager.put("info", new Color(90, 90, 90)); geht auch,
			// ich wei nur nicht, ob das irgendwelche Nebenwirkungen auf andere
			// Bereiche hat
			
			UIManager.put("control", Color.WHITE);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Feryr
	 *
	 *         creates a new TooltipPainter with a dark background theme
	 */
	private final class MyTooltipPainter extends AbstractRegionPainter
	{
		// package private integers representing the available states that
		// this painter will paint. These are used when creating a new instance
		// of ToolTipPainter to determine which region/state is being painted
		// by that instance.
		static final int BACKGROUND_ENABLED = 1;
		
		private int state; // refers to one of the static final ints above
		private PaintContext ctx;
		
		// the following 4 variables are reused during the painting code of the
		// layers
		@SuppressWarnings("unused")
		private Path2D path = new Path2D.Float();
		private Rectangle2D rect = new Rectangle2D.Float(0, 0, 0, 0);
		@SuppressWarnings("unused")
		private RoundRectangle2D roundRect = new RoundRectangle2D.Float(0, 0,
				0, 0, 0, 0);
		@SuppressWarnings("unused")
		private Ellipse2D ellipse = new Ellipse2D.Float(0, 0, 0, 0);
		
		// All Colors used for painting are stored here. Ideally, only those
		// colors being used
		// by a particular instance of ToolTipPainter would be created. For the
		// moment at least,
		// however, all are created for each instance.
		private Color color1 = decodeColor("nimbusBorder", 0.0f, 0.0f, 0.0f, 0);
		private Color color2 = decodeColor("info", -0.8f, -0.8f, -0.8f, 0);
		
		// Array of current component colors, updated in each paint call
		@SuppressWarnings("unused")
		private Object[] componentColors;
		
		public MyTooltipPainter(Object ctx, int state)
		{
			super();
			this.state = state;
			this.ctx = (PaintContext) ctx;
		}
		
		@Override
		protected void doPaint(Graphics2D g, JComponent c, int width,
				int height, Object[] extendedCacheKeys)
		{
			// populate componentColors array with colors calculated in
			// getExtendedCacheKeys call
			componentColors = extendedCacheKeys;
			// generate this entire method. Each state/bg/fg/border combo that
			// has
			// been painted gets its own KEY and paint method.
			switch (state)
			{
			case BACKGROUND_ENABLED:
				paintBackgroundEnabled(g);
				break;
			
			}
		}
		
		@Override
		protected final PaintContext getPaintContext()
		{
			return ctx;
		}
		
		private void paintBackgroundEnabled(Graphics2D g)
		{
			rect = decodeRect1();
			g.setPaint(color1);
			g.fill(rect);
			rect = decodeRect2();
			g.setPaint(color1);
			g.fill(rect);
			rect = decodeRect3();
			g.setPaint(color1);
			g.fill(rect);
			rect = decodeRect4();
			g.setPaint(color1);
			g.fill(rect);
			rect = decodeRect5();
			g.setPaint(color2);
			g.fill(rect);
			
		}
		
		private Rectangle2D decodeRect1()
		{
			rect.setRect(decodeX(2.0f), // x
					decodeY(1.0f), // y
					decodeX(3.0f) - decodeX(2.0f), // width
					decodeY(2.0f) - decodeY(1.0f)); // height
			return rect;
		}
		
		private Rectangle2D decodeRect2()
		{
			rect.setRect(decodeX(0.0f), // x
					decodeY(1.0f), // y
					decodeX(1.0f) - decodeX(0.0f), // width
					decodeY(2.0f) - decodeY(1.0f)); // height
			return rect;
		}
		
		private Rectangle2D decodeRect3()
		{
			rect.setRect(decodeX(0.0f), // x
					decodeY(2.0f), // y
					decodeX(3.0f) - decodeX(0.0f), // width
					decodeY(3.0f) - decodeY(2.0f)); // height
			return rect;
		}
		
		private Rectangle2D decodeRect4()
		{
			rect.setRect(decodeX(0.0f), // x
					decodeY(0.0f), // y
					decodeX(3.0f) - decodeX(0.0f), // width
					decodeY(1.0f) - decodeY(0.0f)); // height
			return rect;
		}
		
		private Rectangle2D decodeRect5()
		{
			rect.setRect(decodeX(1.0f), // x
					decodeY(1.0f), // y
					decodeX(2.0f) - decodeX(1.0f), // width
					decodeY(2.0f) - decodeY(1.0f)); // height
			return rect;
		}
	}
}