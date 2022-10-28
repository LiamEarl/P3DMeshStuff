package org.example;

import processing.opengl.*;

import generation.TerrainGenerator;
import processing.core.PApplet;
public class Main extends PApplet {

    private static final int WORLD_WIDTH = 250;
    private static final int WORLD_HEIGHT = 250;

    private int amplitude = 100;

    private TerrainGenerator generator;
    private float[][] heightMap;

    private int fps = 0;

    private int lastTime = 0;
    private int lastFrame = 0;
    private boolean[] keys = new boolean[222];
    private float[] cameraData;

    private float rotationAngle;
    private float elevationAngle = 300;

    private float centerX;
    private float centerY;
    private float centerZ;

    private int speed = 10;
    @Override
    public void settings() {
        size(1000, 1000, P3D);

    }

    @Override
    public void setup() {
        generator = new TerrainGenerator(WORLD_WIDTH, WORLD_HEIGHT, round(random(1, 10000)));
        heightMap = generator.getMapInformation();
        noStroke();
        cameraData = new float[] {0, 0, 0, 0, 0, 0};

    }
    @Override
    public void keyPressed() {
        keys[keyCode] = true;
    }
    @Override
    public void keyReleased() {
        keys[keyCode] = false;
    }
    @Override
    public void draw() {
        if(frameCount == 1) {
            lastTime = millis();
        }else if(millis() > lastTime + 1000) {
            fps = frameCount - lastFrame;
            lastFrame = frameCount;
            lastTime = millis();
        }

        if(keys[65]) {
            cameraData[0] += cos(radians(cameraData[4])) * speed;
            cameraData[2] += sin(radians(cameraData[4])) * speed;
        }else if(keys[68]) {
            cameraData[0] -= cos(radians(cameraData[4])) * speed;
            cameraData[2] -= sin(radians(cameraData[4])) * speed;
        }

        if(keys[83]) {
            cameraData[0] += cos(radians(cameraData[4]-90)) * speed;
            cameraData[2] += sin(radians(cameraData[4]-90)) * speed;
        }else if(keys[87]) {
            cameraData[0] += cos(radians(cameraData[4]+90)) * speed;
            cameraData[2] += sin(radians(cameraData[4]+90)) * speed;
        }

        if(keys[32]) {
            cameraData[1] += speed;

        }else if(keys[16]) {
            cameraData[1] -= speed;
        }

        if(keys[LEFT]) {
            cameraData[4] -= 5;
        }else if(keys[RIGHT]) {
            cameraData[4] += 5;
        }

        if(keys[UP]) {
            cameraData[3] += 5;
        }else if(keys[DOWN]) {
            cameraData[3] -= 5;
        }
        if(cameraData[4] > 360f) {
            cameraData[4] = 0f;
        }else if(cameraData[4] < 0f) {
            cameraData[4] = 360f;
        }

        rotationAngle = 0;
        elevationAngle = map(cameraData[4], 0, 360, 0, TWO_PI);

        centerX = cos(rotationAngle) * sin(elevationAngle);
        centerY = sin(rotationAngle) * sin(elevationAngle);
        centerZ = -cos(elevationAngle);

        camera(0, 0, 0, centerX, 0, centerZ, 0, 1, 0);

        //pointLight(map(sin(frameCount / 10f), -1, 1, 0, 255), map(tan(frameCount / 10f), -1, 1, 0, 255), map(cos(frameCount / 10f), -1, 1, 0, 255), 0, -500, 0);

        pushMatrix();
        translate(cameraData[0], cameraData[1], cameraData[2]);
        rotateX(radians(cameraData[3]));
        background(50, 75, 150);

        int scaleFactor = (int)(600 / ((WORLD_WIDTH + WORLD_HEIGHT) / 2f));

        for(int y = 0; y < heightMap.length; y++) {

            for(int x = 0; x < heightMap[y].length; x++) {

                if(y >= heightMap.length - 1 || x >= heightMap[y].length - 1) {
                    continue;
                }

                fill(map(heightMap[y][x], 0, 1, 0, 255));

                beginShape();
                vertex(x*scaleFactor, heightMap[y][x] * amplitude, y*scaleFactor);
                vertex((x)*scaleFactor, heightMap[y+1][x] * amplitude, (y+1)*scaleFactor);
                vertex((x+1)*scaleFactor, heightMap[y+1][x+1] * amplitude, (y+1)*scaleFactor);
                vertex((x+1)*scaleFactor, heightMap[y][x+1] * amplitude, y*scaleFactor);
                endShape();


            }

        }

        textSize(40);
        fill(0);
        text("FPS: " + fps, 0, -100, 0);

        popMatrix();
    }

    public static void main(String[] args) {
        String[] appletArgs = new String[]{Main.class.getName()};
        PApplet.main(appletArgs);
    }
}