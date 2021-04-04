package pl.wgalka.wekarestapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MvcController {
    @GetMapping("/main")
    public String main(@RequestParam(name = "tab", required = false, defaultValue = "mainpage") String tab, Model model) {
        model.addAttribute("name", tab);
        return "main";
    }
}
