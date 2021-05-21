package pl.wgalka.wekarestapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.io.InputStream;
import java.util.Random;


@RestController
public class ApiController {

    @GetMapping("/formapi")
    public Form formapi(@RequestParam(required = true) Integer age,
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
                        @RequestParam(required = true) String obesity) throws Exception {
        Form diagnose = new Form(age, gender, polyuria, polydipsia, sudden_weight_loss, weakness, polyphagia,
                genital_thrush, visual_blurring, itching, irritability, delayed_healing, partial_paresis,
                muscle_stiffness, alopecia, obesity);

        try {
            Instances data = diagnose.newInstances();
//            data.setClassIndex(16);

//            DataSource suorce = new DataSource("data/diabetes_data_upload.arff");
//            Instances data2 = suorce.getDataSet();
//            System.out.println(data.toSummaryString());
//            System.out.println(data2.toSummaryString());

            Classifier tree = (Classifier) weka.core.SerializationHelper.read("src/main/resources/models/j48.model");

            System.out.println(tree.toString());

            double[] dist = tree.distributionForInstance(data.firstInstance());
            double result = tree.classifyInstance(data.firstInstance());

            String classification = data.attribute("class").value((int) result);

            diagnose.setProbability(dist[(int) result]);
            diagnose.setDiagnose(classification);
        } catch (Exception e) {
            diagnose.setDiagnose(e.toString());
            return diagnose;
        }
        return diagnose;
    }


    @GetMapping("/generate")
    public String createmodel() throws Exception {
        int seed = 1;          // the seed for randomizing the data
        int folds = 10;         // the number of folds to generate, >=2
        DataSource suorce = new DataSource("data/diabetes_data_upload.arff");
        Instances data = suorce.getDataSet(); // our dataset again, obtained from somewhere
        System.out.println(data.firstInstance());
        data.setClassIndex(data.numAttributes() - 1);

        // see: randomize the data
        Random rand = new Random(seed);   // create seeded number generator
        Instances randData = new Instances(data);   // create copy of original data
        randData.randomize(rand);         // randomize data with number generator

        StringBuilder result = new StringBuilder();
        // see: generate the folds
        for (int n = 0; n < folds; n++) {
            Instances train = randData.trainCV(folds, n, rand);
            System.out.println("Train: " + train.numInstances());
            Instances test = randData.testCV(folds, n);
            System.out.println("Test: " + test.numInstances());

            // further processing, classification, etc.
            String[] options = new String[1];
            options[0] = "-U";            // unpruned tree
            J48 tree = new J48();         // new instance of tree
            tree.setOptions(options);     // set the options
            tree.buildClassifier(train);   // build classifier

            Evaluation evaluation = new Evaluation(train);
            evaluation.evaluateModel(tree, test);
            System.out.println(evaluation.toSummaryString());
            result.append("Fold: ").append(n);
            result.append(evaluation.toSummaryString()).append("<br>");
        }

        String[] options = new String[1];
        options[0] = "-U";            // unpruned tree
        J48 tree = new J48();         // new instance of tree
        tree.setOptions(options);     // set the options
        tree.buildClassifier(data);   // build classifier

        weka.core.SerializationHelper.write("./src/main/resources/models/newj48.model", tree);
        return result.toString();
    }

    @GetMapping("/addrecord")
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
                                            @RequestParam(required = true) String diagnose) throws Exception {
        Form form = new Form(age, gender, polyuria, polydipsia, sudden_weight_loss, weakness, polyphagia,
                genital_thrush, visual_blurring, itching, irritability, delayed_healing, partial_paresis,
                muscle_stiffness, alopecia, obesity, diagnose);

        Instances newrecord = form.newInstances2();

        DataSource suorce = new DataSource("data/diabetes_data_upload_new.arff");
        Instances data = suorce.getDataSet();
        data.add(newrecord.firstInstance());

        ArffSaver s = new ArffSaver();
        s.setInstances(data);
        s.setFile(new File("/data/diabetes_data_upload_new.arff"));
        s.writeBatch();

        return ResponseEntity.ok().body("Reford added.");
    }
}
