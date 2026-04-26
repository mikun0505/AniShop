package com.example.java.anishop.service;

import org.springframework.stereotype.Service;

import com.example.java.anishop.model.reponse.ApiResponse;


@Service
public interface AnimeService {
    // tìm theo tên anime trên AniMapper kh lưu vào DB
    ApiResponse<?> searchAnime(String animeName,int offset);

    // lấy metadata chi tiết 1 anime từ AniMapper
    ApiResponse<?> getMetadata(Long anilistId);
 
    // sync anilistId về db (chỉ lưu anilistId + viewCount)
    ApiResponse<?> syncAnime(Long anilistId);
 
    // lấy danh sách tập từ provider
    ApiResponse<?> getEpisodes(Long anilistId, String provider, int offset);
 
    // lấy url stream của 1 tập
    ApiResponse<?> getStreamSource(String episodeData, String provider, String server);

}
