package dev.shoheiyamagiwa.example;

import dev.shoheiyamagiwa.easygl4j.opengl.GLButton;
import dev.shoheiyamagiwa.easygl4j.opengl.GLTextField;
import dev.shoheiyamagiwa.easygl4j.opengl.GLWindow;

public class Main {

    public static void main(String[] args) {
        GLWindow window = new GLWindow("Example Window", 1920, 1080);
        window.setVisible(true);
        window.setFullscreen(true);
        window.add(new GLButton());
        window.add(new GLTextField());
        window.run();
    }
}
