package cz.esgaltur.maxweight;


import java.util.HashMap;
import java.util.Map;

/**
 * Class of weights and counts to do in Bench Press
 */
public class WeightsCounts {
    /**
     * @param weeks - Map to save procents of  weights
     */
    private Map<String,int[][]> weeks = new HashMap<String,int[][]>();
    /**
     * @param countsOfWeeks - map to save counts to doin Bench Press
     */

    private Map<String,int[][]> countsOfWeeks = new HashMap<String,int[][]>();

    /**
     * Constructor does not have any parameters
     */
    public WeightsCounts()
        {
            weeks.put("Week1",
                new int[][]{{45, 55, 65, 70, 70, 70},
                {45, 55, 65, 75, 80, 80, 80, 75, 65, 55}});
            weeks.put("Week2",
                new int[][]{ {45, 55, 65, 70, 75, 75},
                        {45, 55, 65, 75, 80, 85, 85, 85, 80, 70}});
            weeks.put("Week3",
                new int[][]{{45, 55, 70, 75, 80, 85},
                        {45, 55, 65, 75, 85, 90, 90, 80, 60}});
            weeks.put("Week4",
                new int[][]{ {45, 55, 65, 75, 85, 85},
                        {45, 55, 65, 75, 85, 90, 95, 75}});
            weeks.put("Week5",
                new int[][]{{45, 55, 65, 75, 75},
                        {45, 55, 65, 75, 80, 85}});
            weeks.put("Week6",
                new int[][]{{45, 55, 65, 75, 85},
                        {45, 55, 65, 75, 85}});
            countsOfWeeks.put("CountW1",
                new int[][]{{10, 8, 6, 6, 6, 6},
                        {10, 8, 5, 5, 5, 5, 5, 5, 8, 12}});
            countsOfWeeks.put("CountW2",
                new int[][]{{10, 8, 6, 5, 5, 5},
                        {10, 8, 5, 4, 4, 4, 4, 4, 5, 8}});
            countsOfWeeks.put("CountW3",
                new int[][]{  {10, 8, 4, 3, 3, 3},
                        {10, 8, 5, 4, 3, 3, 3, 5, 10}});
            countsOfWeeks.put("CountW4",
                new int[][]{{10, 8, 5, 4, 3, 3},
                        {10, 8, 5, 4, 2, 2, 2, 6}});
            countsOfWeeks.put("CountW5",
                new int[][]{{10, 8, 5, 5, 5, 5},
                        {10, 8, 5, 3, 3, 2}});
            countsOfWeeks.put("CountW6",
                new int[][]{  {10, 8, 5, 3, 2},
                        {10, 8, 5, 3, 2}});
    }

    /**
     *
     * @param numberOfWeek Get week with name Week1 - 6
     * @return array of procents of weights to do in Bench Press
     */
    public int[][] getWeek(String numberOfWeek)
    {
     return weeks.get(numberOfWeek);
    }

    /**
     *
     * @param counts Get count with name CountW1 - 6
     * @return array of count of weights to do in Bench Press
     */
    public int[][] getCountsOfWeeks(String counts)
    {
     return countsOfWeeks.get(counts);
    }


}


