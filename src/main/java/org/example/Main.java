package org.example;

import processing.core.PApplet;

public class Main extends PApplet {

    @Override
    public void settings() {
        size(600, 600, P3D);

    }

    @Override
    public void setup() {


    }

    @Override
    public void draw() {
        background(255, 255, 255);
    }

    public static void main(String[] args) {
        String[] appletArgs = new String[]{Main.class.getName()};
        PApplet.main(appletArgs);
    }
}