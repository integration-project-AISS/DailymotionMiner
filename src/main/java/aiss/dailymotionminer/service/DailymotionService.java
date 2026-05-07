package aiss.dailymotionminer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import aiss.dailymotionminer.etl.Transformer;
import aiss.dailymotionminer.model.dailymotion.Owner;
import aiss.dailymotionminer.model.dailymotion.Video;
import aiss.dailymotionminer.model.dailymotion.VideoResponse;
import aiss.dailymotionminer.model.videominer.VMChannel;
import aiss.dailymotionminer.model.videominer.VMVideo;

@Service
public class DailymotionService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private Transformer transformer;

    // URL base Dailymotion
    @Value("${dailymotionminer.baseuri}")
    private String dailymotionBaseUrl;

    // URL VideoMiner
    @Value("${videominer.uri}")
    private String videoMinerBaseUrl;

    // ============================================
    // METODO PRINCIPAL
    // ============================================
    public VMChannel mineChannel(String channelId, int maxVideos, int maxPages) {

        VMChannel vmChannel = getChannelInfo(channelId);
        if (vmChannel == null) {
            return null;
        }

        List<VMVideo> videos = getVideos(channelId, maxVideos, maxPages);
        vmChannel.setVideos(videos);
        sendToVideoMiner(vmChannel);
        return vmChannel;
    }

    // ============================================
    // PREVIEW (GET SIN ENVIAR A VIDEOMINER)
    // ============================================
    public VMChannel previewChannel(String channelId, int maxVideos, int maxPages) {

        VMChannel vmChannel = getChannelInfo(channelId);

        if (vmChannel == null) {
            return null;
        }

        List<VMVideo> videos = getVideos(channelId, maxVideos, maxPages);
        vmChannel.setVideos(videos);

        return vmChannel;
    }

    // ============================================
    // 1. OBTENER INFO DEL CANAL
    // ============================================
    private VMChannel getChannelInfo(String channelId) {

        String url = dailymotionBaseUrl +
                "/user/" + channelId +
                "?fields=id,screenname,description,created_time";

        try {
            Owner owner = restTemplate.getForObject(url, Owner.class);
            if (owner != null) {
                return transformer.transformChannel(owner);
            }

        } catch (HttpClientErrorException.NotFound e) {

            System.err.println("Canal no encontrado: " + channelId);
        }

        return null;
    }

    // ============================================
    // 2. OBTENER VIDEOS
    // ============================================
    private List<VMVideo> getVideos(String channelId, int maxVideos, int maxPages) {
        List<VMVideo> result = new ArrayList<>();
        try {
            for (int currentPage = 1; currentPage <= maxPages; currentPage++) {
                // Ajustamos el límite de la petición para no pedir más de lo que falta para llegar a maxVideos
                int remainingVideos = maxVideos - result.size();
                if (remainingVideos <= 0) break; // Ya hemos alcanzado el máximo total

                String url = dailymotionBaseUrl + "/user/" + channelId 
                    + "/videos?fields=id,title,description,created_time,"
                    + "owner.screenname,owner.url,owner.avatar_240_url,"
                    + "tags,ai_subtitle_languages"
                    + "&limit=" + Math.min(remainingVideos, 10) // Usamos un límite razonable o el restante
                    + "&page=" + currentPage;

                VideoResponse response = restTemplate.getForObject(url, VideoResponse.class);

                if (response == null || response.getList() == null || response.getList().isEmpty()) {
                    break; 
                }

                for (Video dVideo : response.getList()) {
                    if (result.size() >= maxVideos) break; // Doble seguridad para no exceder el total

                    VMVideo vmVideo = transformer.transformVideo(dVideo);
                    vmVideo.setComments(transformer.transformTagsToComments(dVideo.getTags(), dVideo.getId()));
                    vmVideo.setCaptions(transformer.transformCaptions(dVideo.getAiSubtitleLanguages()));

                    result.add(vmVideo);
                }
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo vídeos: " + e.getMessage());
        }
        return result;
    }

    // ============================================
    // 3. ENVIAR A VIDEOMINER
    // ============================================
    private void sendToVideoMiner(VMChannel vmChannel) {
        String url = videoMinerBaseUrl + "/channels";

        try {
            restTemplate.postForObject(
                    url,
                    vmChannel,
                    VMChannel.class
            );
            System.out.println("Canal enviado con éxito a VideoMiner");

        } catch (Exception e) {
            System.err.println(
                    "Error al conectar con VideoMiner: "
                            + e.getMessage()
            );
        }
    }
}