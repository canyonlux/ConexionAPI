package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BuscadorPerros {

        public static void main(String[] args) {
            try {
                // Genera un número aleatorio entre 1 y 30 (o el rango que desees)
                int numberOfImages = (int) (Math.random() * 30) + 1;

                // Modifica la URL para obtener un número aleatorio de imágenes
                String apiUrl = "https://dog.ceo/api/breeds/image/random/" + numberOfImages;
                String getResponse = sendGetRequest(apiUrl); // Realiza la solicitud GET a la APIpublic class BuscadorPerros {

                System.out.println("Respuesta GET:");
                System.out.println(getResponse);

                // Busca la palabra "bull" en las imágenes
                searchForBull(getResponse);

                // Cuenta la cantidad de imágenes en la respuesta
                int imageCount = countImagesInResponse(getResponse); // Cuenta la cantidad de imágenes en la respuesta
                System.out.println("La cantidad de imágenes en la respuesta es: " + imageCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    // Función para realizar una solicitud GET a la API y devolver la respuesta como una cadena
    public static String sendGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl); // Crea una nueva URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Abre una conexión HTTP
        connection.setRequestMethod("GET"); // Establece el método de solicitud en GET
        connection.connect(); // Conecta la URL

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Ocurrió un error " + responseCode);
        } else {
            StringBuilder jsonResponse = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                jsonResponse.append(scanner.nextLine());
            }

            scanner.close();
            return jsonResponse.toString();
        }
    }

    // Función para buscar la palabra "bull" en la respuesta
    public static void searchForBull(String response) {
        boolean found = response.contains("bull");
        String message = found ? "Se encontró la palabra 'bull' en la respuesta." : "La palabra 'bull' no se encontró en la respuesta.";
        System.out.println(message);
    }

    // Función para contar la cantidad de imágenes en la respuesta
    public static int countImagesInResponse(String response) {
        Pattern pattern = Pattern.compile("\"(http[^\"]+)\""); // Crea un patrón para buscar todas las URL de las imágenes
        Matcher matcher = pattern.matcher(response);  // Crea un matcher para buscar las URL de las imágenes en la respuesta

        int count = (int) matcher.results().count();
        return count;
    }
}
