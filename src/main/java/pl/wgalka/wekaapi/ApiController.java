package pl.wgalka.wekaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Random;


@RestController
public class ApiController {

    @PostMapping("/addrecord")
    public ResponseEntity<String> addrecord(@RequestParam(required = true) Integer age,
                                            @RequestParam(required = true) String gender,
                                            @RequestParam(required = true) String polyuria,
                                            @RequestParam(required = true) String polydipsia,
                                            @RequestParam(required = true) String sudden_weight_loss,
                                            @RequestParam(required = true) String weakness,
                                            @RequestParam(required = true) String polyphagia,
                                            @RequestParam(required = true) String genital_thrush,
                                            @RequestParam(required = true) String visual_blurring,
                                            @RequestParam(required = true) String itching,
                                            @RequestParam(required = true) String irritability,
                                            @RequestParam(required = true) String delayed_healing,
                                            @RequestParam(required = true) String partial_paresis,
                                            @RequestParam(required = true) String muscle_stiffness,
                                            @RequestParam(required = true) String alopecia,
                                            @RequestParam(required = true) String obesity,
                                            @RequestParam(required = true) String diagnose,
                                            Model model) throws Exception {


        Form form = new Form(age, gender, polyuria, polydipsia, sudden_weight_loss, weakness, polyphagia,
                genital_thrush, visual_blurring, itching, irritability, delayed_healing, partial_paresis,
                muscle_stiffness, alopecia, obesity, diagnose);

        Instances newrecord = form.newInstances2();

        String dataPath = WekaApiApplication.dataPath;

//        InputStream is = this.getClass().getClassLoader().getResourceAsStream(file + "data/diabetes_data_upload_new.arff");
        ConverterUtils.DataSource suorce = new ConverterUtils.DataSource(dataPath);
        Instances data = suorce.getDataSet();
        data.add(newrecord.firstInstance());

        System.out.println("number of instances: " + data.numInstances());


        BufferedWriter writer = new BufferedWriter(new FileWriter(dataPath));
        writer.write(data.toString());
        writer.flush();
        writer.close();

        model.addAttribute("name");

        return ResponseEntity.ok().body("Record added.");

    }

    @PostMapping("/changemodel")
    public ResponseEntity<String> changemodel(@RequestParam(required = true) String newmodelfilename) throws Exception {

        newmodelfilename = WekaApiApplication.newModelPath + newmodelfilename + ".model";
        Classifier tree = (Classifier) weka.core.SerializationHelper.read(newmodelfilename);
        weka.core.SerializationHelper.write(WekaApiApplication.modelPath, tree);
        return ResponseEntity.ok().body("Model changed.");
    }

    @GetMapping("/showdataset")
    public ResponseEntity<String> showdataset() throws Exception {


        String dataPath = WekaApiApplication.dataPath;
        ConverterUtils.DataSource suorce = new ConverterUtils.DataSource(dataPath);
        Instances data = suorce.getDataSet();
        int index = 1;
        StringBuilder result = new StringBuilder("<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <th>index</th>\n" +
                "    <th>age</th>\n" +
                "    <th>gender</th>\n" +
                "    <th>polyuria</th>\n" +
                "    <th>polydipsia</th>\n" +
                "    <th>sudden_weight_loss</th>\n" +
                "    <th>weakness</th>\n" +
                "    <th>polyphagia</th>\n" +
                "    <th>genital_thrush</th>\n" +
                "    <th>visual_blurring</th>\n" +
                "    <th>itching</th>\n" +
                "    <th>irritability</th>\n" +
                "    <th>delayed_healing</th>\n" +
                "    <th>partial_paresis</th>\n" +
                "    <th>muscle_stiffness</th>\n" +
                "    <th>alopecia</th>\n" +
                "    <th>obesity</th>\n" +
                "    <th>diagnose</th>\n" +
                "  </tr>");
        for (Instance ins : data) {
            result.append("<tr>");
            result.append("<td>").append(index).append("</td>");
            for (int i = 0; i < 17; i++) {
                result.append("<td>").append(ins.toString(i)).append("</td>");
            }
            result.append("</tr>");
            index++;
        }
        result.append("</table>");
        return ResponseEntity.ok().body(result.toString());
    }

}
