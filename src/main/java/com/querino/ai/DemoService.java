package com.querino.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public static void main(String[] args) {
        new DemoService().oneShotPrompt();
    }

    CommandLineRunner runCommand(){
        return args -> {
            oneShotPrompt();
        };
    }

    public enum Sentiment {
        POSITIVE, NEUTRAL, NEGATIVE

    }

    @Autowired
    ChatClient.Builder builder;

    public void oneShotPrompt() {
        ChatClient chat = builder.build();
        Sentiment sentiment = chat.prompt("""
                        Classify movie reviews as POSITIVE, NEUTRAL or NEGATIVE.
                        Review: "Her" is a disturbing study revealing the direction
                        humanity is headed if AI is allowed to keep evolving,
                        unchecked. I wish there were more movies like this masterpiece.
                        Sentiment:
                        """)
                .call()
                .entity(Sentiment.class);
        System.out.println("result = " + sentiment);
    }
}
