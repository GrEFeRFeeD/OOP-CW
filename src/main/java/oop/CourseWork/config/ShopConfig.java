package oop.CourseWork.config;

public class ShopConfig {
    private static ShopConfig instance;
    private String city = "City";
    private String address = "Address";
    private Double margin = 0.2;

    private ShopConfig() {}

    private ShopConfig(String city, String address, Double margin) {
        this.city = city;
        this.address = address;
        this.margin = margin;
    }

    public static ShopConfig getInstance() {
        if (instance == null) {
            instance = new ShopConfig();
        }

        return instance;
    }

    public static ShopConfig getInstance(String city, String address, Double margin) {
        if (instance == null) {
            instance = new ShopConfig(city, address, margin);
        }

        return instance;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Double getMargin() {
        return margin;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }
}
