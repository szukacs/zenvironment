package com.useclient.zenvironment.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.useclient.zenvironment.TestBootstrapper;
import com.useclient.zenvironment.configuration.ChatAssistantProperties;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class AssistantService {

    private static final String MODEL = "gpt-3.5-turbo";
    private static final Integer MAX_TOKENS = 256;

    private final OpenAiService assistant;
    private final Map<String, Function<Garden, String>> gardenBasedSuggestions;
    private final List<String> youHaveAllPlantsOptions;
    private final List<String> newPlantSuggestionOptions;
    private final ZenService zenService;

    public AssistantService(ChatAssistantProperties props, ZenService zenService) {
        this.assistant = new OpenAiService(props.getApiKey());
        this.zenService = zenService;
        this.gardenBasedSuggestions = Map.of(
                "what new plant could I add to my garden?", this::getNewPlantSuggestion);
        this.newPlantSuggestionOptions = Arrays.asList(
                "Well, son, if you're lookin' to spruce up that patch of yours, reckon you could plant yourself a fine crop of %s.",
                "Well, youngin', if you aim to freshen up that plot of yours, consider sowin' a good batch of %s in your garden.",
                "Well, if you're hankerin' to liven up that piece of land, think about puttin' in a crop of %s in your garden, partner.",
                "If you're yearnin' to add some zest to that soil of yours, ponder on plantin' a hearty helping of %s in your garden, my friend."
        );
        this.youHaveAllPlantsOptions = Arrays.asList(
                "Seems your patch is practically overflowing with the plants this contraption can handle. Reckon it's high time to dig in and plant yourself a new batch of %s, don't you think?",
                "Looks like your piece of land is just about burstin' with all them plants this contraption can manage. Reckon it's about time to get some new %s in the ground.",
                "Well, I reckon your patch is bursting at the seams with all them plants this contraption can handle. Might be time to plant a new %s.",
                "Well, it seems like your plot is plum full with all them plants this gadget can manage. Perhaps it's high time to sow a fresh batch of %s."
        );
    }

    public String getAiAssistance(String message) {
        var chatCompletionRequest = ChatCompletionRequest.builder()
                .model(MODEL)
                .messages(generateAiChatMessage(message))
                .maxTokens(MAX_TOKENS)
                .build();
        var response = assistant.createChatCompletion(chatCompletionRequest);
        var choices = response.getChoices();
        if (CollectionUtils.isEmpty(choices)) {
            throw new RuntimeException("no response");
        }
        return choices.get(0).getMessage().getContent();
    }

    private List<ChatMessage> generateAiChatMessage(String message) {
        return Stream.of("answer in one sentence",
                        "answer like an old american farmer",
                        "use imperial and metric units",
                        message
                )
                .map(messageContent -> new ChatMessage(ChatMessageRole.USER.value(), messageContent))
                .toList();
    }

    public String getAssistance(String message) {
        if (!gardenBasedSuggestions.containsKey(message)) {
            return getAiAssistance(message);
        }
        var suggestionFunction = gardenBasedSuggestions.get(message);
        var myGarden = zenService.getGardenByName(TestBootstrapper.MY_GARDEN_NAME);
        return suggestionFunction.apply(myGarden);
    }

    public List<String> getGardenBasedAssistanceQuestions() {
        return gardenBasedSuggestions.keySet().stream().toList();
    }

    public String getNewPlantSuggestion(Garden myGarden) {
        var myPlants = myGarden.getPlants().stream()
                .map(Plant::getPlantType)
                .map(PlantType::getName)
                .toList();
        var missingPlants = zenService.getAllPlantTypes().stream()
                .map(PlantType::getName)
                .filter(plantName -> !myPlants.contains(plantName))
                .toList();
        if (missingPlants.isEmpty()) {
            var randomExistingPlant = getRandom(myPlants);
            return String.format(getRandom(youHaveAllPlantsOptions), randomExistingPlant);
        }

        var randomMissingPlant = getRandom(missingPlants);
        return String.format(getRandom(newPlantSuggestionOptions), randomMissingPlant);
    }

    private String getRandom(List<String> options) {
        return options.get((int) (Math.random() * options.size()));
    }
}
