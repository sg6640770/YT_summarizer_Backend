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
        int userId = repository.getOrCreateUserId(summary.getUserEmail());
        summary.setUserId(userId);
        repository.save(summary, summary.getUserEmail()); // ✅ fix: pass email too
    }

    public List<Summary> getSummariesByEmail(String email) {
        int userId = repository.findUserIdByEmail(email);
        if (userId == -1) {
            return List.of(); // return empty list if user not found
        }
        return repository.findByUserId(userId);
    }
}
