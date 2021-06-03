package pl.wgalka.wekaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WekaApiApplication {
//    public static String dataPath = "src/main/resources/data/diabetes_data_upload_new.arff";
//    public static String modelPath = "C:/WekaApp/models/j48.model";
//    public static String newModelPath = "C:/WekaApp/models/";

//    Jar paths
    public static String dataPath = "C:/WekaApp/data/diabetes_data_upload.arff";
    public static String newDataPath = "C:/WekaApp/data/diabetes_data_upload_new.arff";
    public static String modelPath = "C:/WekaApp/models/original/j48.model";
    public static String newModelPath = "C:/WekaApp/models/";

    public static void main(String[] args) {
        SpringApplication.run(WekaApiApplication.class, args);
    }

}
