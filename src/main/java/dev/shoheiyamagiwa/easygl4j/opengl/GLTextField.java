package dev.shoheiyamagiwa.easygl4j.opengl;

import dev.shoheiyamagiwa.easygl4j.graphics.Color;
import dev.shoheiyamagiwa.easygl4j.graphics.Graphics;

public class GLTextField extends GLComponent {
    @Override
    public void draw(Graphics g) {
        Color left = new Color(1.0F, 0.0F, 0.0F, 1.0F);
        Color right = new Color(0.0F, 0.0F, 1.0F, 0.1F);
        g.drawHorizontalGradientRect(1000, 100, 200, 200, left, right);
    }

    @Override
    public void dispose() {
    }
}
