package com.asiafrank.java11;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create("https://app.anitama.net/guide/today/all"))
                                             .timeout(Duration.ofMinutes(2))
                                             .header("Content-Type", "application/json")
                                             .build();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
            HttpClient client = HttpClient.newBuilder()
                                          .version(HttpClient.Version.HTTP_1_1)
                                          .followRedirects(HttpClient.Redirect.NORMAL)
                                          .connectTimeout(Duration.ofSeconds(20))
                                          .sslContext(sslContext)
                                          .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException | InterruptedException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
