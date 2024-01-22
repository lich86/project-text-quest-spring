package textquest.service;

import textquest.model.*;

import java.util.List;

public interface QuestService {

    String getRedirectUrl(Answer answer);
    String handlePlayerChoice();
    String getUsername();
    void setUsername(String username);
    Integer getCounter(String counterName);
    void initCounters();
    void setChapterNumber(Integer i);
    void setChosenAnswer(String answerId);
    Question getQuestion();
    List<Answer> getAnswers();
    void incrementChapterCounter();
}
