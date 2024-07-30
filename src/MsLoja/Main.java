package MsLoja;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	private static Main instance;
	private ConfigHandler pos = null;

	public static ArrayList<String> fly = new ArrayList<>();
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		instance = this;
		setupConfig();
		ConsoleCommandSender b = Bukkit.getConsoleSender();
		b.sendMessage("§8=-=-=-=-=-=-=-=-=-=-=-=-=-==-");
		b.sendMessage("§7Versao:7-a1.0");
		b.sendMessage("§7Autor: §9yurirp4");
		b.sendMessage("§bSkype: Filipe Silva amy");
		b.sendMessage("§7stats Do Plugin: §aAtivo");
		b.sendMessage("§8=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
	}

	public static Main getinstance() {
		return instance;
	}

	public void onDisable() {
		ConsoleCommandSender b = Bukkit.getConsoleSender();
		b.sendMessage("§8=-=-=-=-=-=-=-=-=-=-=-=-=-==-==-");
		b.sendMessage("§7Versao:§a1.0");
		b.sendMessage("§7Autor: §9yurirp4");
		b.sendMessage("§bSkype: Filipe Silva amy");
		b.sendMessage("§7stats Do Plugin: §4Desativado");
		b.sendMessage("§8=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=");
	}

	private void setupConfig() {
		try {
			this.pos = new ConfigHandler(this, "posicoes.yml");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player j = (Player) sender;
		if (!(sender instanceof Player)) {
			j.sendMessage("§4Voce Precisa Ser Um Player Para Usar Esse Comando");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("setloja")) {
			if (!j.hasPermission("setloja.usar")) {
				j.sendMessage("§cVoce Nao Tem Permissao Para Usar Este Comando");
				return true;
			}
			int x = (int) j.getLocation().getX();
			int y = (int) j.getLocation().getY();
			int z = (int) j.getLocation().getZ();
			World w = j.getWorld();
			this.pos.set("loja.x", Integer.valueOf(x));
			this.pos.set("loja.y", Integer.valueOf(y));
			this.pos.set("loja.z", Integer.valueOf(z));
			this.pos.set("loja.world", w.getName());
			this.pos.trySave();
			j.sendMessage("§aLoja Foi Setada Com Sucesso");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("loja")) {
			j.sendMessage(getConfig().getString("Menssagen_Normal").replace("&", "§"));
			int x = this.pos.getInt("loja.x");
			int y = this.pos.getInt("loja.y");
			int z = this.pos.getInt("loja.z");
			Location l = new Location(Bukkit.getWorld(this.pos.getString("loja.world")), x, y, z);
			j.playSound(j.getLocation(), Sound.SUCCESSFUL_HIT, 5.0F, 5.0F);
			j.teleport(l);
		}
		if (cmd.getName().equalsIgnoreCase("setvip")) {
			if (!j.hasPermission("setvip.usar")) {
				j.sendMessage("§cVoce Nao Tem Permissao Para Usar Este Comando");
			}
			int x = (int) j.getLocation().getX();
			int y = (int) j.getLocation().getY();
			int z = (int) j.getLocation().getZ();
			World w = j.getWorld();
			this.pos.set("lojavip.x", Integer.valueOf(x));
			this.pos.set("lojavip.y", Integer.valueOf(y));
			this.pos.set("lojavip.z", Integer.valueOf(z));
			this.pos.set("lojavip.world", w.getName());
			this.pos.trySave();
			j.sendMessage("§bVip Foi Setada Com Sucesso");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("vip")) {
			if (!j.hasPermission("vip.usar")) {
				j.sendMessage(getConfig().getString("No_Acess").replace("&", "§"));
				return true;
			}
			int x = this.pos.getInt("lojavip.x");
			int y = this.pos.getInt("lojavip.y");
			int z = this.pos.getInt("lojavip.z");
			Location l = new Location(Bukkit.getWorld(this.pos.getString("lojavip.world")), x, y, z);
			j.teleport(l);
			for (String msg : getConfig().getStringList("Menssagen_Vip")) {
				j.sendMessage(msg.replace("&", "§"));
				fly.add(j.getName());
				j.setAllowFlight(true);
				j.playSound(j.getLocation(), Sound.LEVEL_UP, 10, 10);
			}
			return false;
		}
		return false;
	}
	@EventHandler
	public void fly(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (fly.contains(p.getName())) {
			if (e.getMessage().equalsIgnoreCase("/spawn") || e.getMessage().startsWith("/warp"));  e.getMessage().equals("/loja");
			e.getMessage().equals("tpa");
			e.getMessage().startsWith("tp");
			e.getMessage().startsWith("mv");
			e.getMessage().startsWith("home");
			e.getMessage().startsWith("/back");
			e.getMessage().equals("/kill");
			e.getMessage().equals("/evento");
			e.getMessage().equals("/mina");
			e.getMessage().equals("/lobby");
			e.getMessage().startsWith("/plot");
				fly.remove(p.getName());
				if(p.isFlying() || p.getAllowFlight()) {
		            p.setAllowFlight(false);
		            p.setFlying(false);
		        }
			}
		}
	}
