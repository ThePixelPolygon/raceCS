package omg.lol.jplexer.race.command;

import static omg.lol.jplexer.race.Race.CHAT_PREFIX;

import kong.unirest.json.JSONObject;
import omg.lol.jplexer.race.CommandUtils;
import omg.lol.jplexer.race.Race;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.md_5.bungee.api.ChatColor;
import kong.unirest.Unirest;

// All command classes need to implement the CommandExecutor interface to be a proper command!
public class RaceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {


		// checks if there are no arguments at all (/command)
		if (args.length == 0) {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Invalid usage - there are no arguments.");
			return false;
		}
		switch (args[0].toLowerCase()) {
			case "players":
				viewUsers(sender);
				return true;
			case "adduser":
				addUser(sender, args);
				return true;
			case "removeuser":
				removeUser(sender, args);
				return true;
			case "arrived":
				arrive(sender, args);
				return true;
			case "completion":
				complete(sender, args);
				return true;
			default:
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, \"" + args[0] + "\" is not a valid verb.");
				return true;
		}

	}
	void viewUsers(CommandSender sender) {
		JSONObject response = Unirest.get(Race.API_BASE + "/users").queryString("auth", Race.AUTH_TOKEN).asJson().getBody().getObject();
		sender.sendMessage(response.toString());
	}
	void addUser(CommandSender sender, String[] args) {
		if(sender.hasPermission("racecs.jplexer")) {
			if (args.length != 2) {
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you'll need to specify Name.");

			} else {
				String user = CommandUtils.getTarget(sender, args[1]).getName();

				Unirest.post(Race.API_BASE + "/addUser/{player}")
						.routeParam("player", user)
						.queryString("auth", Race.AUTH_TOKEN)
						.asString();
				sender.sendMessage(CHAT_PREFIX + user + " was added.");
			}
		} else {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you can't use this.");
		}

	}
	void removeUser(CommandSender sender, String[] args)  {
		if(sender.hasPermission("racecs.jplexer")) {
			if (args.length != 2) {
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you'll need to specify Name.");

			} else {
				String user = CommandUtils.getTarget(sender, args[1]).getName();

				Unirest.post(Race.API_BASE + "/removeUser/{player}")
						.routeParam("player", user)
						.queryString("auth", Race.AUTH_TOKEN)
						.asString();
				sender.sendMessage(CHAT_PREFIX + user + " was removed.");
			}
		} else {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you can't use this.");
			}

	}
	void arrive(CommandSender sender, String[] args)  {
		if (sender.hasPermission("racecs.jplexer")) {
			if (args.length <= 2) {
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you'll need to specify Name and Location.");

			} else if (args.length != 3) {
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you'll need to specify Location.");

			} else {
				String user = CommandUtils.getTarget(sender, args[1]).getName();

				Unirest.post(Race.API_BASE + "/arrive/{player}/{location}")
						.routeParam("player", user)
						.routeParam("location", args[2])
						.queryString("auth", Race.AUTH_TOKEN)
						.asString();
			}
		} else {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you can't use this.");

		}

	}
	void complete(CommandSender sender, String[] args)  {
		if (sender.hasPermission("racecs.jplexer")) {
			if (args.length <= 2) {
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you'll need to specify Name and Place.");

			} else if (args.length != 3) {
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you'll need to specify Place.");

			} else {
				String user = CommandUtils.getTarget(sender, args[1]).getName();

				Unirest.post(Race.API_BASE + "/completion/{player}/{place}")
						.routeParam("player", user)
						.routeParam("place", args[2])
						.queryString("auth", Race.AUTH_TOKEN)
						.asString();
			}
		} else {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Sorry, you can't use this.");

		}

	}
}


