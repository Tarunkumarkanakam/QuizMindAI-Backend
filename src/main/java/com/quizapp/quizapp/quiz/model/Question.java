package com.quizapp.quizapp.quiz.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "question")
@EntityListeners(AuditingEntityListener.class)
public class Question {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false, unique = true)
    private String questionId;
//    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;
    private String questionType;

    private String topic;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    private Boolean lockStatus;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
