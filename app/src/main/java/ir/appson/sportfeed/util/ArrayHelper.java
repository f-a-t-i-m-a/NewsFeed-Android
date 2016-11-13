package ir.appson.sportfeed.util;

import java.util.ArrayList;
import java.util.Iterator;

import ir.appson.sportfeed.dto.NewsDetail;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;

/**
 * Created by fatemeh on 9/14/2015.
 */
public class ArrayHelper {
    public static int[] extractIds(ArrayList<NewsDetailObject> newsDetailObjects) {
        int[] result = new int[newsDetailObjects.size()];
        int i = 0;
        for (NewsDetailObject newsDetailObject : newsDetailObjects) {
            result[i] = newsDetailObjects.get(i).getNewsId();
            i++;
        }
        return result;
    }

    public static int[] extractIds2(ArrayList<NewsDetail> newsDetailObjects) {
        int[] result = new int[newsDetailObjects.size()];
        int i = 0;
        for (NewsDetail newsDetailObject : newsDetailObjects) {
            result[i] = newsDetailObjects.get(i).ID;
            i++;
        }
        return result;
    }

    public static int positionInArray(int[] array, int wanted) {
        int i = 0;
        for (int iterator : array) {
            if (wanted == iterator)
                return i;
            i++;
        }
        return -1;
    }

    public static int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
}
