package controllers;
import services.ImageService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Route;

public class ImageController {


     public static Route saveImage = (req, res) -> {
          res.type("application/json");
          Gson gson = new Gson();
          JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
          try {
               String userID = jsonObj.get("userId").getAsString();
               String fields= jsonObj.get("field").getAsString();
               String image = jsonObj.get("value").getAsString();
               String patente = jsonObj.get("patente").isJsonNull() ? "" : jsonObj.get("patente").getAsString();

              ImageService.saveImage (userID, patente, fields, image);
              res.status(200);
              return "The image has been saved";
          }
          catch (Exception e) {
               res.status(500);
               res.body(e.getMessage());
               return "Something went wrong";
          }
     };


}
