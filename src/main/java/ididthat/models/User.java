package ididthat.models;

public class User {

    String id = null;
    String userName, email, placeID, checkinID, ratingID;

    public User(String userName, String email, String placeID, String checkinID,
                String ratingID) {
        this.email = email;
        this.userName = userName;
        this.placeID = placeID;
        this.checkinID = checkinID;
        this.ratingID = ratingID;
    }

    public void setId(String id) {
        this.id = id;
    }
}
