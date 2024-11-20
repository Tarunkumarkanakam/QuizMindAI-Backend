package com.quizapp.quizapp.quiz.model;

import com.quizapp.quizapp.core.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "results")
@EntityListeners(AuditingEntityListener.class)
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;
    @Column(nullable = false)
    private Integer score;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime declaredDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
