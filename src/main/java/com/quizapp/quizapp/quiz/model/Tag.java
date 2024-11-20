package com.quizapp.quizapp.quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag")
    private Set<QuestionTags> questions;
}
