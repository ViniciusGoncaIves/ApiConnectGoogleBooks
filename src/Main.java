import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Digite o nome do livro: ");
            var livro = scanner.nextLine().trim();
            if (livro.isEmpty()) {
                System.out.println("Você não digitou nada.");
                return;
            }

            var livroEncoded = URLEncoder.encode(livro, StandardCharsets.UTF_8);
            var key = "Your key";
            var url = "https://www.googleapis.com/books/v1/volumes?q=" + livroEncoded + "&key=" + key;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Requisição feita com sucesso!");
                System.out.println("Resposta: " + response.body());
            } else {
                System.out.println("Erro na requisição: " + response.statusCode());
                System.out.println("Detalhes: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao fazer a requisição: " + e.getMessage());
        }
    }
}