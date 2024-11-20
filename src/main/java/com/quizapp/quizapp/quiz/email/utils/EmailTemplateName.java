package com.quizapp.quizapp.quiz.email.utils;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

//    USER_REGISTER("user_register");

    EXAM_USER_REGISTER("exam_user_register");


    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}