package ididthat.rest;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.cmu.sv.app17.models.Driver;
import org.bson.Document;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("drivers")
public class DriversInterface {

    MongoCollection<Document> collection;

    public DriversInterface() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("app17-5");

        this.collection = database.getCollection("drivers");
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public ArrayList<Driver> getAll() {

        ArrayList<Driver> driverList = new ArrayList<Driver>();

        FindIterable<Document> results = collection.find();
        if (results == null) {
            return  driverList;
        }
        for (Document item : results) {
            Driver driver = new Driver(
                    item.getString("firstName"),
                    item.getString("lastName"),
                    item.getString("phoneNumber"),
                    item.getString("address1"),
                    item.getString("address2"),
                    item.getString("stateCode"),
                    item.getString("countryCode")
            );
            driver.setId(item.getObjectId("_id").toString());
            driverList.add(driver);
        }
        return driverList;
    }

}
