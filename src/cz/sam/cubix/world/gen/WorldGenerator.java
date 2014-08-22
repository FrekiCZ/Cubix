package cz.sam.cubix.world.gen;

import java.util.Random;


public class WorldGenerator {
	
	private static Random random = new Random();
	
	public WorldGenerator() {
		
	}
	
	public static float Interpolate(float x0, float x1, float alpha) {
		return x0 * (1 - alpha) + alpha * x1;
	}
	
	public static float[][] GenerateWhiteNoise(int width, int height) {
		float[][] noise = new float[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				noise[i][j] = (float) random.nextDouble() % 1;
			}
		}
		return noise;
	}
	
	public static float[][] GenerateSmoothNoise(float[][] baseNoise, int octave) {
		int width = baseNoise.length;
        int height = baseNoise[0].length;
        
        float[][] smoothNoise = new float[width][height];
        
        int samplePeriod = 1 << octave;
        float sampleFrequency = 1.0f / samplePeriod;
        
        for(int i = 0; i < width; i++) {
        	int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % width;
            float horizontal_blend = (i - sample_i0) * sampleFrequency;
            
            for(int j = 0; j < height; j++) {
            	int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
                float vertical_blend = (j - sample_j0) * sampleFrequency;

                float top = Interpolate(baseNoise[sample_i0][sample_j0],
                    baseNoise[sample_i1][sample_j0], horizontal_blend);

                float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
                    baseNoise[sample_i1][sample_j1], horizontal_blend);

                smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);    
            }
        }
        
        return smoothNoise;
	}
	
	public static float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount) {
		int width = baseNoise.length;
        int height = baseNoise[0].length;
        
        float[][][] smoothNoise = new float[octaveCount][][];
        
        float persistance = 0.7f;
        
        for(int i = 0; i < octaveCount; i++) {
        	smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
        }
        
        float[][] perlinNoise = new float[width][height];
        
        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;
        
        for(int octave = octaveCount - 1; octave >= 0; octave--) {
        	amplitude *= persistance;
            totalAmplitude += amplitude;
            
            for(int i = 0; i < width; i++) {
            	for(int j = 0; j < height; j++) {
            		perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
            	}
            }
        }
        
        for(int i = 0; i < width; i++) {
        	for(int j = 0; j < height; j++) {
        		perlinNoise[i][j] /= totalAmplitude;
        	}
        }
        
        return perlinNoise;
	}
	
	public static float[][] generateNoise(int width, int height, int octaveCount) {
		float[][] baseNoise = GenerateWhiteNoise(width, height);
		return GeneratePerlinNoise(baseNoise, octaveCount);
	}
	
}
