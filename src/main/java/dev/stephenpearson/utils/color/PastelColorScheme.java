package dev.stephenpearson.utils.color;

import java.awt.Color;
import java.util.Random;

public class PastelColorScheme implements ColorScheme {
    private static final Random RANDOM = new Random();

    @Override
    public Color generateColor() {
        float hue = RANDOM.nextFloat();
        float saturation = 0.4f + (0.3f * RANDOM.nextFloat()); 
        float brightness = 0.8f + (0.2f * RANDOM.nextFloat()); 
        return Color.getHSBColor(hue, saturation, brightness);
    }
}
