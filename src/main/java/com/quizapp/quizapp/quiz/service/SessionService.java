package com.quizapp.quizapp.quiz.service;

import com.quizapp.quizapp.core.auth.util.ResourceNotFoundException;
import com.quizapp.quizapp.core.model.User;
import com.quizapp.quizapp.core.repository.UserRepository;
import com.quizapp.quizapp.quiz.dto.CreateSessionRequest;
import com.quizapp.quizapp.quiz.dto.SessionDataDTO;
import com.quizapp.quizapp.quiz.dto.TestResponse;
import com.quizapp.quizapp.quiz.model.Exam;
import com.quizapp.quizapp.quiz.model.Session;
import com.quizapp.quizapp.quiz.repository.ExamRepository;
import com.quizapp.quizapp.quiz.repository.SessionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createSession(@RequestBody @Valid CreateSessionRequest request) {
        var exam = examRepository.findByExamId(request.getExamId()).orElse(null);
        if (exam == null) {
            throw new NoSuchElementException("Exam not found");
        }
        List<String> emails = request.getEmails();
        for (var email : emails) {
            var user = userRepository.findByEmail(email).orElse(null);
            var session = Session.builder()
                    .sessionId(request.getSessionId())
                    .lockStatus(true)
                    .exam(exam)
                    .user(user)
                    .examStart(request.getExamStart())
                    .examEnd(request.getExamEnd())
                    .createdBy(userRepository.findByEmail(request.getAdminEmail()).orElseThrow(()-> new ResourceNotFoundException("Admin Not Found")))
                    .build();
            sessionRepository.save(session);
        }
    }

    public List<TestResponse> findAllTestsofUser(String email) {
        var tests = sessionRepository.findExamsByUserId(email);
        List<TestResponse> responses = new ArrayList<>();
        for (var exam : tests) {
            var testResponse = TestResponse.builder()
                    .examId(exam.getExam().getExamId())
                    .examName(exam.getExam().getExamName())
                    .examDesc(exam.getExam().getExamDescription())
                    .examStartTime(exam.getExamStart())
                    .examEndTime(exam.getExamEnd())
                    .build();
            responses.add(testResponse);
        }
        return responses;
    }

    public Session getSessionfromEmailAndExamId(String email, String examId) {
        return sessionRepository.findActiveSessionByUserIdAndExamExamId(email, examId);
    }

    @Transactional
    public SessionDataDTO updateSession(SessionDataDTO sessionDetails, String email) {
        Session session = sessionRepository.findById(sessionDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Session not found for this id :: " + sessionDetails.getId()));

        session.setSessionId(sessionDetails.getSessionId());
        session.setExamStart(LocalDateTime.now());
        session.setExamEnd(sessionDetails.getExamEndTime());
        session.setLockStatus(sessionDetails.getLockStatus());
        session.setUser(userRepository.findByEmail(sessionDetails.getUserEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        session.setExam(examRepository.findByExamId(sessionDetails.getExamId()).orElseThrow(() -> new ResourceNotFoundException("User not found")));
//        session.setUserSessions(sessionDetails.getUserSessions());
        session.setCreatedBy(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Admin not found")));

        return sessionToSessionDataDTO(sessionRepository.save(session));
    }

    @Transactional
    public void deleteSession(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found for this id :: " + id));

        sessionRepository.delete(session);
    }

    public List<String> getAllSessionsOnAdmin(String email) {
        return sessionRepository.findSessionByLogUserId(email);
    }

    public List<SessionDataDTO> getAllSessions(String email, String sessionId) {
        var sessionData = sessionRepository.findAllSessionsBySessionId(email, sessionId);
        List<SessionDataDTO> sessions = new ArrayList<>();
        for (var temp : sessionData) {
            sessions.add(sessionToSessionDataDTO(temp));
        }
        return sessions;
    }

    public SessionDataDTO sessionToSessionDataDTO(Session temp) {
        return SessionDataDTO
                .builder()
                .id(temp.getId())
                .sessionId(temp.getSessionId())
                .userEmail(temp.getUser().getEmail())
                .examId(temp.getExam().getExamId())
                .examStartTime(temp.getExamStart())
                .examEndTime(temp.getExamEnd())
                .lockStatus(temp.getLockStatus())
                .build();
    }

    public void lockSession(Session session) {
        session.setLockStatus(false);
        sessionRepository.save(session);
    }
}
