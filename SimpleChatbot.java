import java.io.IOException;
import java.util.Scanner;

public class SimpleChatbot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Simple Chatbot!");
        System.out.println("You can ask me to open applications or search the web.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (!input.isEmpty()) {
                handleCommand(input);
            }
        }

        scanner.close();
    }

    private static void handleCommand(String command) {
        if (command.startsWith("open ")) {
            openApplication(command.substring(5));
        } else if (command.startsWith("search ")) {
            searchWeb(command.substring(7));
        } else {
            System.out.println("Command not recognized.");
        }
    }

    private static void openApplication(String appName) {
        try {
            ProcessBuilder pb = new ProcessBuilder(appName);
            pb.start();
            System.out.println(appName + " opened successfully.");
        } catch (IOException e) {
            System.out.println("Error opening application: " + e.getMessage());
        }
    }

    private static void searchWeb(String query) {
        try {
            String searchURL = "https://www.google.com/search?q=" + query;
            openWebPage(searchURL);
            System.out.println("Web search for '" + query + "' performed.");
        } catch (IOException e) {
            System.out.println("Error performing web search: " + e.getMessage());
        }
    }

    private static void openWebPage(String url) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb = new ProcessBuilder();

        try {
            if (os.contains("win")) {
                // Windows
                pb.command("rundll32", "url.dll,FileProtocolHandler", url);
            } else if (os.contains("mac")) {
                // macOS
                pb.command("open", url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux or Unix
                pb.command("xdg-open", url);
            } else {
                // Unsupported platform
                throw new UnsupportedOperationException("Unsupported operating system: " + os);
            }
            
            pb.start();
        } catch (Exception e) {
            System.out.println("Error opening web page: " + e.getMessage());
        }
    }
}
