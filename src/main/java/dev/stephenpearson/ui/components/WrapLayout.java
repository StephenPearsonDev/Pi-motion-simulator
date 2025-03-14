package dev.stephenpearson.ui.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;


public class WrapLayout extends FlowLayout {
    public WrapLayout() {
        super();
    }
    
    public WrapLayout(int align) {
        super(align);
    }
    
    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }
    
    @Override
    public Dimension minimumLayoutSize(Container target) {
        return computeSize(target, true);
    }
    
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return computeSize(target, false);
    }
    
    private Dimension computeSize(Container target, boolean minimum) {
        synchronized (target.getTreeLock()) {
            int width = target.getWidth();
            if (width == 0) {
                width = Integer.MAX_VALUE;
            }
            
            Insets insets = target.getInsets();
            int maxWidth = width - (insets.left + insets.right + getHgap() * 2);
            int x = 0, y = insets.top + getVgap();
            int rowHeight = 0;
            int nmembers = target.getComponentCount();
            
            for (int i = 0; i < nmembers; i++) {
                Component c = target.getComponent(i);
                if (!c.isVisible()) continue;
                
                Dimension d = minimum ? c.getMinimumSize() : c.getPreferredSize();
                
                if ((x == 0) || (x + d.width <= maxWidth)) {
                
                    x += d.width + getHgap();
                    rowHeight = Math.max(rowHeight, d.height);
                } else {
                 
                    x = d.width + getHgap();
                    y += rowHeight + getVgap();
                    rowHeight = d.height;
                }
            }
            
            y += rowHeight + getVgap();
            return new Dimension(width, y + insets.bottom);
        }
    }
}