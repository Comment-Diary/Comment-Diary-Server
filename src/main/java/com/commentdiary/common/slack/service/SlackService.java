package com.commentdiary.common.slack.service;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.slack.api.Slack;

import java.io.IOException;

@Slf4j
@Service
public class SlackService {
    @Value("${slack.token}")
    String token;

    @Value("${slack.channel.id}")
    String channel;

    public void sendSlackMessage(String message) {
        try {
            Slack slack = Slack.getInstance();
            slack.methods(token).chatPostMessage(req -> req
                    .channel(channel)
                    .text(message));
        }
        catch (SlackApiException | IOException e) {
            log.error("Send Slack Message Error : {}", e.getMessage());
        }

    }
}
