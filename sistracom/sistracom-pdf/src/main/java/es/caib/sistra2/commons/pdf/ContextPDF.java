package es.caib.sistra2.commons.pdf;

import java.awt.Color;
import java.util.HashMap;

import com.lowagie.text.Font;

import es.caib.sistra2.commons.pdfcaib.FontResourceLoader;

public class ContextPDF {
	
	private HashMap fonts = new HashMap();
	
	private HashMap colors = new HashMap();
	
	
	public Font getDefaultFont()
	{
		return getNotoSansFont(10, Font.NORMAL);
	}

	private Font getNotoSansFont(final float size, final int style) {
		final Font font = FontResourceLoader.getNotoSansFont(size, (style & Font.BOLD) != 0);
		font.setStyle(style);
		return font;
	}
	
	public Font getFont(int baseFont, float size, int style)
	{
		String key = Integer.toString(baseFont) + "_" + Float.toString(size) + "_" + Integer.toString(style);
		if(fonts.containsKey(key))
		{
			return (Font)fonts.get(key);
		}
		Font font = new Font(baseFont, size, style);
		fonts.put(key,font);
		return font;
	}

	public Font getFont(String estilo)
	{
		if(fonts.containsKey(estilo))
		{
			return (Font)fonts.get(estilo);
		}
		return getDefaultFont();
	}
	
	public void initFonts()
	{
		Font font = getNotoSansFont(14, Font.NORMAL);
		fonts.put("Titulo1", font);
		font = getNotoSansFont(10, Font.BOLD);
		fonts.put("Titulo2", font);
		font = getNotoSansFont(10, Font.NORMAL);
		fonts.put("Normal", font);
		font = getNotoSansFont(9, Font.NORMAL);
		fonts.put("Arial9", font);
		font = getNotoSansFont(9, Font.BOLD);
		fonts.put("Arial9Bold", font);
		font = getNotoSansFont(11, Font.NORMAL);
		fonts.put("Arial11", font);
		font = getNotoSansFont(11, Font.BOLD);
		fonts.put("Arial11Bold", font);
		font = getNotoSansFont(8, Font.NORMAL);
		fonts.put("Arial8", font);		
		font = getNotoSansFont(8, Font.BOLD);
		fonts.put("Arial8Bold", font);

		
	}

	public Color getDefaultColor()
	{
		return getColor(0xff,0xff,0xff);
	}
	
	public Color getColor(int r, int g, int b)
	{
		String key = Float.toString(r) + "_" + Float.toString(g) + "_" + Float.toString(b);
		if(colors.containsKey(key))
		{
			return (Color)colors.get(key);
		}
		Color color = new Color(r,g,b);
		colors.put(key,color);
		return color;
	}	

}
