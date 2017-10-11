db = connect("localhost:8080/ididthatdb");
coll = db.places;
coll.insert({   "id" : "156",
    "PlaceName" : "Place1",
    "PlaceLat" : 37.4,
    "PlaceLong" : -122.0,
    "PlaceCategoryType" : "Restaurant",
    "numberCheckins" : 0,
    "avgRating" : 2.5,
    "latestRankingbyCategory" : 1});
coll.insert({    "id" : "158",
    "PlaceName" : "Place2",
    "PlaceLat" : 39.4,
    "PlaceLong" : -122.0,
    "PlaceCategoryType" : "Restaurant",
    "numberCheckins" : 0,
    "avgRating" : 2.9,
    "latestRankingbyCategory" : 2});
coll.insert({    "id" : "162",
    "PlaceName" : "Place3",
    "PlaceLat" : 37.4,
    "PlaceLong" : -124.0,
    "PlaceCategoryType" : "Park",
    "numberCheckins" : 1,
    "avgRating" : 3.5,
    "latestRankingbyCategory" : 3});