package dev.montianus;

import dev.montianus.listeners.*;

import java.io.FileInputStream;
import java.util.Properties;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App {
  public static void main(String[] args) {

    Properties prop = new Properties();
    String rootPath = "./";
    String propertiesPath = rootPath + "client.properties";
    try {
      prop.load(new FileInputStream(propertiesPath));
    } catch (Exception e) {
      System.out.println(e);
      return;
    }
    String token = prop.getProperty("token");

    JDA api = JDABuilder.createDefault(token)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .build();

    api.addEventListener(new MessageListener());
    api.addEventListener(new ReadyListener());
    api.addEventListener(new SlashListener());
  }
}

