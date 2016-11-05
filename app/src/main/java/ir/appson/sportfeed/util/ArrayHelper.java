package ir.appson.sportfeed.util;

import ir.appson.sportfeed.proxy.dto.NewsDetailObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by fatemeh on 9/14/2015.
 */
public class ArrayHelper {
    public static int[] extractIds(ArrayList<NewsDetailObject> newsDetailObjects){
        int[] result = new int[newsDetailObjects.size()];
        int i=0;
        for (NewsDetailObject newsDetailObject: newsDetailObjects){
            result[i] = newsDetailObjects.get(i).getNewsId();
            i++;
        }
        return result;
    }
    public static int positionInArray(int[] array, int wanted){
        int i=0;
        for (int iterator: array){
            if (wanted == iterator)
                return i;
            i++;
        }
        return -1;
    }
    public static int[] convertIntegers(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
}
