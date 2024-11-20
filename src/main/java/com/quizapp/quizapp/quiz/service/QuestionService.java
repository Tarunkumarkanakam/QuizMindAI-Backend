package com.quizapp.quizapp.quiz.service;

import com.quizapp.quizapp.quiz.model.Answer;
import com.quizapp.quizapp.quiz.model.Exam;
import com.quizapp.quizapp.quiz.model.Question;
import com.quizapp.quizapp.quiz.repository.AnswerRepository;
import com.quizapp.quizapp.quiz.repository.ExamRepository;
import com.quizapp.quizapp.quiz.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ExamRepository examRepository;

    @Transactional
    public void processExcelFile(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rows = sheet.iterator();
        rows.next(); // Skip header row

        List<Question> questions = new ArrayList<>();

        while (rows.hasNext()) {
            Row currentRow = rows.next();

            String questionId = currentRow.getCell(0).getStringCellValue();
            String questionText = currentRow.getCell(1).getStringCellValue();
            String questionType = currentRow.getCell(2).getStringCellValue();
            String examId = currentRow.getCell(3).getStringCellValue();
            String answerId = currentRow.getCell(4).getStringCellValue();
            String answerText = currentRow.getCell(5).getStringCellValue();
            boolean isCorrect = currentRow.getCell(6).getBooleanCellValue();
            String explanation = currentRow.getCell(7).getStringCellValue();

            // Retrieve exam by examId
            Exam exam = examRepository.findByExamId(examId)
                    .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));

            // Check if question already exists (based on questionId)
            Question question = questionRepository.findByQuestionId(questionId)
                    .orElse(new Question());

            question.setQuestionId(questionId);
            question.setQuestion(questionText);
            question.setQuestionType(questionType);
            question.setExam(exam);

            // Create new answer for the question
            Answer answer = new Answer();
            answer.setAnswerId(answerId);
            answer.setAnswerText(answerText);
            answer.setIsCorrect(isCorrect);
            answer.setExplanation(explanation);
            answer.setQuestion(question);

            // Add answer to question's answers set
            if (question.getAnswers() == null) {
                question.setAnswers(new HashSet<>());
            }
            question.getAnswers().add(answer);

            questions.add(question);
        }

        // Save all questions and answers
        questionRepository.saveAll(questions);

        workbook.close();
    }
}
