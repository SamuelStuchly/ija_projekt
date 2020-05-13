package ija.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

        static final byte VEHICLES_PER_LINE = 1;

        public static void main(String[] args) {
                launch(args);

     
        }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
        System.out.println("test" + getClass().getResource("/Map.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Map.fxml"));
        if (null == loader.getLocation()){
                System.out.println("ZAS JE NULL !!!!!!!!");

        }


       BorderPane root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        MainController controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();


        /*--------------------------------------YAML-------------------------------------------------*/
        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);

        /** Serializacia **/
      //  mapper.writeValue(new File("data.yml"), data);

        String filePathString = "data.yml";
        /** Deserializacia **/
        File f = new File(filePathString);
        if(f.exists() && !f.isDirectory()) { 
            System.out.println("IM ALIVE IM A FILE" );
        }
        else{System.out.println("NOT A FILE PEPEHANDS");}

        Data data1 = mapper.readValue(new File("data.yml"), Data.class);

        List<Line> linesList = data1.getLines();

        // ----- set color to lines -----// 
        linesList.get(0).setLineColor(Color.BLUE);
        linesList.get(1).setLineColor(Color.GREEN);
        linesList.get(2).setLineColor(Color.ORANGE);


        /* list of depart time of buses of the line */
        List<Integer> departTimesList = Arrays.asList( 0, 6, 12, 18, 24, 30, 36, 42, 48, 54);
         
         //list of vehicles
        LocalTime time = LocalTime.now();
         List<Vehicle> vList = new ArrayList<>();
         System.out.println(String.format(" LIST SIZE SHOULD BE 0 - %d",vList.size()));
         for(int i = 0; i < linesList.size() -2 ;i++){
                 for(int j = 0; j < VEHICLES_PER_LINE;j++){
                         try {
                                 //System.out.println(linesList.get(i).getPath());
                                 Vehicle v = new Vehicle("bus" + linesList.get(i).getId() + "_" + String.valueOf(j)  , linesList.get(i), departTimesList.get(j));
                                 vList.add(v);
                         }
                         catch(Exception e){
                                 System.out.println(e + " CHYBA") ;
                         }
                        
                         //System.out.println(String.format("LIST SIZE IN LOOP %d",vList.size()));
                 }
         }

        // for (Vehicle v : vList) {
        //         System.out.println(v);
        //         v.getSchedule().printOutSchedule();
        // }


        
         System.out.println("SIZE " + vList.size());

          if(vList == null){
                  System.out.println("ISNULL");
          }

          for (Vehicle vehicle : vList) {
                  if (vehicle == null){
                          System.out.println("ISNULL yes");
                  }
          }
         //System.out.println("prvy prvok z vlist : " + vList.get(0));

        // TODO : upravit vehicles posititons
         //System.out.println(String.format("%s",data1.getVehicles()));
         try {
                 elements.addAll(vList);
         }
         catch(Exception e){
                 System.out.println("chytil som chybu");
         }


        elements.addAll(data1.getStops());
        elements.addAll(data1.getStreets());

        

        controller.setElements(elements);
        controller.startTimer(1);
  }


        @Override
        public void stop(){
                System.out.println("Stage is closing");
                System.exit(0);
                // Save file
        }

}