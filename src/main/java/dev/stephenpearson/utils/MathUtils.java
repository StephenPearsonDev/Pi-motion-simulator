package dev.stephenpearson.utils;

public class MathUtils {
	
	
	// Will need this later for making sure user doesnt pan off screen
	public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

}
