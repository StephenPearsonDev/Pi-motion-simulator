package dev.stephenpearson.utils.color;

import java.awt.Color;

public class ColorUtils {
	
	//TODO: change strategy for way color scheme is handled
    private static ColorScheme colorScheme = new PastelColorScheme();

    public static void setColorScheme(ColorScheme scheme) {
        colorScheme = scheme;
    }

    public static Color randomFluorescentColor() {
        return colorScheme.generateColor();
    }

    public static Color getComplementaryColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor((hsb[0] + 0.5f) % 1.0f, hsb[1], hsb[2]);
    }

    public static Color blendColors(Color c1, Color c2, float ratio) {
        ratio = Math.max(0, Math.min(1, ratio));
        int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(r, g, b);
    }
}
