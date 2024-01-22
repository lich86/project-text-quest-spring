package textquest.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "answer", schema = "quest")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Answer extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "choice_type", columnDefinition = "ENUM('WIN', 'LOST', 'HASNEXT')")
    ChoiceType choiceType;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ToString.Exclude
    Question question;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "next_question_id", referencedColumnName = "id")
    @ToString.Exclude
    Question nextQuestion;
    @OneToOne
    @JoinColumn(name = "loosing_id", referencedColumnName = "id")
    LoosingCause loosingCause;

    @Builder
    public Answer(Long id, String text, ChoiceType choiceType, Question nextQuestion, LoosingCause loosingCause) {
        super(id, text);
        this.choiceType = choiceType;
        this.nextQuestion = nextQuestion;
        this.loosingCause = loosingCause;
    }
}
