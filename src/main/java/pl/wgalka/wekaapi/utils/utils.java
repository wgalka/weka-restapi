package pl.wgalka.wekaapi.utils;

import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;

public class utils {
    public static Instances createemptydataset() {
        Attribute age = new Attribute("Age"); //        @attribute Age numeric
        ArrayList<String> genderLabels = new ArrayList<String>();
        genderLabels.add("Male");
        genderLabels.add("Female");
        Attribute gender = new Attribute("Gender", genderLabels); //        @attribute Gender {Male,Female}
        ArrayList<String> labels1 = new ArrayList<String>();
        labels1.add("No");
        labels1.add("Yes");
        Attribute polyuria = new Attribute("Polyuria", labels1); //        @attribute Polyuria {No,Yes}
        ArrayList<String> labels2 = new ArrayList<String>();
        labels2.add("Yes");
        labels2.add("No");
        Attribute polydipsia = new Attribute("Polydipsia", labels2); //        @attribute Polydipsia {Yes,No}
        ArrayList<String> labels3 = new ArrayList<String>();
        labels3.add("No");
        labels3.add("Yes");
        Attribute sudden_weight_loss = new Attribute("sudden weight loss", labels3); //        @attribute 'sudden weight loss' {No,Yes}
        ArrayList<String> labels4 = new ArrayList<String>();
        labels4.add("Yes");
        labels4.add("No");
        Attribute weakness = new Attribute("weakness", labels4); //        @attribute weakness {Yes,No}
        ArrayList<String> labels5 = new ArrayList<String>();
        labels5.add("No");
        labels5.add("Yes");
        Attribute polyphagia = new Attribute("Polyphagia", labels5); //        @attribute Polyphagia {No,Yes}
        ArrayList<String> labels6 = new ArrayList<String>();
        labels6.add("No");
        labels6.add("Yes");
        Attribute genital_thrush = new Attribute("Genital thrush", labels6); //        @attribute 'Genital thrush' {No,Yes}
        ArrayList<String> labels7 = new ArrayList<String>();
        labels7.add("No");
        labels7.add("Yes");
        Attribute visual_blurring = new Attribute("visual blurring", labels7); //        @attribute 'visual blurring' {No,Yes}
        ArrayList<String> labels8 = new ArrayList<String>();
        labels8.add("Yes");
        labels8.add("No");
        Attribute itching = new Attribute("Itching", labels8); //        @attribute Itching {Yes,No}
        ArrayList<String> labels9 = new ArrayList<String>();
        labels9.add("No");
        labels9.add("Yes");
        Attribute irritability = new Attribute("Irritability", labels9); //        @attribute Irritability {No,Yes}
        ArrayList<String> labels10 = new ArrayList<String>();
        labels10.add("Yes");
        labels10.add("No");
        Attribute delayed_healing = new Attribute("delayed healing", labels10); //        @attribute 'delayed healing' {Yes,No}
        ArrayList<String> labels11 = new ArrayList<String>();
        labels11.add("No");
        labels11.add("Yes");
        Attribute partial_paresis = new Attribute("partial paresis", labels11); //        @attribute 'partial paresis' {No,Yes}
        ArrayList<String> labels12 = new ArrayList<String>();
        labels12.add("Yes");
        labels12.add("No");
        Attribute muscle_stiffness = new Attribute("muscle stiffness", labels12); //        @attribute 'muscle stiffness' {Yes,No}
        ArrayList<String> labels13 = new ArrayList<String>();
        labels13.add("Yes");
        labels13.add("No");
        Attribute alopecia = new Attribute("Alopecia", labels13); //        @attribute Alopecia {Yes,No}
        ArrayList<String> labels14 = new ArrayList<String>();
        labels14.add("Yes");
        labels14.add("No");
        Attribute obesity = new Attribute("Obesity", labels14); //        @attribute Obesity {Yes,No}
        ArrayList<String> diagnoseLabels = new ArrayList<String>();
        diagnoseLabels.add("Positive");
        diagnoseLabels.add("Negative");
        Attribute diagnose = new Attribute("class", diagnoseLabels); //        @attribute class {Positive,Negative}

        ArrayList<Attribute> attributes = new ArrayList<Attribute>(); //utworzenie listy atrybutow dla nowej tablicy
        attributes.add(age);
        attributes.add(gender);
        attributes.add(polyuria);
        attributes.add(polydipsia);
        attributes.add(sudden_weight_loss);
        attributes.add(weakness);
        attributes.add(polyphagia);
        attributes.add(genital_thrush);
        attributes.add(visual_blurring);
        attributes.add(itching);
        attributes.add(irritability);
        attributes.add(delayed_healing);
        attributes.add(partial_paresis);
        attributes.add(muscle_stiffness);
        attributes.add(alopecia);
        attributes.add(obesity);
        attributes.add(diagnose);

        //Utworzenie pustej tablicy danych
        Instances data = new Instances("Nowa tablica", attributes, 0);
        data.setClassIndex(16);
        return data;
    }
}
