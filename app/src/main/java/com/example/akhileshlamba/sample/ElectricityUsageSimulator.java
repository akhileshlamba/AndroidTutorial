package com.example.akhileshlamba.sample;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by akhileshlamba on 29/3/18.
 */

public class ElectricityUsageSimulator {
    private static double[] fridgeRange = {0.3,0.4,0.5,0.6,0.7,0.8};
    private static double[] acRange = {1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0};
    private static double[] wmRange = {0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3};
    private static final int MAX_AC_TIME = 10;
    private static final int MAX_WM_TIME = 3;
    private static int ac_counter = 0;
    private static int wm_counter = 0;
    private static boolean wm_flag = false;


    private static double fridge_Usage = getFirdgeUsage();
    private static double ac_Usage = getAcUsage();
    private static double wm_Usage = getWmUsage();

    public static double getFirdgeUsage(){
        return fridgeRange[getRandom()];
    }

    public static int getRandom(){
        Random random = new Random();
        return random.nextInt(fridgeRange.length);
    }

    public static double getAcUsage(){
        return acRange[getRandom()];
    }

    public static double getWmUsage(){
        return wmRange[getRandom()];
    }


    public static HashMap<String, Double> setUsage(){
        HashMap<String, Double> usage = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(wm_counter < MAX_WM_TIME && hour < 21 && hour >= 6){
            wm_counter ++;
            usage.put("wm", wm_Usage);
        }else
            usage.put("wm", 0.0);

        if(ac_counter < MAX_AC_TIME && hour < 23 && hour >= 9){
            ac_counter ++;
            usage.put("ac", ac_Usage);
        }else
            usage.put("ac", 0.0);

        usage.put("fridge", fridge_Usage);

        return usage;
    }




}
