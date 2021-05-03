package pl.wgalka.wekarestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Populatedb {

    private FormRepo formRepo;

    @Autowired
    public void Populatedb(FormRepo formRepo) {
        this.formRepo = formRepo;
    }

    public void populate(){
        Form f1 = new Form();
    }
}
