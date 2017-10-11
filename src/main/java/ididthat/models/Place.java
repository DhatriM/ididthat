package ididthat.models;

public class Place {

    String id = null;
    String PlaceName;
    Double PlaceLat;
    Double PlaceLong;
    String PlaceCategoryType;
    Integer numberCheckins;
    Double avgRating;
    Integer latestRankingbyCategory;

    public Place(String PlaceName, Double PlaceLat, Double PlaceLong, String PlaceCategoryType, Integer numberCheckins, Double avgRating,Integer latestRankingbyCategory) {
        this.PlaceName = PlaceName;
        this.PlaceLat = PlaceLat;
        this.PlaceLong = PlaceLong;
        this.PlaceCategoryType = PlaceCategoryType;
        this.numberCheckins = numberCheckins;
        this.avgRating = avgRating;
        this.latestRankingbyCategory = latestRankingbyCategory;
    }

    public void setId(String id) {
        this.id = id;
    }
}
