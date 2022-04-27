package capstone.luis.devbyte;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController implements ErrorController {
    @GetMapping("/error")
    public String forwardVuePaths() {
        return "forward:/index.html";
    }
}
