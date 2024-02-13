package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class ScanatronFilter implements PixelFilter {
    int bubbleRowCount = 5;
    int bubbleVertSpacing = 50;
    int bubbleHoriSpacing = 23;

    String s = "ABCDEFGH";

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        // Do stuff with color channels here
        short[][] newGrid = crop(grid, 0,0,500,500);
        ArrayList<String> answers = getAnswers(grid, 104, 109, 275, 132);

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
    public ArrayList<String> getAnswers(short[][] grid, int x1, int yi1, int x2, int yi2){
        ArrayList<String> answers = new ArrayList<>();

        for (int y1 = yi1; y1 < grid.length - bubbleVertSpacing; y1 += bubbleVertSpacing){
            answers.add(getAnswer(grid, x1, y1));
        }

        for (int y2 = yi2; y2 < grid.length - bubbleVertSpacing; y2 += bubbleVertSpacing){
            answers.add(getAnswer(grid, x2, y2));
        }

        return answers;
    }
    public String getAnswer(short[][] grid, int x1, int y){
        double[] bubbleBlackValue = getBubbleAverages(grid, x1, y);
        double MAX = 0;
        int MAXINDEX = 0;
        for (int i = 0; i < bubbleBlackValue.length; i++) {
            if(bubbleBlackValue[i] > MAX){
                MAXINDEX = i;
            }
        }
        return s.substring(MAXINDEX, MAXINDEX + 1);
    }
    public double[] getBubbleAverages(short[][] grid, int x1, int y){
        double[] bubbleAverages = new double[bubbleRowCount];
        for (int i = 0; i < bubbleRowCount; i ++) {
            int x = x1 + (i * bubbleHoriSpacing);
            bubbleAverages[i] = getBubbleAverages(grid, x, y, 18);
        }
        return bubbleAverages;
    }
    public double getBubbleAverages(short[][] grid, int x1, int y1, int bubbleS){
        int totalBubblePixels = 0;
        int blackPixels = 0;
        for (int x = x1; x < x1 + bubbleS; x++) {
            for (int y = y1; y < y1 + bubbleS; y++) {
                totalBubblePixels++;
                if(grid[y][x] == 0){
                    blackPixels++;
                }
            }
        }
        return (double) totalBubblePixels/blackPixels;
    }
}
