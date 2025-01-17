package yjkim.GuideUs.Maps.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/maps")
@Controller
public class MapsController {



    @GetMapping("/")
    public String getMaps(Model model){

        System.out.println("dasdasd");
        return "maps";
    }

    // 결과를 출력해주는 컨트롤러임
    @PostMapping("/result")
    public String getResult (Model model){

        return "result";
    }

}
