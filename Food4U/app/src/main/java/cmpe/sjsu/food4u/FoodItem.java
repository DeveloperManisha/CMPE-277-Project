package cmpe.sjsu.food4u;

/**
 * Created by manas on 5/6/2018.
 */

public class FoodItem {

    private String category;
    private String name;
    private String picture;
    private Double price;
    private Integer calories;
    private Integer time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }


    public FoodItem() {

    }

    public FoodItem(String category,String name, String picture, Double price, Integer calories, Integer time) {
        this.category = category;
        this.picture = picture;
        this.price = price;
        this.calories = calories;
        this.time = time;
        this.name=name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String imageURL) {
        this.picture = imageURL;
    }
}


