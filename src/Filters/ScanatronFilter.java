package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class ScanatronFilter implements PixelFilter {
    int bubbleRowCount = 5;
    int bubbleVertSpacing = 47;
    int bubbleHoriSpacing = 26;

    String s = "ABCDEFGH";

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        // Do stuff with color channels here
        short[][] newGrid = crop(grid, 0,0,500,500);
       // ArrayList<String> answers = getAnswers(newGrid, 106, 158);

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
        for (int y = y1; y < grid.length; y += bubbleVertSpacing){
            answers.add(getAnswer(grid, x, y));
        }
        return answers;
    }
    public String getAnswer(short[][] grid, int x1, int y){
        double[] bubbleBlackValue = getBubbleAverage(grid, x1, y);
        double MAX = 0;
        int MAXINDEX = 0;
        for (int i = 0; i < bubbleBlackValue.length; i++) {
            if(bubbleBlackValue[i] > MAX){
                MAXINDEX = i;
            }
        }
        return s.substring(MAXINDEX, MAXINDEX + 1);
    }
    public double[] getBubbleAverage(short[][] grid, int x1, int y){
        double[] bubbleAverage = new double[bubbleRowCount];
        for (int i = 0; i < bubbleRowCount; i ++) {
            int x = x1 + (i * bubbleHoriSpacing);
            bubbleAverage[i] = getBubbleAverage(grid, x, y, 19);
        }
        return bubbleAverage;
    }
    public double getBubbleAverage(short[][] grid, int x1, int y1, int bubbleS){
        int totalBubblePixels = 0;
        int blackPixels = 0;
        for (int x = x1; x < x + bubbleS; x++) {
            for (int y = y1; y < y + bubbleS; y++) {
                totalBubblePixels++;
                if(grid[x][y] == 0){
                    blackPixels++;
                }
            }
        }
        return (double) totalBubblePixels/blackPixels;
    }
}
