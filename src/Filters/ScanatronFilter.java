package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class ScanatronFilter implements PixelFilter {
    int bubbleRowCount = 5;
    int bubbleVertSpacing = 50;
    int bubbleHoriSpacing;

    String s = "ABCDEFGH";

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        // Do stuff with color channels here
        short[][] newGrid = crop(grid, 0,0,500,500);
        img.setPixels(newGrid);
        return img;
    }
    public short[][] crop(short[][] grid, int r1, int c1, int height, int width) {
        short[][] newGrid = new short[height][width];
        for (int row = r1; row < height; row++) {
            for (int col = c1; col < width; col++) {
                newGrid[row][col] = grid[row][col];
            }
        }
        return newGrid;
    }
    public ArrayList<String> getAnswers(short[][] grid){
        ArrayList<String> answers = new ArrayList<>();
        for (int j = 0; j < grid.length; j += bubbleVertSpacing){
            double[] bubblesBlackAverage = storeBubblesBlackAverage(grid);
        }
        return answers;
    }

    public double[] storeBubblesBlackAverage(short[][] grid){
        double[] bubblesBlackAverage = new double[0];
        for (int i = 0; i < bubbleRowCount; i += bubbleHoriSpacing) {

        }
        return bubblesBlackAverage;
    }
}
