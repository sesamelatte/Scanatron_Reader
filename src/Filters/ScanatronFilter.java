package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class ScanatronFilter implements PixelFilter {
    String answers = "ABCDE";
    int startingRow = 110;
    int lastRow = 670;
    int rowInterval = 48;
    int startingCol = 106;
    int lastCol = 225;
    int colInterval = 25;
    int bubbleSize = 17;
    int threshold = 225; //threshold based on calculated data
    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        short[][] newGrid = crop(grid, 0,0,700,700);

        ArrayList<String> allAnswers = new ArrayList<>();
        for (int row = startingRow; row < lastRow; row+=rowInterval) { //looping through question rows
            ArrayList<Integer> answersForRow = new ArrayList<>();
            for (int col = startingCol; col < lastCol; col+= colInterval) { //looping through each question's bubbles
                answersForRow.add(getAverage(newGrid, row, col));
            }
            String rowAnswer = convertAnswer(getAnswer(answersForRow));
            allAnswers.add(rowAnswer);
        }
        System.out.println(allAnswers);

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


    public int getAverage (short[][] grid, int r, int c) { //for one bubble
        int sum = 0;
        for (int row = r; row <= r + bubbleSize; row++) {
            for (int col = c; col <= c + bubbleSize; col++) {
                sum += grid[row][col];
            }
        }
        return sum / (bubbleSize * bubbleSize); //bubbleSize^2 = the total number of pixels for bubble
    }
    public int getAnswer (ArrayList<Integer> answersForRow) {
        for (int i = 0; i < answersForRow.size(); i++) {
            if (answersForRow.get(i) < threshold) {
                return i;
            }
        }
        return -1;
    }
    public String convertAnswer (int answerInteger) { //taking the number answer and turning it into a letter
        String letterAnswer = "";
        for (int i = 0; i < 5; i++) {
            if (answerInteger == i) {
                letterAnswer = answers.substring(i, i + 1);
            }
        }
        return letterAnswer;
        //add check for if answerInteger = -1
    }
//    int bubbleRowCount = 5;
//    int bubbleVertSpacing = 48;
//    int bubbleHoriSpacing = 25;
//
//    String s = "ABCDEFGH";
//
//    @Override
//    public DImage processImage(DImage img) {
//        short[][] grid = img.getBWPixelGrid();
//        // Do stuff with color channels here
//        short[][] newGrid = crop(grid, 0,0,700,700);
//        ArrayList<String> answers = getAnswers(grid, 106, 110, 275, 132);
//        System.out.println(answers);
//        img.setPixels(newGrid);
//        return img;
//    }
//    public short[][] crop(short[][] grid, int r1, int c1, int height, int width) {
//        short[][] newGrid = new short[height][width];
//        for (int row = r1; row < height; row++) {
//            for (int col = c1; col < width; col++) {
//                newGrid[row][col] = grid[row][col];
//            }
//        }
//        return newGrid;
//    }
//    public ArrayList<String> getAnswers(short[][] grid, int x1, int yi1, int x2, int yi2){
//        ArrayList<String> answers = new ArrayList<>();
//
//        for (int y1 = yi1; y1 < grid.length - bubbleVertSpacing; y1 += bubbleVertSpacing){
//            answers.add(getAnswer(grid, x1, y1));
//        }
//
//        for (int y2 = yi2; y2 < grid.length - bubbleVertSpacing; y2 += bubbleVertSpacing){
//            answers.add(getAnswer(grid, x2, y2));
//        }
//
//        return answers;
//    }
//    public String getAnswer(short[][] grid, int x1, int y){
//        double[] bubbleBlackValue = getBubbleAverages(grid, x1, y);
//        double MAX = 0;
//        int MAXINDEX = 0;
//        for (int i = 0; i < bubbleBlackValue.length; i++) {
//            if(bubbleBlackValue[i] > MAX){
//                MAXINDEX = i;
//            }
//        }
//        return s.substring(MAXINDEX, MAXINDEX + 1);
//    }
//    public double[] getBubbleAverages(short[][] grid, int x1, int y){
//        double[] bubbleAverages = new double[bubbleRowCount];
//        for (int i = 0; i < bubbleRowCount; i ++) {
//            int x = x1 + (i * bubbleHoriSpacing);
//            bubbleAverages[i] = getBubbleAverage(grid, x, y, 18);
//        }
//        return bubbleAverages;
//    }
//    public double getBubbleAverage(short[][] grid, int x1, int y1, int bubbleS){
//        int totalBubblePixels = 0;
//        int blackPixels = 0;
//        for (int x = x1; x < x1 + bubbleS; x++) {
//            for (int y = y1; y < y1 + bubbleS; y++) {
//                totalBubblePixels++;
//                if(grid[y][x] == 0){
//                    blackPixels++;
//                }
//            }
//        }
//        return (double) totalBubblePixels/blackPixels;
//    }
}
