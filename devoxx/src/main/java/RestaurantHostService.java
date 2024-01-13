import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.Duration.ofSeconds;

public class RestaurantHostService {
    static ChatLanguageModel model = OpenAiChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            .logRequests(true)
            .logResponses(true)
            .timeout(ofSeconds(60))
            .build();

    public static void main(String[] args) {

        RestaurantHost restaurantHost = AiServices.create(RestaurantHost.class, model);

//        GuestInvitation invite = restaurantHost.invite("italian");
//        System.out.println(invite);
        GuestInvitations invites = restaurantHost.invites(Arrays.asList("italian", "russion", "english", " hebrew"));
        System.out.println(invites);


    }

    static class GuestInvitations {

        private List<GuestInvitation> list;

        public List<GuestInvitation> getList() {
            return list;
        }

        public void setList(List<GuestInvitation> list) {
            this.list = list;
        }
    }
    static class GuestInvitation {

        private String language;
        private String invitation;

        @Override
        public String toString() {
            return "GuestInvitation{" +
                    "language='" + language + '\'' +
                    ", invitation='" + invitation + '\'' +
                    '}';
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getInvitation() {
            return invitation;
        }

        public void setInvitation(String invitation) {
            this.invitation = invitation;
        }
    }

    interface RestaurantHost {

        @SystemMessage("You are a host in a luxury restaurant")
        @UserMessage("Invite guests to sit in the following language: {{language}}")
        GuestInvitation invite(@V("language") String language);


        @SystemMessage("You are a host in a luxury restaurant")
        @UserMessage("Invite guests to sit in the following languages: {{languages}}")
        GuestInvitations invites(@V("languages") List<String> languages);


    }
}
