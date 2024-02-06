package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class ScanatronFilter implements PixelFilter {
    int bubblesCount, bubbleVertSpacing, bubbleHoriSpacing;

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        // Do stuff with color channels here

        img.setPixels(grid);
        return img;
    }

    public String[] getBubble(short[][] grid){
        for (int j = 0; j < grid.length; j += bubbleVertSpacing){
            for (int i = 0; i < bubblesCount; i+= bubbleHoriSpacing) {
                store isBubbled;
            }
        }
    }

    public Double geta

    public boolean isBubbled(){}
}
