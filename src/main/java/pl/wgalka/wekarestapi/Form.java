package pl.wgalka.wekarestapi;

import weka.classifiers.Classifier;

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
}
