package textquest.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "answer", schema = "quest")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer extends BaseEntity{

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "choice_type")
    ChoiceType choiceType;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    Question question;
    @OneToOne
    @Column(name = "next_question_id")
    Question nextQuestion;
    @OneToOne
    @Column(name = "loosing_id")
    LoosingCause loosingCause;

    @Builder
    public Answer(int id, String text, ChoiceType choiceType, Question nextQuestion, LoosingCause loosingCause) {
        super(id, text);
        this.choiceType = choiceType;
        this.nextQuestion = nextQuestion;
        this.loosingCause = loosingCause;
    }
}
