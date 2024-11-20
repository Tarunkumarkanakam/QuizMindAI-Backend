package com.quizapp.quizapp.quiz.model;

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
@Table(name = "answer")
@EntityListeners(AuditingEntityListener.class)
public class Answer {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "question_Id", nullable = false)
    private Question question;
    @Column(nullable = false)
    private String answerId;
    @Column(nullable = false)
    private String answerText;
    @Column(nullable = false)
    private Boolean isCorrect;
    private String explanation;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
