package dev.stephenpearson.ui.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dev.stephenpearson.event.StateChangeEvent;
import dev.stephenpearson.event.StateChangeListener;
import dev.stephenpearson.model.CameraState;
import dev.stephenpearson.model.ColoredSegment;
import dev.stephenpearson.model.SimulationState;

//TODO: Fix antialiasing
public class ScreenPanel extends JPanel implements StateChangeListener {
    private final SimulationState state;
    private Point panStart = null;
    
    public ScreenPanel(SimulationState state) {
        this.state = state;
        state.addStateChangeListener(this);
        
        setBackground(new Color(32, 32, 32));
        setDoubleBuffered(state.isDoubleBufferingEnabled());
        setupInputHandlers();
    }
    
    private void setupInputHandlers() {

        setFocusable(true);
        addMouseWheelListener(this::handleMouseWheel);
        
        MouseAdapter panHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (state.isShowHelp()) return;
                if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0 ||
                    SwingUtilities.isMiddleMouseButton(e)) {
                    panStart = e.getPoint();
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (panStart != null) {
                    int dx = e.getX() - panStart.x;
                    int dy = e.getY() - panStart.y;
                    state.getCamera().pan(dx, dy);
                    panStart = e.getPoint();
                    repaint();
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                panStart = null;
            }
        };
        addMouseListener(panHandler);
        addMouseMotionListener(panHandler);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (state.isShowHelp()) return;
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    state.incrementSpeedLevel();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    state.decrementSpeedLevel();
                }
            }
        });
    }
    
    private void handleMouseWheel(MouseWheelEvent e) {
        if (state.isShowHelp()) return;
        int notches = e.getWheelRotation();
        if (notches == 0) return;
        
        CameraState camera = state.getCamera();
        double oldZoom = camera.getZoom();
        camera.setZoom(oldZoom * (1 - 0.1 * notches));
        
        if (notches > 0) {
           
            camera.setOffsetX(0);
            camera.setOffsetY(0);
        } else {
            adjustZoomOffset(oldZoom);
        }
        repaint();
    }
    
    private void adjustZoomOffset(double oldZoom) {
        CameraState camera = state.getCamera();
        double newZoom = camera.getZoom();
        
        Point2D locked = state.getLockedWorldPoint();
        double oldScreenX = camera.getOffsetX() + locked.getX() * oldZoom;
        double oldScreenY = camera.getOffsetY() + locked.getY() * oldZoom;
        double newScreenX = camera.getOffsetX() + locked.getX() * newZoom;
        double newScreenY = camera.getOffsetY() + locked.getY() * newZoom;
        
        camera.setOffsetX(camera.getOffsetX() + (oldScreenX - newScreenX));
        camera.setOffsetY(camera.getOffsetY() + (oldScreenY - newScreenY));
    }
    
    @Override
    public boolean isDoubleBuffered() {
        return state.isDoubleBufferingEnabled();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(state.getLineThickness()));
        
        int w = getWidth();
        int h = getHeight();

        CameraState camera = state.getCamera();
        double zoom = camera.getZoom();
        
        Point2D locked = state.getLockedWorldPoint();
        double transX = w / 2.0 + camera.getOffsetX() - locked.getX() * zoom;
        double transY = h / 2.0 + camera.getOffsetY() - locked.getY() * zoom;
        g2.translate(transX, transY);

        drawSegments(g2, zoom);

        drawPivotLines(g2, zoom);
    }
    
    private void drawSegments(Graphics2D g2, double zoom) {
        List<ColoredSegment> segments = state.getSegments();
        
        for (ColoredSegment seg : segments) {
            g2.setColor(seg.getColor());
            List<Point2D> pts = seg.getPoints();
            for (int i = 1; i < pts.size(); i++) {
                Point2D p1 = pts.get(i - 1);
                Point2D p2 = pts.get(i);
                int x1 = (int)(p1.getX() * zoom);
                int y1 = (int)(p1.getY() * zoom);
                int x2 = (int)(p2.getX() * zoom);
                int y2 = (int)(p2.getY() * zoom);
                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }
    
    private void drawPivotLines(Graphics2D g2, double zoom) {
        double theta = state.getTheta();

        double x2 = Math.cos(theta);
        double y2 = Math.sin(theta);
        double xOff = Math.cos(Math.PI * theta);
        double yOff = Math.sin(Math.PI * theta);
        double x3 = x2 + xOff;
        double y3 = y2 + yOff;

        x2 *= zoom;
        y2 *= zoom;
        x3 *= zoom;
        y3 *= zoom;

        g2.setColor(Color.WHITE);
        g2.drawLine(0, 0, (int)x2, (int)y2);
        g2.drawLine((int)x2, (int)y2, (int)x3, (int)y3);

        g2.setColor(Color.RED);
        g2.fillOval((int)x2 - 6, (int)y2 - 6, 12, 12);
        
        g2.setColor(Color.YELLOW);
        g2.fillOval((int)x3 - 3, (int)y3 - 3, 6, 6);
    }
    
    @Override
    public void onStateChanged(StateChangeEvent event) {
        if (event.isPropertyChange("doubleBufferingEnabled")) {
            setDoubleBuffered((Boolean) event.getNewValue());
        }
        repaint();
    }
}