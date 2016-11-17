package com.khoonat.news.util;

import java.util.ArrayList;

import com.khoonat.news.dto.ChannelNEWSObject;

/**
 * Created by fatemeh on 9/14/2015.
 */
public class ArrayHelper {

    public static int[] extractIDs(ArrayList<ChannelNEWSObject> newsDetailObjects) {
        int[] result = new int[newsDetailObjects.size()];
        for (int i = 0; i < newsDetailObjects.size(); i++) {
            result[i] = newsDetailObjects.get(i).ID;
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
}
