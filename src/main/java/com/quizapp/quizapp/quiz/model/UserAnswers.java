package com.quizapp.quizapp.quiz.model;

import com.quizapp.quizapp.core.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_answers")
@EntityListeners(AuditingEntityListener.class)
public class UserAnswers {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    private LocalDateTime answeredAt;
}
