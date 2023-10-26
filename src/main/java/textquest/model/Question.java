package textquest.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table(name = "question", schema = "quest")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question extends BaseEntity{
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "answer_id")
    final ArrayList<Answer> answers = new ArrayList<>();

    public Question(int id, String text, Answer... answers) {
        super(id, text);
        this.answers.addAll(Arrays.asList(answers));
    }

    public void setAnswers(Answer... answers) {
        this.answers.addAll(Arrays.asList(answers));
    }
}
