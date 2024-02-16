package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
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

        System.out.println(findAnswersForAPage(newGrid));

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

    public void writeText(ArrayList pageAnswers) throws IOException {
        String out = arrayListToString(pageAnswers);
        writeDataToFile("src/answers.csv", out);
/*        PrintWriter writer = new PrintWriter("answers.txt");
        writer.println("");
        writer.close();*/
    }

    public String arrayListToString(ArrayList<String> L){
        String s = null;
        for(String l: L){
            s += l;
        }
        return s;
    }
    public ArrayList<String> findAnswersForAPage (short[][] newGrid) {
        ArrayList<String> allAnswers = new ArrayList<>();
        for (int row = startingRow; row < lastRow; row+=rowInterval) { //looping through question rows
            ArrayList<Integer> answersForRow = findAnswersForAQuestion(newGrid, row);
            String rowAnswer = convertAnswer(getAnswer(answersForRow));
            allAnswers.add(rowAnswer);
        }
        return allAnswers;
    }
    public ArrayList<Integer> findAnswersForAQuestion (short[][] newGrid, int row) {
        ArrayList<Integer> answersForRow = new ArrayList<>();
        for (int col = startingCol; col < lastCol; col+= colInterval) { //looping through each question's bubbles
            answersForRow.add(getAverage(newGrid, row, col));
        }
        return answersForRow;
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

    public static void writeDataToFile(String filePath, String data) throws IOException {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {

            writer.println(data);

        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }
}