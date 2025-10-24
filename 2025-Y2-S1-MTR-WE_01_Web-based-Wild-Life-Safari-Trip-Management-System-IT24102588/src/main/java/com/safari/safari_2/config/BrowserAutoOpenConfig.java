package com.safari.safari_2.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class BrowserAutoOpenConfig {

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowser() {
        try {
            // Wait a moment for the server to fully start
            Thread.sleep(2000);
            
            String url = "http://localhost:8080/home";
            
            // Try to open browser using Desktop API first
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("🌐 Browser opened automatically to: " + url);
            } else {
                // Fallback: Try to open using system command
                openBrowserWithCommand(url);
            }
            
        } catch (Exception e) {
            System.out.println("⚠️  Could not open browser automatically.");
            System.out.println("   Please manually navigate to: http://localhost:8080/home");
            System.out.println("   Error: " + e.getMessage());
        }
    }
    
    private void openBrowserWithCommand(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        
        try {
            if (os.contains("win")) {
                // Windows
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                // macOS
                runtime.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux
                runtime.exec("xdg-open " + url);
            }
            System.out.println("🌐 Browser opened automatically to: " + url);
        } catch (IOException e) {
            System.out.println("⚠️  Could not open browser with system command.");
            System.out.println("   Please manually navigate to: " + url);
        }
    }
}
