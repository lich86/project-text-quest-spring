package textquest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import textquest.model.Answer;
import textquest.model.ChoiceType;
import textquest.model.Question;
import textquest.repository.AnswerRepository;
import textquest.repository.LoosingCauseRepository;
import textquest.repository.QuestionRepository;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class QuestServiceImpl implements QuestService {

    private final SessionData sessionData;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final LoosingCauseRepository loosingCauseRepository;


    @Autowired
    public QuestServiceImpl(SessionData sessionData, AnswerRepository answerRepository, QuestionRepository questionRepository, LoosingCauseRepository loosingCauseRepository) {
        this.sessionData = sessionData;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.loosingCauseRepository = loosingCauseRepository;
    }

    public void handleLoss(Answer answer) {
        try {
            setLoosingCause(answer);
        } catch (NullPointerException e) {
            log.error("У вопроса [{}] нет поля loosingCause", answer.getId());
            //TODO
        }
        sessionData.incrementCounter("counter");
        sessionData.incrementCounter("counterLost");
    }

    public void handleWin() {
        sessionData.incrementCounter("counter");
        sessionData.incrementCounter("counterWon");
    }


    @Override
    public String getRedirectUrl(Answer answer) {
        String redirectUrl;
        if(answer.getChoiceType() == ChoiceType.HASNEXT) {
            redirectUrl = "game";
        } else if(answer.getChoiceType() == ChoiceType.WIN) {
            handleWin();
            redirectUrl = "youwon";
        } else if(answer.getChoiceType() == ChoiceType.LOST) {
            handleLoss(answer);
            redirectUrl = "gameover";
        } else {
            log.error("У вопроса [{}] нет поля ChoiceType, либо в нём ошибка", answer.getId());
            redirectUrl = "error";
        }
        return redirectUrl;
    }

    @Override
    public String handlePlayerChoice() {
        Question question = questionRepository.findById(1L).orElse(null);
        String redirectUrl = "game";

        Answer chosenAnswer = getChosenAnswer();
        if(chosenAnswer != null) {
            redirectUrl = getRedirectUrl(chosenAnswer);

            if(chosenAnswer.getChoiceType() == ChoiceType.HASNEXT) {
                question = chosenAnswer.getNextQuestion();
            }
        }

        List<Answer> answers;
        try {
            answers = question.getAnswers();
            setQuestion(question);
            setAnswers(answers);
        } catch (NullPointerException e) {
            //вопроса или ответа с таким номером не существует
            log.error("Проблема с вопросом id [{}] (нет такого вопроса, либо у него не установлены ответы)", question.getId());
            redirectUrl = "error";
        }

        return redirectUrl;
    }

    private void setQuestion(Question question) {
        sessionData.setQuestion(question);
    }

    @Override
    public Question getQuestion() {
        return sessionData.getQuestion();
    }

    @Override
    public List<Answer> getAnswers() {
        return sessionData.getAnswers();
    }

    public void setAnswers(List<Answer> answers) {
        Collections.shuffle(answers);
        sessionData.setAnswers(answers);
    }

    @Override
    public String getUsername() {
        return sessionData.getUsername();
    }

    @Override
    public void setUsername(String username) {
        sessionData.setUsername(username);
    }

    public Answer getChosenAnswer() {
        return sessionData.getChosenAnswer();
    }

    @Override
    public void setChosenAnswer(String answerId) {
        Answer answer = answerRepository.findById(Long.parseLong(answerId)).orElse(null);
        if(answer !=null) {
            sessionData.setChosenAnswer(answer);
        }
    }

    public Integer getChapterNumber() {
        return sessionData.getChapterNumber();
    }

    public void setChapterNumber(Integer i) {
        sessionData.setChapterNumber(i);
    }

    public void setLoosingCause(Answer answer) {
        sessionData.setLoosingCause(answer.getLoosingCause());
    }

    @Override
    public void incrementChapterCounter() {
        sessionData.setChapterNumber(sessionData.getChapterNumber() + 1);
    }

    @Override
    public Integer getCounter(String counterName) {
        switch (counterName) {
            case "counter" : return sessionData.getCounter();
            case "counterLost" : return sessionData.getCounterLost();
            case "counterWon" : return sessionData.getCounterWon();
            case "chapterNumber" : return sessionData.getChapterNumber();
            default: return null;
        }
    }

    @Override
    public void initCounters() {
        if(isNull(getCounter("counter"))) {
            sessionData.setCounter(0);
        }
        if(isNull(getCounter("counterLost"))) {
            sessionData.setCounterLost(0);
        }
        if(isNull(getCounter("counterWon"))) {
            sessionData.setCounterWon(0);
        }
    }
}
