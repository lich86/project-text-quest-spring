package textquest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "loosing_cause", schema = "quest")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoosingCause extends BaseEntity{
    public LoosingCause(int id, String text) {
        super(id, text);
    }
}
