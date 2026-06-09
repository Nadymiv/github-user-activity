import java.net.http.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Please provide a GitHub username as an argument.");
            return;
        }
        String username = args[0];

        String url = "https://api.github.com/users/" + username + "/events";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // System.out.println("Events for user: " + username);
            // System.out.println(response.body());
            Pattern pattern = Pattern.compile("\"type\":\"(.*?)\".*?\"repo\":.*?\"name\":\"(.*?)\"");
            Matcher matcher = pattern.matcher(response.body());
    
            while (matcher.find()) {
                String eventType = matcher.group(1);
                String repoName = matcher.group(2);
                String action = switch (eventType) {
                    case "WatchEvent" -> "Starred";
                    case "CreateEvent" -> "Created a repository";
                    case "PushEvent" -> {
                        int commitCount = 1;

                        String remainingText = response.body().substring(matcher.end());

                        Pattern sizePattern = Pattern.compile("\"size\":(\\d+)");
                        Matcher sizeMatcher = sizePattern.matcher(remainingText);

                        if (sizeMatcher.find()) {
                            String sizeValue = sizeMatcher.group(1);
                            commitCount = Integer.parseInt(sizeValue);
                        }
                        yield "Pushed " + commitCount + " commit(s) to";
                    }
                    case "IssuesEvent" -> "Opened an issue in";
                    default -> "Did something with";
                };

                System.out.println(action + " " + repoName);
            }
        } else {
            System.out.println("Failed to fetch events. HTTP Status Code: " + response.statusCode());
        }


    }
}
