package dev.montianus.listeners;

import java.util.List;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class ReadyListener extends ListenerAdapter {
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
              .setDefaultPermissions(
                  DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL)))
          .queue();
    }
  }
}
