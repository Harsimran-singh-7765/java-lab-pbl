package gamehub.airiddle;

// --- Official Google GenAI SDK Imports ---
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Part;
import com.google.genai.types.Content;
import java.util.List;
// Removed java.util.concurrent.ExecutionException because it's no longer thrown

public class GeminiClient {

    private final Client client;
    private static final String MODEL_NAME = "gemini-2.5-flash";
    private static final String HARDCODED_API_KEY = "AIzaSyCJMDo3hqKJL2OhDCfNaH8io7azfbNQIBY"; 

    public GeminiClient() {
        try {
            // Instantiate the client using the API key explicitly
            this.client = Client.builder().apiKey(HARDCODED_API_KEY).build();
        } catch (Exception e) {
            System.err.println("FATAL: Failed to initialize Gemini Client. Check SDK JARs and API Key.");
            e.printStackTrace();
            throw new RuntimeException("Gemini Client initialization failed.", e);
        }
    }
    
    /**
     * Helper method to call the model with both a system instruction and a user prompt.
     * The synchronous method call is wrapped in a try/catch for networking/SDK errors.
     */
    private String sendRequest(String userPrompt, String systemInstruction) {
        try {
            
            // 1. Define the System Instruction Content Object
            // The SDK's builder methods make the correct Content object structure
            Content systemInstructionContent = Content.fromParts(Part.fromText(systemInstruction));

            // 2. Define the Configuration (including System Instruction)
            GenerateContentConfig config = GenerateContentConfig.builder()
                .systemInstruction(systemInstructionContent) // Correct structure via SDK
                .build();

            // 3. Define the User Content
            Content userContent = Content.fromParts(Part.fromText(userPrompt));
            
            // 4. Make the API Call (Synchronous/Blocking call)
            GenerateContentResponse response = client.models.generateContent(
                MODEL_NAME, 
                List.of(userContent), // Contents is a list of Content objects
                config 
            ); 

            // 5. Extract and Return Text using the quick accessor method `text()`
            return response.text();
            
        // Catch the general Exception that the synchronous SDK methods throw upon failure.
        } catch (Exception e) { 
            System.err.println("Gemini API Request Error: " + e.getMessage());
            e.printStackTrace();
            return "Error: Could not complete API request. Check console.";
        }
    }


    public String generateRiddle() {
        String userPrompt = "Generate a new, original riddle.";
        String systemInstruction = "You are a witty, creative riddle master for a casual game. Your riddles should be challenging but solvable in 1-2 sentences. Respond only with the riddle text and nothing else.";
        
        return sendRequest(userPrompt, systemInstruction);
    }

    public String checkAnswer(String riddle, String userAnswer) {
        // Embed the riddle into the instruction for the AI judge
        String systemInstruction = String.format(
            "You are an impartial judge. The original riddle was: '%s'. Respond ONLY with the single word 'CORRECT', 'INCORRECT', or 'CLOSE', followed by a colon and a brief, one-sentence justification. For example: CORRECT: The user matched the core concept.",
            riddle
        );
        String userPrompt = String.format("The user's guess for the riddle is: '%s'. Evaluate the guess.", userAnswer);
        
        return sendRequest(userPrompt, systemInstruction);
    }
}