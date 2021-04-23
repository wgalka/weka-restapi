package pl.wgalka.wekarestapi;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

public class Form {
    private Integer age;
    private String gender;
    private String polyuria;
    private String polydipsia;
    private String sudden_weight_loss;
    private String weakness;
    private String polyphagia;
    private String genital_thrush;
    private String visual_blurring;
    private String itching;
    private String irritability;
    private String delayed_healing;
    private String partial_paresis;
    private String muscle_stiffness;
    private String alopecia;
    private String obesity;
    private String diagnose;
    private double probability;

    public Form(Integer age, String gender, String polyuria, String polydipsia, String sudden_weight_loss, String weakness, String polyphagia, String genital_thrush, String visual_blurring, String itching, String irritability, String delayed_healing, String partial_paresis, String muscle_stiffness, String alopecia, String obesity) throws Exception {
        this.age = age;
        this.gender = gender;
        this.polyuria = polyuria;
        this.polydipsia = polydipsia;
        this.sudden_weight_loss = sudden_weight_loss;
        this.weakness = weakness;
        this.polyphagia = polyphagia;
        this.genital_thrush = genital_thrush;
        this.visual_blurring = visual_blurring;
        this.itching = itching;
        this.irritability = irritability;
        this.delayed_healing = delayed_healing;
        this.partial_paresis = partial_paresis;
        this.muscle_stiffness = muscle_stiffness;
        this.alopecia = alopecia;
        this.obesity = obesity;

//        Classifier tree = (Classifier) weka.core.SerializationHelper.read("models/j48.model");

//        double[] attValues = new double[16];
//        attValues[0] = 50;
//        attValues[1] = 5;
//        attValues[2] = 800;
//        attValues[3] = 74;
//        attValues[4] = 3;
//        attValues[5] = 760;

        //Create the new instance i1
//        Instances i1 = new Instances();


//        tree.classifyInstance(i1);

    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPolyuria() {
        return polyuria;
    }

    public void setPolyuria(String polyuria) {
        this.polyuria = polyuria;
    }

    public String getPolydipsia() {
        return polydipsia;
    }

    public void setPolydipsia(String polydipsia) {
        this.polydipsia = polydipsia;
    }

    public String getSudden_weight_loss() {
        return sudden_weight_loss;
    }

    public void setSudden_weight_loss(String sudden_weight_loss) {
        this.sudden_weight_loss = sudden_weight_loss;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public String getPolyphagia() {
        return polyphagia;
    }

    public void setPolyphagia(String polyphagia) {
        this.polyphagia = polyphagia;
    }

    public String getGenital_thrush() {
        return genital_thrush;
    }

    public void setGenital_thrush(String genital_thrush) {
        this.genital_thrush = genital_thrush;
    }

    public String getVisual_blurring() {
        return visual_blurring;
    }

    public void setVisual_blurring(String visual_blurring) {
        this.visual_blurring = visual_blurring;
    }

    public String getItching() {
        return itching;
    }

    public void setItching(String itching) {
        this.itching = itching;
    }

    public String getIrritability() {
        return irritability;
    }

    public void setIrritability(String irritability) {
        this.irritability = irritability;
    }

    public String getDelayed_healing() {
        return delayed_healing;
    }

    public void setDelayed_healing(String delayed_healing) {
        this.delayed_healing = delayed_healing;
    }

    public String getPartial_paresis() {
        return partial_paresis;
    }

    public void setPartial_paresis(String partial_paresis) {
        this.partial_paresis = partial_paresis;
    }

    public String getMuscle_stiffness() {
        return muscle_stiffness;
    }

    public void setMuscle_stiffness(String muscle_stiffness) {
        this.muscle_stiffness = muscle_stiffness;
    }

    public String getAlopecia() {
        return alopecia;
    }

    public void setAlopecia(String alopecia) {
        this.alopecia = alopecia;
    }

    public String getObesity() {
        return obesity;
    }

    public void setObesity(String obesity) {
        this.obesity = obesity;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    /**
     * Tworzenie nowej tablicy danych - przyklad
     *
     * @return
     * @throws Exception
     */
    public Instances newInstances() throws Exception {

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

        //Wstawienie obiektu (z wartosciami "?" - czyli "missing")

        DenseInstance emptyinstance = new DenseInstance(attributes.size());
        data.add(emptyinstance);


        //Wypelnienie tablicy wartosciami (UWAGA NA TYP!!!)
        Instance instance = data.instance(0); //Pobranie obiektu o podanym numerze
        instance.setValue(0, this.age);//        @attribute Age numeric
        instance.setValue(1, this.gender);//        @attribute Gender {Male,Female}
        instance.setValue(2, this.polyuria);//        @attribute Polyuria {No,Yes}
        instance.setValue(3, this.polydipsia);//        @attribute Polydipsia {Yes,No}
        instance.setValue(4, this.sudden_weight_loss);//        @attribute 'sudden weight loss' {No,Yes}
        instance.setValue(5, this.weakness);//        @attribute weakness {Yes,No}
        instance.setValue(6, this.polyphagia);//        @attribute Polyphagia {No,Yes}
        instance.setValue(7, this.genital_thrush);//        @attribute 'Genital thrush' {No,Yes}
        instance.setValue(8, this.visual_blurring);//        @attribute 'visual blurring' {No,Yes}
        instance.setValue(9, this.itching);//        @attribute Itching {Yes,No}
        instance.setValue(10, this.irritability);//        @attribute Irritability {No,Yes}
        instance.setValue(11, this.delayed_healing);//        @attribute 'delayed healing' {Yes,No}
        instance.setValue(12, this.partial_paresis);//        @attribute 'partial paresis' {No,Yes}
        instance.setValue(13, this.muscle_stiffness);//        @attribute 'muscle stiffness' {Yes,No}
        instance.setValue(14, this.alopecia);//        @attribute Alopecia {Yes,No}
        instance.setValue(15, this.obesity);//        @attribute Obesity {Yes,No}
//        String x = "Negative";
//        instance.setValue(16, x);//       @attribute class {Positive,Negative}
        data.setClassIndex(16);
        return data;
    }

    public String formanswers() {
        return "Age: " + age +'\n' +
                "Gender: " + gender + '\n' +
                "Polyuria: " + polyuria + '\n' +
                "Polydipsia: " + polydipsia + '\n' +
                "Sudden weight loss: " + sudden_weight_loss + '\n' +
                "Weakness: " + weakness + '\n' +
                "Polyphagia: " + polyphagia + '\n' +
                "Genital thrush: " + genital_thrush + '\n' +
                "Visual blurring: " + visual_blurring + '\n' +
                "Itching: " + itching + '\n' +
                "Irritability: " + irritability + '\n' +
                "Delayed healing: " + delayed_healing + '\n' +
                "Partial paresis: " + partial_paresis + '\n' +
                "Muscle stiffness: " + muscle_stiffness + '\n' +
                "Alopecia: " + alopecia + '\n' +
                "Obesity: " + obesity;
    }
}
