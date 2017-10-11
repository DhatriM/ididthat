package ididthat.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import ididthat.exceptions.APPBadRequestException;
import ididthat.exceptions.APPInternalServerException;
import ididthat.exceptions.APPNotFoundException;
import ididthat.helpers.PATCH;
import ididthat.models.Place;
import ididthat.models.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("places")
public class PlacesInterface {

    private MongoCollection<Document> collection = null;
    private MongoDatabase database;
    private ObjectWriter ow;

    public PlacesInterface() {
        MongoClient mongoClient = new MongoClient();
        database = mongoClient.getDatabase("ididthatdb");
        collection = database.getCollection("places");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }


    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public ArrayList<Place> getAll() {

        ArrayList<Place> placeList = new ArrayList<Place>();

        try {
            FindIterable<Document> results = collection.find();
            if (results == null) {
                throw new APPInternalServerException();
            }
            for (Document item : results) {
                String placeName = item.getString("placeName");
                Place place = new Place(
                        placeName,
                        item.getDouble("PlaceLat"),
                        item.getDouble("PlaceLong"),
                        item.getString("PlaceCategoryType"),
                        item.getInteger("numberCheckins"),
                        item.getDouble("avgRating"),
                        item.getInteger("latestRankingbyCategory")
                );
                place.setId(item.getObjectId("_id").toString());
                placeList.add(place);
            }
            return placeList;

        } catch(Exception e) {
            System.out.println("Cannot get data" + e + "Cannot get data");
            throw new APPInternalServerException();


        }

    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Place getOne(@PathParam("id") String id) {


        BasicDBObject query = new BasicDBObject();

        try {
            query.put("_id", new ObjectId(id));
            Document item = collection.find(query).first();
            if (item == null) {
                throw new APPNotFoundException();
            }
            Place place = new Place(
                    item.getString("PlaceName"),
                    item.getDouble("PlaceLat"),
                    item.getDouble("PlaceLong"),
                    item.getString("PlaceCategoryType"),
                    item.getInteger("numberCheckins"),
                    item.getDouble("avgRating"),
                    item.getInteger("latestRankingbyCategory")
            );
            place.setId(item.getObjectId("_id").toString());
            return place;

        } catch(IllegalArgumentException e) {
            throw new APPBadRequestException("Bad identifier","Doesn't look like MongoDB ID");
        }  catch(Exception e) {
            throw new APPInternalServerException();
        }


    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Object create(Object request) {
        JSONObject json = null;
        try {
            json = new JSONObject(ow.writeValueAsString(request));
        }
        catch (JsonProcessingException e) {
            throw new APPBadRequestException("Format Error", e.getMessage());
        }
        if (!json.has("PlaceName"))
            throw new APPBadRequestException("missing name","");
        if (!json.has("PlaceLat"))
            throw new APPBadRequestException("missing longitude","");
        if (!json.has("PlaceLong"))
            throw new APPBadRequestException("missing latitude","");
        if (!json.has("PlaceCategoryType"))
            throw new APPBadRequestException("missing category","");
        Document doc = new Document("PlaceName", json.getString("PlaceName"))
                .append("PlaceLat", json.getDouble("PlaceLat"))
                .append("PlaceLong", json.getDouble("PlaceLong"))
                .append("PlaceCategoryType", json.getString("PlaceCategoryType"))
                .append("numberCheckins", json.getInt("numberCheckins"))
                .append("avgRating", json.getDouble("avgRating"))
                .append("latestRankingbyCategory", json.getInt("latestRankingbyCategory"));
        collection.insertOne(doc);
        return request;
    }

    @PATCH
    @Path("{id}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Object update(@PathParam("id") String id, Object request) {
        JSONObject json = null;
        try {
            json = new JSONObject(ow.writeValueAsString(request));
        }
        catch (JsonProcessingException e) {
            throw new APPBadRequestException("Format Error", e.getMessage());
        }

        try {

            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));

            Document doc = new Document();
            if (json.has("PlaceName"))
                doc.append("PlaceName", json.getString("PlaceName"));
            if (json.has("PlaceLat"))
                doc.append("PlaceLat", json.getDouble("PlaceLat"));
            if (json.has("PlaceLong"))
                doc.append("PlaceLong", json.getDouble("PlaceLong"));
            if (json.has("PlaceCategoryType"))
                doc.append("PlaceCategoryType", json.getString("PlaceCategoryType"));
            if (json.has("numberCheckins"))
                doc.append("numberCheckins", json.getInt("numberCheckins"));
            if (json.has("avgRating"))
                doc.append("avgRating", json.getDouble("avgRating"));
            if (json.has("latestRankingbyCategory"))
                doc.append("latestRankingbyCategory", json.getInt("latestRankingbyCategory"));
            Document set = new Document("$set", doc);
            collection.updateOne(query,set);

        } catch(JSONException e) {
            System.out.println("Failed to create a document");

        }
        return request;
    }


    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Object delete(@PathParam("id") String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        DeleteResult deleteResult = collection.deleteOne(query);
        if (deleteResult.getDeletedCount() < 1)
            throw new APPNotFoundException("Could not delete","");

        return new JSONObject();
    }
}
