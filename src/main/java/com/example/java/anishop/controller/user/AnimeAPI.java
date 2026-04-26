package com.example.java.anishop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.service.AnimeService;

import jakarta.validation.constraints.Min;




@RestController
public class AnimeAPI {

    @Autowired
    private AnimeService animeService;
    
    @GetMapping("/api/animes/search")
    public ResponseEntity<ApiResponse<?>> searchAnime(@RequestParam String title,
                                                    @RequestParam(defaultValue="0") int offset) {
        return ResponseEntity.ok(animeService.searchAnime(title, offset));
    }
    
    @GetMapping("/api/animes/{anilistId}")
    public ResponseEntity<ApiResponse<?>> getMetadata(@PathVariable @Min(1) Long anilistId) {
        return ResponseEntity.ok(animeService.getMetadata(anilistId));
    }
    
    @PostMapping("/api/animes/sync/{anilistId}")
    public ResponseEntity<ApiResponse<?>> syncAnime(@PathVariable @Min(1) Long anilistId) {
        //TODO: process POST request
        
        return ResponseEntity.ok(animeService.getMetadata(anilistId));
    }
    

    @GetMapping("/api/animes/{anilistId}/episodes")
    public ResponseEntity<ApiResponse<?>> getEpsodes(@PathVariable @Min(1) Long anilistId,
                                                    @RequestParam String provider,
                                                    @RequestParam(defaultValue="0") int offset) {
        return ResponseEntity.ok(animeService.getEpisodes(anilistId, provider, offset));

    }

    // lấy url của 1 tập
    @GetMapping("/api/animes/stream")
    public ResponseEntity<ApiResponse<?>> getStreamSource(@RequestParam String episodes,
                                                          @RequestParam String provider,
                                                          @RequestParam(required=false) String server) {
        return ResponseEntity.ok(animeService.getStreamSource(episodes, provider, server));
    }
    
    
}
