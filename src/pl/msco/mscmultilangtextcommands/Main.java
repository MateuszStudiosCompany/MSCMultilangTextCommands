package pl.msco.mscmultilangtextcommands;

import org.bukkit.plugin.java.JavaPlugin;
import pl.msco.mscmultilangtextcommands.Core;


public class Main extends JavaPlugin{
	
	private static Main instance;
		
	@Override public void onEnable() {	
		instance = this;
		
		System.out.println("================================");
		System.out.println("MSC Multilang Text Commands 0.1");
		System.out.println("================================");
		
		getCommand("multitextcmd").setExecutor(new Core());
		this.saveDefaultConfig();

		
	}
	
	public void onDistable() {
		System.out.println("Goodbye!");
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	
};