package me.kippy.special;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin implements Listener {
	private Logger logger = getLogger();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(e.getEntity() instanceof Player) {
					Player p = (Player) e.getEntity();
					double arrow_height = arrow.getLocation().getY();
					double player_head_begin = p.getLocation().getY() + 1.35;
					if(arrow_height > player_head_begin) {
						e.setDamage(e.getDamage() + getConfig().getDouble("HeadshotExtraDamage"));
					}
				}
			}
		}else if(e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();
			if(e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				double damager_yaw = damager.getLocation().getYaw();
				double p_yaw = p.getLocation().getYaw();
				if(((damager_yaw >= 0) && (damager_yaw <= 45)) && ((p_yaw <= 0) && (p_yaw >= -45)) || ((damager_yaw <= 0) && (damager_yaw >= -45)) && ((p_yaw >= 0) && (p_yaw <= 45))) {
					damager_yaw += 45;
					p_yaw += 45;
					if(Math.abs(damager_yaw - p_yaw) <= 45) {
						e.setDamage(e.getDamage() + getConfig().getDouble("BackstabExtraDamage"));
					}
				}else{
					damager_yaw = damager_yaw < 0 ? (180 - (damager_yaw - damager_yaw - damager_yaw)) + 180 : damager_yaw;
					p_yaw = p_yaw < 0 ? (180 - (p_yaw - p_yaw - p_yaw)) + 180 : p_yaw;
					if(Math.abs(damager_yaw - p_yaw) <= 45) {
						e.setDamage(e.getDamage() + getConfig().getDouble("BackstabExtraDamage"));
					}
				}
			}
		}
	}
	
	@Override
	public void onDisable() {
		this.logger.info("Special Hits has been disabled!");
	}
	
	@Override
	public void onEnable() {
		this.logger.info("Special Hits has been enabled!");
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		saveDefaultConfig();
		reloadConfig();
	}

}
