package com.youtube_video.summary.service;

import com.youtube_video.summary.model.Summary;
import com.youtube_video.summary.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {

    @Autowired
    private SummaryRepository repository;

    public void saveSummary(Summary summary) {
        if (summary.getUserEmail() == null || summary.getUserEmail().isEmpty()) {
            throw new IllegalArgumentException("userEmail must not be null or empty");
        }

        repository.save(summary, summary.getUserEmail());
    }

    public List<Summary> getSummariesByEmail(String email) {
        int userId = repository.findUserIdByEmail(email);
        if (userId == -1) {
            return List.of(); // return empty list if user not found
        }
        return repository.findByUserId(userId);
    }

    public void createUser(String email) {
        // Force-create user (will skip if already present due to getOrCreateUserId logic)
        repository.getOrCreateUserId(email);
    }
}
