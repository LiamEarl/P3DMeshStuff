package generation;

import java.lang.Math.*;
import noise.FastNoiseLite;

public class TerrainGenerator {

    private float[][] noiseData;

    public TerrainGenerator(int width, int height, int seed) {


        FastNoiseLite noise = new FastNoiseLite();
        noise.SetSeed(1000);
        noise.SetFrequency(0.01f);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);

        noise.SetFractalType(FastNoiseLite.FractalType.FBm);
        noise.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2);
        noise.SetDomainWarpAmp(45f);

        // Gather noise data
        noiseData = new float[width+1][height+1];

        for (int y = 0; y < height+1; y++)
        {
            for (int x = 0; x < width+1; x++)
            {
                noiseData[y][x] = Math.round(Math.abs(noise.GetNoise(x, y)) * 100f) / 100f;
            }
        }
    }

    public float[][] getMapInformation() {
        return noiseData;
    }

}
