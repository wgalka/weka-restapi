package pl.wgalka.wekarestapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;
import pl.wgalka.wekarestapi.utils.*;

import java.io.File;
import java.util.*;

@Controller
public class MvcController {
    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/form")
    public String form() {
        return "form";
    }

    @GetMapping("/result")
    public String result(@RequestParam(required = true) Integer age,
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
                         Model model) throws Exception {
        Form diagnose = new Form(age, gender, polyuria, polydipsia, sudden_weight_loss, weakness, polyphagia,
                genital_thrush, visual_blurring, itching, irritability, delayed_healing, partial_paresis,
                muscle_stiffness, alopecia, obesity);


        Instances data = diagnose.newInstances();

//            DataSource suorce = new DataSource("data/diabetes_data_upload.arff");
//            Instances data2 = suorce.getDataSet();
//            System.out.println(data.toSummaryString());
//            System.out.println(data2.toSummaryString());

        Classifier tree = (Classifier) weka.core.SerializationHelper.read("src/main/resources/models/j48.model");

        double[] dist = tree.distributionForInstance(data.firstInstance());
        double result = tree.classifyInstance(data.firstInstance());

        diagnose.setProbability(dist[(int) result]);
        String classification = data.attribute("class").value((int) result);
        diagnose.setDiagnose(classification);

        String prob = String.valueOf(dist[(int) result] * 100) + "%";

        model.addAttribute("positiveProb", dist[0]);
        model.addAttribute("negativeProb", dist[1]);

        model.addAttribute("tree", tree.toString());
        model.addAttribute("diagnose", classification);
        model.addAttribute("probability", prob);
        model.addAttribute("form", diagnose.formanswers());

        return "result";
    }

    @GetMapping("/addrecordform")
    public String addrecordform() {
        return "addrecord";
    }


    @GetMapping("/createnewmodel")
    public String createnewmodel(
            @RequestParam(required = false) String pickedobjects,
//            @RequestParam(required = false) String unpurned,
            @RequestParam(required = false) String tresholdPruningC,
            @RequestParam(required = false, defaultValue = "2") String minleafinstancesM,
//            @RequestParam(required = false) String numoffoldsN,
            @RequestParam(required = false) String binarySplits,
//            @RequestParam(required = false) String reducedErrorPruning,
            @RequestParam(required = false) String subtreeRaising,
//            @RequestParam(required = false) String collapsetree,
            Model model) throws Exception {
        ConverterUtils.DataSource suorce = new ConverterUtils.DataSource("data/diabetes_data_upload_new.arff");
        Instances data = suorce.getDataSet(); // our dataset again, obtained from somewhere
        data.setClassIndex(16);
        if (pickedobjects == null) {
            pickedobjects = "1:" + data.numInstances();
        }
        List<Integer> listofobjects = new ArrayList<>(Collections.emptyList());
        try {
            System.out.println(pickedobjects);
            List<String> tokens = new ArrayList<>();
            StringTokenizer tokenizer = new StringTokenizer(pickedobjects, ",");
            while (tokenizer.hasMoreElements()) {
                tokens.add(tokenizer.nextToken());
            }
            System.out.println(tokens);
            for (String token : tokens) {
                try {
                    Integer i = Integer.parseInt(token) - 1;
                    listofobjects.add(i);
                } catch (Exception e) {
                    List<Integer> tokens2 = new ArrayList<>();
                    StringTokenizer tokenizer2 = new StringTokenizer(token, ":");
                    while (tokenizer2.hasMoreElements()) {
                        tokens2.add(Integer.parseInt(tokenizer2.nextToken()));
                    }
                    if (tokens2.size() != 2) {
                        throw new Exception();
                    }
                    for (int j = tokens2.get(0) - 1; j <= tokens2.get(1) - 1; j++) {
                        listofobjects.add(j);
                    }
                }
            }
            System.out.println(listofobjects);
            if (listofobjects.size() < 10) {
                throw new Exception();
            }
        } catch (Exception e) {
            return "help";
        }
        Classifier tree = (Classifier) weka.core.SerializationHelper.read("src/main/resources/models/j48.model");
        Instances mydata = utils.createemptydataset();
        for (Integer x : listofobjects) {
            mydata.add(data.get(x));
        }
        Evaluation evaluation = new Evaluation(mydata);
        evaluation.crossValidateModel(tree, mydata, 10, new Random(1));
        model.addAttribute("tree", tree.toString());
        model.addAttribute("modelacc", evaluation.toSummaryString());
        String optionator = "";
//        if (unpurned != null) {
//            optionator += " "+unpurned;
//        }
        if (tresholdPruningC != null) {
            optionator += " -C " + tresholdPruningC;
        }
        if (minleafinstancesM != null) {
            optionator += " -M " + minleafinstancesM;
        }
//        if (numoffoldsN != null) {
//            optionator += " -R -N " + numoffoldsN;
//        }
        if (binarySplits != null) {
            optionator += " " + binarySplits;
        }
//        if (reducedErrorPruning != null) {
//            optionator += " " + reducedErrorPruning;
//        }
        if (subtreeRaising != null) {
            optionator += " " + subtreeRaising;
        }
//        if (collapsetree != null) {
//            optionator += " "+collapsetree;
//        }
        System.out.println(optionator);
        String[] options = Utils.splitOptions(optionator);
        J48 newtree = new J48();         // new instance of tree
        newtree.setOptions(options);     // set the options
        newtree.buildClassifier(data);   // build classifier

        Evaluation newevaluation = new Evaluation(mydata);
        newevaluation.crossValidateModel(newtree, mydata, 10, new Random(1));

//        System.out.println(newtree.graph());
//        System.out.println(newtree.graphType());

        model.addAttribute("newtree", newtree.toString());
        model.addAttribute("newmodelacc", newevaluation.toSummaryString());

        model.addAttribute("pickedobjects", pickedobjects);

        model.addAttribute("tresholdPruningC", tresholdPruningC);
        model.addAttribute("minleafinstancesM", minleafinstancesM);
//        model.addAttribute("numoffoldsN", numoffoldsN);

        return "createnewmodel";
    }
}
