import lombok.*;

/**
 * Created by Lewis on 10/11/2016.
 */
public class Person {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private double totalCost;

    public Person(String name, double cost) {
        this.setName(name);
        this.setTotalCost(cost);
    }

//    public double getTotalCost() {
//        return totalCost;
//    }
//
//    public void setTotalCost(double totalCost) {
//        this.totalCost = totalCost;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public void addCost(double cost){
        this.totalCost += cost;
    }

    public void subtractCost(double cost){
        this.totalCost -= cost;
    }
}
