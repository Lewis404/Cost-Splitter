/**
 * Created by Lewis on 11/11/2016.
 */
public class Formatting {

    /////////////////////////////////////// Text Formatting ///////////////////////////////////////
    // Used for labels
    public static String formatTotal(String name, double cost){
        return String.format(name + ":\t\t£%.2f", cost);
    }

    public static String formatTotal(double cost){
        return String.format("Total:\t\t£%.2f", cost);
    }
    // Used for listsViews
    public static String formatMoneyToString(double amount){
        return String.format("£%.2f", amount);
    }

    public static double formatMoneyToDouble(String string){
        // should be in format '£0.00'
        string = string.replace('£',' '); // should be in format ' 0.00'
        string = string.trim();
        return Double.parseDouble(string);
    }
}
