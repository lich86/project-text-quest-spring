package textquest.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "question", schema = "quest")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Question extends BaseEntity{
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "question")
    @ToString.Exclude
    final List<Answer> answers = new ArrayList<>();

    public Question(Long id, String text, Answer... answers) {
        super(id, text);
        this.answers.addAll(Arrays.asList(answers));
    }

    public Question(Long id, String text) {
        super(id, text);
    }

    public void setAnswers(Answer... answers) {
        this.answers.addAll(Arrays.asList(answers));
    }
}
