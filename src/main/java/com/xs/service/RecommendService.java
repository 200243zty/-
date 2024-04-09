package com.xs.service;

import com.xs.domain.Song;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface RecommendService {

    List<Song> recommend(String userId);
}
