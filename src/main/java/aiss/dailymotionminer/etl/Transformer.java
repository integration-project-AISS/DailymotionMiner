package aiss.dailymotionminer.etl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import aiss.dailymotionminer.model.dailymotion.Owner;
import aiss.dailymotionminer.model.dailymotion.Video;
import aiss.dailymotionminer.model.videominer.VMCaption;
import aiss.dailymotionminer.model.videominer.VMChannel;
import aiss.dailymotionminer.model.videominer.VMComment;
import aiss.dailymotionminer.model.videominer.VMUser;
import aiss.dailymotionminer.model.videominer.VMVideo;

@Component
public class Transformer {

    // 1. Owner -> VMChannel
    public VMChannel transformChannel(Owner owner) {
        VMChannel channel = new VMChannel();

        channel.setId(owner.getId());
        channel.setName(owner.getScreenname());
        channel.setDescription(owner.getDescription());

        if (owner.getCreatedTime() != null) {
            channel.setCreatedTime(owner.getCreatedTime().toString());
        } else {
            channel.setCreatedTime("No disponible");
        }

        return channel;
    }

    // 2. Video -> VMVideo
    public VMVideo transformVideo(Video dVideo) {
        VMVideo video = new VMVideo();
        video.setId(dVideo.getId());
        video.setName(dVideo.getTitle());
        video.setDescription(dVideo.getDescription());

        if (dVideo.getCreatedTime() != null) {
            video.setReleaseTime(dVideo.getCreatedTime().toString());
        }

        // USER
        VMUser user = new VMUser();

        user.setName(dVideo.getOwnerScreenname());
        user.setUser_link(dVideo.getOwnerUrl());
        user.setPicture_link(dVideo.getOwnerAvatar());

        video.setAuthor(user);

        return video;

    }

    // 3. Tags -> VMComment
    // Dailymotion no ofrece comentarios en esta API,
    // así que reutilizamos tags como comentarios
    public List<VMComment> transformTagsToComments(List<String> tags, String videoId) {

        List<VMComment> comments = new ArrayList<>();

        if (tags != null) {

            for (int i = 0; i < tags.size(); i++) {

                VMComment c = new VMComment();

                c.setId(videoId + "-tag-" + i);
                c.setText(tags.get(i));
                c.setCreatedOn("No disponible");

                comments.add(c);
            }
        }

        return comments;
    }

    // 4. Subtitle languages -> VMCaption
    public List<VMCaption> transformCaptions(List<String> subtitleLanguages) {

        List<VMCaption> captions = new ArrayList<>();

        if (subtitleLanguages != null) {

            for (String lang : subtitleLanguages) {

                VMCaption cap = new VMCaption();

                cap.setId(lang);
                cap.setName(lang);
                cap.setLanguage(lang);

                captions.add(cap);
            }
        }

        return captions;
    }
}