import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import comparison.SimilarityCalculator;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class PlagiarismServer {

    public static void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Serve the frontend HTML
        server.createContext("/", new StaticFileHandler());

        // API endpoint: POST /check with multipart form data (file1, file2)
        server.createContext("/check", new CheckHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Server started at http://localhost:" + port);
    }

    // ---------------------------------------------------------------
    // Serves index.html from the project root
    // ---------------------------------------------------------------
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            if (!method.equalsIgnoreCase("GET")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            File htmlFile = new File("index.html");
            if (!htmlFile.exists()) {
                String msg = "index.html not found";
                exchange.sendResponseHeaders(404, msg.length());
                exchange.getResponseBody().write(msg.getBytes());
                exchange.getResponseBody().close();
                return;
            }

            byte[] bytes = Files.readAllBytes(htmlFile.toPath());
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        }
    }

    // ---------------------------------------------------------------
    // Handles POST /check — parses multipart, extracts text from files,
    // runs similarity, returns JSON
    // ---------------------------------------------------------------
    static class CheckHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // CORS headers so browser fetch works
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                sendJson(exchange, 405, "{\"error\":\"Method not allowed\"}");
                return;
            }

            try {
                // Read the raw body
                InputStream is = exchange.getRequestBody();
                byte[] bodyBytes = is.readAllBytes();
                String contentType = exchange.getRequestHeaders().getFirst("Content-Type");

                // Extract the multipart boundary
                String boundary = extractBoundary(contentType);
                if (boundary == null) {
                    sendJson(exchange, 400, "{\"error\":\"Missing multipart boundary\"}");
                    return;
                }

                // Parse the two file parts
                Map<String, String> parts = parseMultipart(bodyBytes, boundary);
                String text1 = parts.get("file1");
                String text2 = parts.get("file2");

                if (text1 == null || text2 == null) {
                    sendJson(exchange, 400, "{\"error\":\"Two files (file1, file2) are required\"}");
                    return;
                }

                double similarity = SimilarityCalculator.calculateSimilarity(text1, text2);
                // Round to 2 decimal places
                similarity = Math.round(similarity * 100.0) / 100.0;

                String json = String.format("{\"similarity\": %.2f}", similarity);
                sendJson(exchange, 200, json);

            } catch (Exception e) {
                e.printStackTrace();
                sendJson(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }

        private void sendJson(HttpExchange exchange, int status, String json) throws IOException {
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(status, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        }

        private String extractBoundary(String contentType) {
            if (contentType == null) return null;
            for (String part : contentType.split(";")) {
                part = part.trim();
                if (part.startsWith("boundary=")) {
                    return part.substring("boundary=".length()).trim();
                }
            }
            return null;
        }

        /**
         * Minimal multipart/form-data parser.
         * Returns a map of field-name -> text content.
         */
        private Map<String, String> parseMultipart(byte[] body, String boundary) throws IOException {
            Map<String, String> result = new HashMap<>();
            String delimiter = "--" + boundary;
            String bodyStr = new String(body, StandardCharsets.ISO_8859_1);

            String[] parts = bodyStr.split(delimiter);
            for (String part : parts) {
                if (part.trim().isEmpty() || part.trim().equals("--")) continue;

                // Split headers from body: blank line separates them
                int headerEnd = part.indexOf("\r\n\r\n");
                if (headerEnd == -1) continue;

                String headers = part.substring(0, headerEnd);
                // +4 to skip the \r\n\r\n, and trim trailing \r\n from boundary
                String content = part.substring(headerEnd + 4);
                if (content.endsWith("\r\n")) {
                    content = content.substring(0, content.length() - 2);
                }

                // Extract field name from Content-Disposition
                String fieldName = null;
                for (String header : headers.split("\r\n")) {
                    if (header.toLowerCase().startsWith("content-disposition")) {
                        for (String token : header.split(";")) {
                            token = token.trim();
                            if (token.startsWith("name=")) {
                                fieldName = token.substring(5).replace("\"", "").trim();
                            }
                        }
                    }
                }

                if (fieldName != null) {
                    result.put(fieldName, new String(content.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }
            }
            return result;
        }
    }
}
