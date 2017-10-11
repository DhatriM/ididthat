db = connect("localhost:8080/ididthatdb");
coll = db.users;
coll.insert({    "userName" : "foodie123",
    "email" : "sample@example.com",
    "placeID" : "p583292",
    "checkinID" : "c391303",
    "ratingID" : "r283913"});
coll.insert({    "userName" : "anna",
    "email" : "anna@anna.com",
    "placeID" : "p583294",
    "checkinID" : "c573929",
    "ratingID" : "r858392"});
coll.insert({    "userName" : "chandler",
    "email" : "chandler@bing.com",
    "checkinID" : "c573829",
    "placeID" : "p857382",
    "ratingID" : "r647321"});
coll.insert({    "userName" : "monica",
    "email" : "monica@geller.com",
    "checkinID" : "c858372",
    "placeID" : "p758395",
    "ratingID" : "r637458"});
coll.insert({    "userName" : "ross",
    "email" : "ross@geller.com",
    "checkinID" : "c768492",
    "placeID" : "p128359",
    "ratingID" : "r737289"});
