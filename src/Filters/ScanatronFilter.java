package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class ScanatronFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        // Do stuff with color channels here

        img.setPixels(grid);
        return img;
    }

    public Integer[][] getBubbled(){
        if(bubbleRowExists()){
            for (int i = 0; i < bubblespacing; i++) {
                store isBubbled;
            }
        }
    }

    public boolean bubbleRowExists(){

    }

    public boolean isBubbled(){}
}
