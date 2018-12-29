package pl.msco.mscmultilangtextcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Core implements CommandExecutor{

	public final FileConfiguration cfg = Main.getInstance().getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!cmd.getName().equalsIgnoreCase("multitextcmd")) { return true; }
		
		Player player = null;
		//Get player from sender
		if(args.length == 1) {
			if(sender instanceof Player) { player = (Player) sender; }
			else { sender.sendMessage("not a player"); }
		}
		//Or get player from argument (if it is not sender)
		else if(args.length == 2) { 
			player = Main.getInstance().getServer().getPlayerExact(args[1]);
			if(player == null) {
				sender.sendMessage("Player is offline!");
				return true;
			}
		}
		else {
			sender.sendMessage("Too Many or too few arguments. Usage: /multitextcmd <msg_id> <optional: player>");
			return true;
		}
		
		
		if(cfg.get("messages."+args[0]) == null) {
			sender.sendMessage("Error: No command with this id!");
			return true;
		}
		
		//String pluginname = cfg.getString("pluginname");
		String userlang = player.getLocale().toLowerCase();
		String userlangshort = userlang.substring(0,2);
		String messagepath = "messages." + args[0];
		
		List<String> message = new ArrayList<String>();
		
		List<String> langspriority = new ArrayList<String>();
		langspriority.add(userlangshort);
		langspriority.add(userlang);	
		
		for(String lang : cfg.getStringList("defaultlangpriority"))
			langspriority.add(lang);
		for(String lang : langspriority) {
			message = this.getLangMsg(messagepath , lang);
			if(!message.isEmpty())
				break;
		}
		if(!message.isEmpty())
			for(String line : message)
				player.sendMessage(line);
		else
			System.out.println("Message with id " + args[0] + " does not have a msg in default lang." + userlang + " " + userlangshort);
		
		return true;
	}
	private List<String> getLangMsg(String msgid, String lang) {
	
		List<String> msg = new ArrayList<String>();
		
		if(cfg.getStringList( msgid + "." + lang) != null) {
			msg =  cfg.getStringList( msgid + "." + lang);
			//For some reason getStringList take simple string as empty list, so we have to detect is string or list by isEmpty 
			if(!msg.isEmpty())
				return msg;
			msg.add(cfg.getString( msgid + "." + lang));
			return msg;
		}
		return null;
	}

	
}

	