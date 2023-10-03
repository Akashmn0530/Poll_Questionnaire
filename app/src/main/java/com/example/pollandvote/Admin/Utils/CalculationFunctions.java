package com.example.pollandvote.Admin.Utils;


import java.text.DecimalFormat;

public class CalculationFunctions {
    public static String calculateVotePercent(double totalVoteCount,double Count){

        double result = 0.00;
        if(totalVoteCount<=0){
            return 0+"";
        }
        result = (Count *100) / totalVoteCount;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(result);
//        return Math.round(result * 100.0) / 100.0;
    }
}
