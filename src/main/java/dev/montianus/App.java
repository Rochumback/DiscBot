package dev.montianus;

import java.io.FileInputStream;
import java.util.Properties;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
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

class MessageListener extends ListenerAdapter {
  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.isFromType(ChannelType.PRIVATE)) {
      System.out.printf("[PM] %s : %s\n", event.getAuthor().getName(),
          event.getMessage().getContentDisplay());
    } else {
      System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
          event.getGuildChannel().getName(), event.getMember().getEffectiveName(),
          event.getMessage().getContentDisplay());
    }

  }
}

class ReadyListener extends ListenerAdapter {
  @Override
  public void onReady(ReadyEvent event) {
    List<Guild> guilds = event.getJDA().getGuilds();
    for (Guild guild : guilds) {
      System.out.println(guild.getName());
      guild.updateCommands().addCommands(
          Commands.slash("ping", "replies with Pong").setDefaultPermissions(
              DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL)),
          Commands.slash("say", "say a message")
              .addOption(OptionType.STRING, "message", "message the bot will saye", true)
              .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL))).queue();
    }
  }
}

class SlashListener extends ListenerAdapter {
  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

    if (event.getName().equals("ping")) {
      event.reply("pong").queue();
      return;
    }

    if (event.getName().equals("say")) {
      event.reply(event.getOption("message").getAsString()).queue();
      return;
    }

  }

}
