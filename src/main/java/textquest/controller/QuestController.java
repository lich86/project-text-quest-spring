package textquest.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import textquest.service.QuestService;


import jakarta.servlet.http.HttpServletRequest;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class QuestController {
    private final QuestService service;

    @Autowired
    public QuestController(QuestService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String start(Model model,
            HttpServletRequest request) {

        initGame(model, request);

        return "game";
    }

    private void initGame(Model model, HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (isNull(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        if(isNull(service.getCounter("counter"))) {
            service.initCounters();
        }

        service.setChapterNumber(1);
        model.addAttribute("IP", ipAddress);
        model.addAttribute("counter", service.getCounter("counter"));
        model.addAttribute("counterLost", service.getCounter("counterLost"));
        model.addAttribute("counterWon", service.getCounter("counterWon"));
    }

    @PostMapping("/restart")
    public String restart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "forward:game";
    }

    @PostMapping("/game")
    public String game(@RequestParam(value = "username", required = false) String username,
                       @RequestParam(value = "chosenAnswer", required = false) String chosenAnswer,
                       Model model,
                       HttpServletRequest request) {

        initGame(model, request);

        if(isNull(service.getUsername())) {
            service.setUsername(username);
        }

        if(chosenAnswer != null) {
            service.setChosenAnswer(chosenAnswer);
        }

        model.addAttribute("username", service.getUsername());
        model.addAttribute("counter", service.getCounter("counter"));
        model.addAttribute("counterLost", service.getCounter("counterLost"));
        model.addAttribute("counterWon", service.getCounter("counterWon"));

        String redirectUrl = service.handlePlayerChoice();

        model.addAttribute("question", service.getQuestion());
        model.addAttribute("answers", service.getAnswers());
        model.addAttribute("chapterNumber", service.getCounter("chapterNumber"));
        service.incrementChapterCounter();

        return redirectUrl;
    }


}
