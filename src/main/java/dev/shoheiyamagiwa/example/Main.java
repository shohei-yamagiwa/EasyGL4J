package dev.shoheiyamagiwa.example;

import dev.shoheiyamagiwa.easygl4j.opengl.GLWindow;

public class Main {

    public static void main(String[] args) {
        GLWindow window = new GLWindow("Example Window", 1600, 900);
        window.setVisible(true);
        window.run();
    }
}
