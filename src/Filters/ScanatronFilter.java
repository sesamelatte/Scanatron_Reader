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
    public ArrayList<String> getAnswers(short[][] grid, int x, int y1){
        ArrayList<String> answers = new ArrayList<>();
        for (int y = 0; y < grid.length; y += bubbleVertSpacing){
            answers.add(getAnswer(grid, x, y1 + y));
        }
        return answers;
    }

    public double[] getBubbleBlackValue(short[][] grid, int x1, int y){
        double[] bubbleBlackValue = new double[bubbleRowCount];
        for (int i = 0; i < bubbleRowCount; i ++) {
            int x = x1 + (i * bubbleHoriSpacing);
            bubbleBlackValue[i] = getBubbleBlack(grid, x, y, 20);
        }
        return bubbleBlackValue;
    }

    public String getAnswer(short[][] grid, int x1, int y){
        double[] bubbleBlackValue = getBubbleBlackValue(grid, x1, y);
        double MAX = 0;
        int MAXINDEX = 0;
        for (int i = 0; i < bubbleBlackValue.length; i++) {
            if(bubbleBlackValue[i] > MAX){
                MAXINDEX = i;
            }
        }
        return s.substring(MAXINDEX, MAXINDEX + 1);
    }
    public Double getBubbleBlack(short[][] grid, int x, int y, int bubbleS){
        //thresehold
    }
}
