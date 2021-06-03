package pl.wgalka.wekaapi;

import org.apache.commons.math3.util.Precision;
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
import weka.core.converters.ConverterUtils;
import pl.wgalka.wekaapi.utils.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.io.FilenameUtils.removeExtension;

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
        data.setClassIndex(16);

        //            DataSource suorce = new DataSource("data/diabetes_data_upload.arff");
        //            Instances data2 = suorce.getDataSet();
        //            System.out.println(data.toSummaryString());
        //            System.out.println(data2.toSummaryString());

        J48 tree = (J48) weka.core.SerializationHelper.read(WekaApiApplication.modelPath);
        double[] dist = tree.distributionForInstance(data.firstInstance());
        double result = tree.classifyInstance(data.firstInstance());

        diagnose.setProbability(dist[(int) result]);
        String classification = data.attribute("class").value((int) result);
        diagnose.setDiagnose(classification);

        double distvalue = Precision.round(dist[(int) result] * 100, 3);
//        System.out.println(distvalue);
        String prob = distvalue + "%";
//        System.out.println("xd" + prob);

        model.addAttribute("positiveProb", Precision.round(dist[0], 3));
        model.addAttribute("negativeProb", Precision.round(dist[1], 3));

        model.addAttribute("tree", tree.toString());
        model.addAttribute("diagnose", classification);
        model.addAttribute("probability", prob);
        model.addAttribute("form", diagnose.formanswers());


        String graph = tree.graph();

        String output = "Tree track: ";
        String[] lines = graph.split("\n");
        String curentLabel = "";
        String curentValue = "";
        String curentNode = "N0";

        System.out.println(tree.graph());
        boolean node = true;
        for (String line : lines) {
            if (node) {
                Pattern compiledPattern = Pattern.compile("(N[0-9]+) .*label=\"(.*)\" (.*)]");
                Matcher matcher = compiledPattern.matcher(line);
                if (matcher.matches()) {
                    if (matcher.group(1).equals(curentNode)) {
                        if (matcher.group(3).equals("")) {
                            curentLabel = matcher.group(2);
                            int xd = data.attribute(curentLabel).index();
                            curentValue = data.firstInstance().toString(xd);
                            output += curentLabel + " " + curentValue;
                            System.out.println(xd + " " + curentNode + " " + curentLabel + " " + curentValue);
                            node = false;
                        } else {
                            output += matcher.group(2);
                            break;
                        }
                    }
                }
            } else {
                Pattern compiledPattern = Pattern.compile("(N[0-9]+)->(N[0-9]+).*label=\"([=><]{1,2}) (.*)\".*");
                Matcher matcher = compiledPattern.matcher(line);
                if (matcher.matches()) {
                    if (matcher.group(1).equals(curentNode)) {
                        if (curentLabel.equals("Age")) {
                            int userage = Integer.parseInt(matcher.group(4));
                            int treeval = Integer.parseInt(matcher.group(4));

                            output += matcher.group(3) + treeval + " > ";
                            if (matcher.group(3).equals("=")) {
                                if (userage == treeval) {
                                    curentNode = matcher.group(2);
                                    node = true;
                                }
                            } else if (matcher.group(3).equals(">")) {
                                if (userage > treeval) {
                                    curentNode = matcher.group(2);
                                    node = true;
                                }
                            } else if (matcher.group(3).equals("<")) {
                                if (userage < treeval) {
                                    curentNode = matcher.group(2);
                                    node = true;
                                }
                            } else if (matcher.group(3).equals(">=")) {
                                if (userage >= treeval) {
                                    curentNode = matcher.group(2);
                                    node = true;
                                }
                            } else if (matcher.group(3).equals("<=")) {
                                if (userage <= treeval) {
                                    curentNode = matcher.group(2);
                                    node = true;
                                }
                            }
                        } else if (matcher.group(4).equals(curentValue)) {
                            System.out.println(line);
                            curentNode = matcher.group(2);
                            node = true;
                            output += " > ";
                        }
                    }
                }
            }
        }
        System.out.println(output);
        model.addAttribute("treetrack", output);
        return "result";
    }

    @GetMapping("/addrecordform")
    public String addrecordform() {
        return "addrecord";
    }


    @GetMapping("/createnewmodel")
    public String createnewmodel(
            @RequestParam(name = "useall", required = false) String useall,
            @RequestParam(name = "pickedobjects", required = false) String pickedobjects,
            @RequestParam(name = "pickedobjectsnew", required = false) String pickedobjectsnew,
//            @RequestParam(required = false) String unpurned,
            @RequestParam(name = "tresholdPruningC", required = false) String tresholdPruningC,
            @RequestParam(name = "minleafinstancesM", required = false, defaultValue = "2") String
                    minleafinstancesM,
//            @RequestParam(required = false) String numoffoldsN,
            @RequestParam(name = "binarySplits", required = false) String binarySplits,
//            @RequestParam(required = false) String reducedErrorPruning,
            @RequestParam(name = "subtreeRaising", required = false) String subtreeRaising,
            @RequestParam(name = "filename", required = true, defaultValue = "newJ48") String filename,
//            @RequestParam(required = false) String collapsetree,
            Model model) throws Exception {

        model.addAttribute("filename", filename);
        filename = filename + ".model";

        ConverterUtils.DataSource suorce = new ConverterUtils.DataSource(WekaApiApplication.dataPath);
        Instances data = suorce.getDataSet(); // our dataset again, obtained from somewhere
        data.setClassIndex(16);

        ConverterUtils.DataSource suorce2 = new ConverterUtils.DataSource(WekaApiApplication.newDataPath);
        Instances data2 = suorce2.getDataSet(); // our dataset again, obtained from somewhere
        data2.setClassIndex(16);

        System.out.println("USEALL= " + useall);

        if (pickedobjects == null) {
            pickedobjects = "1:" + data.numInstances();
        }
        if (pickedobjectsnew == null) {
            pickedobjectsnew = "1:" + data2.numInstances();
        }
        if (useall == null) {
            useall = "off";
        }

        Instances mydata = utils.createemptydataset();
        if (useall.equals("on")) {
            for (int i = 0; i < data2.numInstances(); i++) {
                data.add(data2.get(i));
            }
            mydata = data;
        } else {
            try {
                if (!pickedobjects.equals("0")) {
                    List<Integer> listofobjects1 = utils.stringtolist(pickedobjects);
                    for (Integer x : listofobjects1) {
                        mydata.add(data.get(x));
                    }
                }
                if (!pickedobjectsnew.equals("0")) {
                    List<Integer> listofobjects2 = utils.stringtolist(pickedobjectsnew);
                    for (Integer x : listofobjects2) {
                        mydata.add(data2.get(x));
                    }
                }
                if (mydata.numInstances() < 10) {
                    throw new Exception();
                }
            } catch (Exception e) {
                return "help";
            }
        }

        Classifier tree = (Classifier) weka.core.SerializationHelper.read(WekaApiApplication.modelPath);

        System.out.println(mydata.numInstances());
        Evaluation evaluation = new Evaluation(mydata);
        evaluation.crossValidateModel(tree, mydata, 10, new Random(1));
        model.addAttribute("tree", tree.toString());
        model.addAttribute("modelacc", evaluation.toSummaryString());

        model.addAttribute("ev1TP", evaluation.numTruePositives(0));
        model.addAttribute("ev1FP", evaluation.numFalsePositives(0));
        model.addAttribute("ev1TN", evaluation.numTrueNegatives(0));
        model.addAttribute("ev1FN", evaluation.numFalseNegatives(0));
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
        newevaluation.crossValidateModel(newtree, mydata, 10, new

                Random(1));

        weka.core.SerializationHelper.write(WekaApiApplication.newModelPath + filename, newtree);

//        System.out.println(newtree.graph());
//        System.out.println(newtree.graphType());

        File folder = new File(WekaApiApplication.newModelPath);
        File[] listOfFiles = folder.listFiles();

        StringBuilder newmodelnames = new StringBuilder();
        for (
                File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                newmodelnames.append(removeExtension(listOfFile.getName())).append(", ");
//                System.out.println("File " + listOfFile.getName());
            }
        }

        model.addAttribute("listofmodels", newmodelnames);

        model.addAttribute("newtree", newtree.toString());
        model.addAttribute("newmodelacc", newevaluation.toSummaryString());

        model.addAttribute("ev2TP", newevaluation.numTruePositives(0));
        model.addAttribute("ev2FP", newevaluation.numFalsePositives(0));
        model.addAttribute("ev2TN", newevaluation.numTrueNegatives(0));
        model.addAttribute("ev2FN", newevaluation.numFalseNegatives(0));

        model.addAttribute("pickedobjects", pickedobjects);
        model.addAttribute("pickedobjectsnew", pickedobjectsnew);

        model.addAttribute("tresholdPruningC", tresholdPruningC);
        model.addAttribute("minleafinstancesM", minleafinstancesM);
        model.addAttribute("useall", useall);
//        model.addAttribute("numoffoldsN", numoffoldsN);

        return "createnewmodel";
    }
}
