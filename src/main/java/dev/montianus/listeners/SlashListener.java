package dev.montianus.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashListener extends ListenerAdapter {
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
