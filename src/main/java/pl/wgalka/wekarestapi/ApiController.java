package pl.wgalka.wekarestapi;

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
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Random;


@RestController
public class ApiController {

    @GetMapping("/form")
    public Form greeting(@RequestParam int age, @RequestParam String gender) throws Exception {
        try {
            Classifier tree = (Classifier) weka.core.SerializationHelper.read("models/j48.model");
//            tree.classifyInstance();
        } catch (Exception e) {
            return new Form("wystąpił błąd przy wczytywaniu klasyfikatora:");
        }

        return new Form("xd");
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

        weka.core.SerializationHelper.write("./src/main/resources/models/j48.model", tree);
        return result.toString();
    }
}
