package aiss.dailymotionminer.controller;

import aiss.dailymotionminer.model.videominer.VMChannel;
import aiss.dailymotionminer.service.DailymotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/dailymotion")
public class DailymotionController {

    @Autowired
    private DailymotionService dailymotionService;

    // Lo que me dan en el PDF: POST /dailymotion/{channelId}?maxVideos=10&maxPages=2
    @PostMapping("/{channelId}")
    @ResponseStatus(HttpStatus.CREATED)
    public VMChannel mineChannel(
            @PathVariable String channelId,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxPages) {

        VMChannel channel = dailymotionService.mineChannel(channelId, maxVideos, maxPages);

        //El 404 que me dicen que haga
        if (channel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Canal no encontrado");
        }
        return channel;
    }
    // GET /dailymotion/{channelId}
    // Para ver lo que devolvería el post antes de ejecutarlo
    @GetMapping("/{channelId}")
    public VMChannel previewChannel(
            @PathVariable String channelId,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxPages) {

        VMChannel channel = dailymotionService.previewChannel(channelId, maxVideos, maxPages);

        if (channel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Canal no encontrado en Dailymotion");
        }
        return channel;
    }
}