import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.Arrays;
import java.util.List;

import static java.time.Duration.ofSeconds;

public class NewsService {
    static ChatLanguageModel model = OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
//            .logRequests(true)
//            .logResponses(true)
            .timeout(ofSeconds(60))
            .build();

    public static void main(String[] args) {

        News newsAgant = AiServices.create(News.class, model);


        Response response = newsAgant.analyzeSentimentOf("""
                                                                    Adding the strong gains in the previous four sessions, the Japanese stock market is significantly higher on Friday, following the mixed cues from Wall Street overnight. The benchmark Nikkei 225 is moving above the 35,400 level, with gains across most sectors led by index heavyweights and exporter stocks.
                                
                                The benchmark Nikkei 225 Index is gaining 372.73 points or 1.06 percent to 35,422.59, after touching a fresh 34-year high of 35,839.65 earlier. Japanese stocks closed sharply higher on Thursday.
                """);
        System.out.println(response);


    }

    enum Sentiment {
        POSITIVE, NEUTRAL, NEGATIVE;
    }

    static class Response {

        private Sentiment sentiment;
        private List<String> keywords;
        private List<String> currencies;

        @Override
        public String toString() {
            return "Response{" +
                    "sentiment=" + sentiment +
                    "\n, keywords=" + keywords +
                    "\n, currencies=" + currencies +
                    '}';
        }
    }




    interface News {

        @SystemMessage("Analyze sentiment of {{article}} forex news article ,extract meaningful financial keywords and suggest relevant forex trading pairs in format like EURUSD")
        Response analyzeSentimentOf(@V("article") String article);





    }
}
