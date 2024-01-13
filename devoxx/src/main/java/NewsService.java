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


        Response response = newsAgant.analyzeSentimentOf("Consumer prices in China fell for the third straight month in December highlighting the risk of deflation, while exports expanded more than expected despite the weak global trade.\n" +
                "\n" +
                "The consumer price index, or CPI, dropped 0.3 percent annually after a 0.5 percent decline in November, the National Bureau of Statistics said Friday. This was the third consecutive fall.\n" +
                "\n" +
                "Separate data showed that producer prices continued to decline in December. Producer prices decreased 2.7 percent from the previous year, following a 3.0 percent decrease in November. Concerns about low inflation is likely to linger for a while, leading policymakers to err in favor of keeping the policy stance supportive in the near-term, economists at Capital Economics said.");
        System.out.println(response);


    }

    enum Sentiment {
        POSITIVE, NEUTRAL, NEGATIVE;
    }

    static class Response {

        private Sentiment sentiment;
        private List<String> keywords;

        @Override
        public String toString() {
            return "Response{" +
                    "sentiment=" + sentiment +
                    ", keywords=" + keywords +
                    '}';
        }
    }




    interface News {

        @SystemMessage("Analyze sentiment of {{article}} forex news article and extract meaningful financial keywords")
        Response analyzeSentimentOf(@V("article") String article);





    }
}
