package dev.stephenpearson.utils;

import java.awt.Color;

//eventually line will change color to add a nice effect
public class ColorUtils {
	
	public static Color randomFluorescentColor() {
        float hue = (float) Math.random();
        float saturation = 0.9f + 0.1f * (float) Math.random();
        float brightness = 0.9f + 0.1f * (float) Math.random();
        return Color.getHSBColor(hue, saturation, brightness);
    }

}
