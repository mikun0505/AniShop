package com.example.java.anishop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.repository.AnimeRepository;
import com.example.java.anishop.repository.entity.Animes;
import com.example.java.anishop.service.AnimeService;

import jakarta.transaction.Transactional;

@Service
public class AnimeServiceImpl implements AnimeService{

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private MapperConverter mapperConverter;
    // hỗ trợ lấy API từ web khác đg dẫn của aniMapper
    private final WebClient webClient=WebClient.builder()
                .baseUrl("https://api.animapper.net/api/v1")
                .build();

    
    // số kq mỗi trang tối đa chỉ 20
    private static final int PAGE_SIZE=20; 
    @Override
    public ApiResponse<?> searchAnime(String animeName, int offset) {
        if(animeName==null|| animeName.isBlank()){
            throw new AppException("Tên phim không đc trống",400);
        }
        Map<String, Object> response=webClient.get()
                .uri(uri->uri.path("/search")
                    .queryParam("title", animeName)
                    .queryParam("mediaType","ANIME")
                    .queryParam("limit", PAGE_SIZE)
                    .queryParam("offset", offset)
                .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();
                System.out.println(response);

        if(response==null || response.get("results")==null){
            throw new AppException("Không tìm thấy kết quả",404);
        }
        
        List<Map<String,Object>> results=(List<Map<String,Object>>)response.get("results");
        
        // List<AnimeDTO> datas=results.stream()
        //     .map(item-> {
        //         AnimeDTO dto=mapperConverter.seAnimeDTO(item);
        //         // kiểm tra nếu đã lưu vào DB r thì đém viewCount
        //         animeRepository.findByAnilistId(dto.getAnilistId())
        //                 .ifPresent(anime ->dto.setViewCount(anime.getViewCount()));
        //         return dto;
        //     }).collect(Collectors.toList());
            return ApiResponse.<List<Map<String,Object>>>builder()
                    .status(200)
                    .message("Tìm được "+ results.size() +" kết quả " )
                    .data(results)
                    .build();
    }

    @Override
    public ApiResponse<?> getMetadata(Long anilistId) {
        // tạo Map để đựng madia bên ngoài
        Map<String,Object> reponse=webClient.get()
                    .uri(uri-> uri.path("/metadata")
                        .queryParam("id",anilistId)
                    .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

        if(reponse==null ){
            throw new AppException("Không tìm thây anime và anilistId :"+anilistId, 404);
        }

        Map<String,Object> result=(Map<String,Object>) reponse.get("result" );

        // gắn thêm view nếu có trong DB
        animeRepository.findByAnilistId(anilistId)
                .ifPresent(anime->result.put("viewCount", anime.getViewCount()));

        return ApiResponse.<Map<String,Object>>builder()
                        .status(200)
                        .message("Lấy metadata thành công")
                        .data(result)
                        .build();
    }

    @Transactional
    @Override
    public ApiResponse<?> syncAnime(Long anilistId) {
        if(animeRepository.findByAnilistId(anilistId).isPresent()){
            throw new AppException("Đã tồn tại trong DB",400);
        }

        // verify xem thử có tồn tại trong AniMapper hay kh
        Map<String,Object> response=webClient.get()
                        .uri(uri->uri.path("/metadata")
                        .queryParam("id", anilistId)
                        .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
                
        if(response==null|| !Boolean.TRUE.equals(response.get("success"))){
            throw new AppException("Không tìm thấy anime trên AniMapper với id= "+anilistId,404);
        }

        Animes anime=new Animes();
        anime.setAnilistId(anilistId);
        anime.setViewCount(0L);
        animeRepository.save(anime);

        return ApiResponse.<String>builder()
                .status(201)
                .message("sync thành công anime anilistId= "+ anilistId)
                .data(null)
                .build();
    }

    // lấy danh sách tập - limit fix cứng trên Offset
    @Override
    public ApiResponse<?> getEpisodes(Long anilistId, String provider, int offset) {
        if(provider==null|| provider.isBlank()){
            throw new AppException("Prvider không được trống (ANIMEVIETSUB, ANIMEVN , NINIYO)",400);
        }

        // tạo map để hứng
        Map<String,Object> response=webClient.get()
                                .uri(uri-> uri.path("/stream/episodes")
                                .queryParam("id", anilistId)
                                .queryParam("provider", provider.toUpperCase())// chuyển sang chử hoa
                                .queryParam("limit", PAGE_SIZE)
                                .queryParam("offset", offset)
                                .build())
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block(); 
        if(response==null){
            throw new AppException("Không tìm thấy danh sách tập",404);
        }
        // tăng view lên mỗi lần user load danh sách tập
        animeRepository.findByAnilistId(anilistId).ifPresent(anime->{
            anime.setViewCount(anime.getViewCount()+1);
            animeRepository.save(anime);
        });

        return ApiResponse.<Map<String,Object>>builder()
                    .status(200)
                    .message("Lấy danh sách tập")
                    .data(response)
                    .build();
    }

    // lấy link xem video của 1 tập phim
    @Override
    public ApiResponse<?> getStreamSource(String episodeData, String provider, String server) {
        if(episodeData==null|| episodeData.isBlank()){
            throw new AppException("Không được trống episodeData ",400);
        }

        if(provider==null|| provider.isBlank()){
            throw new AppException("Không được trống provider (ANIMEVIETSUB, ANIMEVN , NINIYO)",400);
        }

        // tạo map để đựng data của AniMapper
        
        Map<String, Object> response = webClient.get()
                .uri(uri -> {
                    var b = uri.path("/stream/source")
                            .queryParam("episodeData", episodeData)
                            .queryParam("provider", provider.toUpperCase());
                    if (server != null && !server.isBlank()) {
                        b.queryParam("server", server.toUpperCase());
                    }
                    return b.build();
                })
                .retrieve()
                .bodyToMono(Map.class)
                .block();

                
        if(response==null){
            throw new AppException("Không lấy được url stream ",404);
        }
        return ApiResponse.<Map<String,Object>>builder()
                    .status(200)
                    .message("Lấy url thành công")
                    .data(response)
                    .build();
                    
    }
    
}
