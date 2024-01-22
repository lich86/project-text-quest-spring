package textquest.service;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import textquest.model.Answer;
import textquest.model.LoosingCause;
import textquest.model.Question;

import java.util.List;

@Component
@SessionScope
@Data
public class SessionData {

    private String username;
    private Integer chapterNumber;
    private Integer counter;
    private Integer counterLost;
    private Integer counterWon;
    private Answer chosenAnswer;
    private Question question;
    private List<Answer> answers;
    private LoosingCause loosingCause;

    public void incrementCounter(String counterName) {
        switch (counterName) {
            case "counter":
                counter++;
                break;
            case "counterLost":
                counterLost++;
                break;
            case "counterWon":
                counterWon++;
                break;
        }
    }

}
