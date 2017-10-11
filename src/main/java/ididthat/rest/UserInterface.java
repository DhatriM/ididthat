package ididthat.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import ididthat.exceptions.APPBadRequestException;
import ididthat.exceptions.APPInternalServerException;
import ididthat.exceptions.APPNotFoundException;
import ididthat.helpers.PATCH;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import ididthat.models.User;
import ididthat.models.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

@Path("user")
public class UserInterface {

    private MongoCollection<Document> collection = null;
    //private MongoDatabase database;
    private ObjectWriter ow;

    public UserInterface() {
        MongoClient mongoClient = new MongoClient();
        //database = mongoClient.getDatabase("app17-5");
        //collection = database.getCollection("users");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }


    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public ArrayList<User> getAll() {

        ArrayList<User> userList = new ArrayList<User>();

        try {
            FindIterable<Document> results = collection.find();
            if (results == null) {
                throw new APPInternalServerException();
            }
            for (Document item : results) {
                String userName = item.getString("userName");
                User user = new User(
                        userName,
                        item.getString("email"),
                        item.getInteger("placeID"),
                        item.getInteger("checkinID"),
                        item.getInteger("ratingID")
                );
                user.setId(item.getObjectId("_id").toString());
                userList.add(user);
            }
            return userList;

        } catch(Exception e) {
            System.out.println("Cannot get data" + e + "Cannot get data");
            throw new APPInternalServerException();


        }

    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public User getOne(@PathParam("id") String id) {


        BasicDBObject query = new BasicDBObject();

        try {
            query.put("_id", new ObjectId(id));
            Document item = collection.find(query).first();
            if (item == null) {
                throw new APPNotFoundException();
            }
            User user = new User(
                    item.getString("userName"),
                    item.getString("email"),
                    item.getInteger("placeID"),
                    item.getInteger("checkinID"),
                    item.getInteger("ratingID")
            );
            user.setId(item.getObjectId("_id").toString());
            return user;

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
        if (!json.has("userName"))
            throw new APPBadRequestException("missing make","");
        if (!json.has("email"))
            throw new APPBadRequestException("missing model","");
        if (!json.has("placeID"))
            throw new APPBadRequestException("missing color","");
        if (!json.has("checkinID"))
            throw new APPBadRequestException("missing year","");
        if (!json.has("ratingID"))
            throw new APPBadRequestException("missing size","");
            Document doc = new Document("userName", json.getString("userName"))
                    .append("email", json.getString("email"))
                    .append("placeID", json.getString("placeID"))
                    .append("checkID", json.getString("checkID"))
                    .append("ratingID", json.getInt("ratingID"));
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
            if (json.has("userName"))
                doc.append("userName",json.getString("userName"));
            if (json.has("email"))
                doc.append("email",json.getString("email"));
            if (json.has("placeID"))
                doc.append("placeID",json.getString("placeID"));
            if (json.has("checkinID"))
                doc.append("checkID",json.getString("checkinID"));
            if (json.has("ratingID"))
                doc.append("ratingID",json.getInt("ratingID"));
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
