package cz.esgaltur.maxweight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class ChangeWeek {

    /**
     *
     * @param from from which week starting your program
     * @param to   till which week finishing your program
     * @param maxWeight your maximal weight for last times
     */
    public void PrintProgramForAllWeeks(int from, int to,int maxWeight)
    {
        for (int week = from; week <= to; week++) {
            if(week == 5 || week == 6)
                printProgram("Week"+week,"CountW"+week,maxWeight);
                else
            printProgram("Week"+week,"CountW"+week,maxWeight,true);
        }
    }

    /**
     * This function made for generating and print program for 1 to 6 weeks
     * @param week  Which week do you want to print
     * @param count Which week do you want to print
     * @param maxWeight yourmaximal weight for last times
     * @param b boolean to have any functions PrintProgram
     */
    public void printProgram(String week,String count,int maxWeight,boolean b)
    {
        WeightsCounts weightsCounts = new WeightsCounts();
        Boolean aBoolean= true;
        int len = weightsCounts.getWeek(week)[0].length;
        System.out.println("***********"+week+"***********");
        System.out.println("Day 1                  Day 2");
        for (int j = 0; j < len; j++) {
            if (aBoolean.equals(true) ) {
                aBoolean = false;

                System.out.print(weightsCounts.getWeek(week)[0][j] + "% - ");
                System.out.print(calculateProcent(maxWeight, weightsCounts.getWeek(week)[0][j]) + " x ");
                System.out.print(weightsCounts.getCountsOfWeeks(count)[0][j] + "\t");

            }
            if (aBoolean.equals(false)) {
                aBoolean = true;

                System.out.print(" * " + weightsCounts.getWeek(week)[1][j] + "% - ");
                System.out.print(calculateProcent(maxWeight, weightsCounts.getWeek(week)[1][j]) + " x ");
                System.out.print(weightsCounts.getCountsOfWeeks(count)[1][j] + "\n");

            }
        }
        for (int j = 6; j < countOfArr(weightsCounts.getWeek(week), 1); j++) {


            System.out.print("                 * " + weightsCounts.getWeek(week)[1][j] + "% - ");
            System.out.print(calculateProcent(maxWeight, weightsCounts.getWeek(week)[1][j]) + " x ");
            System.out.print(weightsCounts.getCountsOfWeeks(count)[1][j] + "\n");


        }
    }

    /**
     *
     * @param week
     * @param Count
     * @param maxWeight
     */
    public void printProgram(String week,String Count,int maxWeight)
    {
        WeightsCounts weightsCounts = new WeightsCounts();
        int len = weightsCounts.getWeek(week)[0].length;
        boolean aBoolean= true;
        System.out.println("***********"+week+"***********");
        System.out.println("Day 1                  Day 2");
        for (int j = 0; j < len; j++) {
            if (aBoolean == true) {
                aBoolean = false;
                System.out.print(weightsCounts.getWeek(week)[0][j] + "% - ");
                System.out.print(calculateProcent(maxWeight, weightsCounts.getWeek(week)[0][j]) + " x ");
                System.out.print(weightsCounts.getCountsOfWeeks(Count)[0][j] + "\t");
            }
            if (aBoolean == false) {
                aBoolean = true;

                System.out.print(" * " + weightsCounts.getWeek(week)[1][j] + "% - ");
                System.out.print(calculateProcent(maxWeight, weightsCounts.getWeek(week)[1][j]) + " x ");
                System.out.print(weightsCounts.getCountsOfWeeks(Count)[1][j] + "\n");

            }
        }
    }

    /**
     *
     * @throws NumberFormatException
     * @throws IOException
     */
    public void menu() throws NumberFormatException, IOException
    {
        int maxWeight = 0;
        int from = -1;
        int to = -1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true)
           {
               try {
                   System.out.println("******Program of calculate weight of bench press******");
                   System.out.println("Please choose week from 1 to 6:");
                   System.out.println("From: ");
                   from = Integer.parseInt(reader.readLine());
                   System.out.println("To:");
                   to = Integer.parseInt(reader.readLine());
                   if(from<0||from>6||to<0||to>6)
                   {
                       try {
                           throw new Exception("You enter other number.\nFor using this program enter number from 1 to 6");
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
                   if(from == 0 && to == 0)
                   {
                       System.out.println("*****Thanks for using my program****");break;}
                   System.out.println("Enter please your msx weight to bench press");
                   maxWeight = Integer.parseInt(reader.readLine());
                   PrintProgramForAllWeeks(from, to,maxWeight);
               } catch (NumberFormatException e) {
                   System.out.println("You have entered a word or letter or empty string");
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
    }

    /**
     * Function to calculate count of a elements in row in array
     * @param array array to calc
     * @param row which row
     * @return count of elements
     */
    public int countOfArr(int[][] array, int row)
    {
        int count = 0;
        for (int a = 0; a < 1; a++) {
            for (int b = 0; b < array[row].length; b++) {
                count++;
            }
        }
        return count;
    }

    /**
     * Function for calculating of weight
     * @param weight weight to  calculate percent
     * @param percent percent
     * @return how many weight you need
     */
    public double calculateProcent(int weight, int percent)
    {
        return (weight * percent) / 100.0;

    }
}
