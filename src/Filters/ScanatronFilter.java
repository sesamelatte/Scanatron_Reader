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

        img.setPixels(grid);
        return img;
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
