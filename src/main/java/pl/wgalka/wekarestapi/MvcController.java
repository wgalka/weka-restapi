package pl.wgalka.wekarestapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weka.classifiers.Classifier;
import weka.core.Instances;

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

            String prob = String.valueOf(dist[(int) result]*100)+"%";
            model.addAttribute("tree", tree.toString());
            model.addAttribute("diagnose", classification);
            model.addAttribute("probability", prob);
            model.addAttribute("form",diagnose.formanswers());

        return "result";
    }
}
