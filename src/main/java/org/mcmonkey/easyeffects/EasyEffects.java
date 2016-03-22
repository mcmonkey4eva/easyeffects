package org.mcmonkey.easyeffects;

import net.minecraft.server.v1_9_R1.EnumParticle;
import net.minecraft.server.v1_9_R1.PacketPlayOutWorldParticles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

// monkey: this is really old code, and it's not the best.
// I apologize for tricking you into looking at it :(
public class EasyEffects extends JavaPlugin implements Listener {

    public static enum ParticleEffectType {
        HEART, FLAME, MUSIC_NOTE, WATER_DRIP, LAVA_DRIP, WITCH, SMOKE, ANGRY_VILLAGER, HAPPY_VILLAGER,
        CRITICAL, ENCHANTMENT, ENDERMAN
    }

    public final static String Prefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "Easy Effects"
            + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN;

    public final static String Title = ChatColor.RED.toString() + "Easy Effects";

    public static HashMap<UUID, ParticleEffectType> ChosenEffect;

    public static HashMap<UUID, Integer> CurrentAngles;

    public Inventory generateMenu(boolean status) {
        // Inventory menu
        Inventory Menu = getServer().createInventory(null, 4 * 9, Title);
        // Slot 0-0: Heart
        ItemStack Heart = new ItemStack(Material.REDSTONE, 1);
        ItemMeta meta = Heart.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED.toString() + "Heart");
        Heart.setItemMeta(meta);
        Menu.setItem(0, Heart);
        // Slot 0-1: Music_Note
        ItemStack Music_Note = new ItemStack(Material.NOTE_BLOCK, 1);
        meta = Music_Note.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GREEN.toString() + "Music Note");
        Music_Note.setItemMeta(meta);
        Menu.setItem(1, Music_Note);
        // Slot 0-2: Flame
        ItemStack Flame = new ItemStack(Material.BLAZE_POWDER, 1);
        meta = Flame.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD.toString() + "Flame");
        Flame.setItemMeta(meta);
        Menu.setItem(2, Flame);
        // Slot 0-3: Water_Drip
        ItemStack Water_Drip = new ItemStack(Material.WATER_BUCKET, 1);
        meta = Water_Drip.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE.toString() + "Water Drip");
        Water_Drip.setItemMeta(meta);
        Menu.setItem(3, Water_Drip);
        // Slot 0-4: Lava_Drip
        ItemStack Lava_Drip = new ItemStack(Material.LAVA_BUCKET, 1);
        meta = Lava_Drip.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD.toString() + "Lava Drip");
        Lava_Drip.setItemMeta(meta);
        Menu.setItem(4, Lava_Drip);
        // Slot 0-5: Witch
        ItemStack Witch = new ItemStack(Material.POTION, 1);
        meta = Witch.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE.toString() + "Witch");
        Witch.setItemMeta(meta);
        Menu.setItem(5, Witch);
        // Slot 0-6: Smoke
        ItemStack Smoke = new ItemStack(Material.INK_SACK, 1);
        meta = Smoke.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY.toString() + "Smoke");
        Smoke.setItemMeta(meta);
        Menu.setItem(6, Smoke);
        // Slot 0-7: Angry_Villager
        ItemStack Angry_Villager = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        meta = Angry_Villager.getItemMeta();
        meta.setDisplayName(ChatColor.RED.toString() + "Angry Villager");
        Angry_Villager.setItemMeta(meta);
        Menu.setItem(7, Angry_Villager);
        // Slot 0-8: Happy_Villager
        ItemStack Happy_Villager = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
        meta = Happy_Villager.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN.toString() + "Happy Villager");
        Happy_Villager.setItemMeta(meta);
        Menu.setItem(8, Happy_Villager);
        // Slot 1-0, 1-1: Empty
        // Slot 1-2: Notification pane
        // monkey note: irrelevant to modern plugin release
        /*
        ItemStack Notification_Pane1 = new ItemStack(Material.THIN_GLASS, 1);
        meta = Notification_Pane1.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN.toString() + "Coming Soon");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Suggest ideas on our forums!"));
        Notification_Pane1.setItemMeta(meta);
        Menu.setItem(9 + 2, Notification_Pane1);
        */
        // Slot 1-3: Critical
        ItemStack Critical = new ItemStack(Material.WEB, 1);
        meta = Critical.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY.toString() + "Critical");
        Critical.setItemMeta(meta);
        Menu.setItem(9 + 3, Critical);
        // Slot 1-4: Enchantment
        ItemStack Enchantment = new ItemStack(Material.BOOK, 1);
        meta = Enchantment.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW.toString() + "Enchantment");
        Enchantment.setItemMeta(meta);
        Menu.setItem(9 + 4, Enchantment);
        // Slot 1-5: Enderman
        ItemStack Enderman = new ItemStack(Material.EYE_OF_ENDER, 1);
        meta = Enderman.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + "Enderman");
        Enderman.setItemMeta(meta);
        Menu.setItem(9 + 5, Enderman);
        // Slot 1-6: Notification pain
        // monkey note: irrelevant to modern plugin release
        /*
        ItemStack Notification_Pane2 = new ItemStack(Material.THIN_GLASS, 1);
        meta = Notification_Pane2.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN.toString() + "Coming Soon");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Suggest ideas on our forums!"));
        Notification_Pane2.setItemMeta(meta);
        Menu.setItem(9 + 6, Notification_Pane2);
        */
        // Slot 1-7, 1-8: Empty
        // Slot 2-0 through 2-8: Empty
        // Slot 3-0: Disabler
        ItemStack Disabler = new ItemStack(Material.STAINED_CLAY, 1, (short)14);
        meta = Disabler.getItemMeta();
        meta.setDisplayName(ChatColor.RED.toString() + "OFF");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Disables your current effect!"));
        Disabler.setItemMeta(meta);
        Menu.setItem(9 * 3, Disabler);
        // Slot 3-1 through 3-7: Empty
        // Slot 3-8: Status
        ItemStack Status = new ItemStack(Material.STAINED_CLAY, 1, (short)13);
        meta = Status.getItemMeta();
        if (status) {
            meta.setDisplayName(ChatColor.GREEN.toString() + "Effect Status" +
                    ChatColor.DARK_GRAY.toString() + ": " + ChatColor.DARK_GREEN.toString() + "ON");
        }
        else {
            meta.setDisplayName(ChatColor.GREEN.toString() + "Effect Status" +
                    ChatColor.DARK_GRAY.toString() + ": " + ChatColor.RED.toString() + "OFF");
        }
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Shows if you have an effect on or off!"));
        Status.setItemMeta(meta);
        Menu.setItem(9 * 3 + 8, Status);
        // Final return
        return Menu;
    }

    @Override
    public void onEnable() {
        ChosenEffect = new HashMap<UUID, ParticleEffectType>();
        CurrentAngles = new HashMap<UUID, Integer>();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("EasyEffects loaded!");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                runWholeTick();
            }
        }, 2, 2);
    }

    void runWholeTick() {
        for (Player player: getServer().getOnlinePlayers()) {
            if (ChosenEffect.containsKey(player.getUniqueId())) {
                ParticleEffectType pet = ChosenEffect.get(player.getUniqueId());
                Location base = player.getLocation().clone().add(0, 2, 0);
                Integer angle = CurrentAngles.get(player.getUniqueId());
                angle += 30;
                if (angle > 360) {
                    angle -= 360;
                }
                CurrentAngles.put(player.getUniqueId(), angle);
                base = base.add(Math.cos(Math.toRadians(angle)) * 0.4f, 0, Math.sin(Math.toRadians(angle)) * 0.4f);
                playEffect(pet, base);
            }
        }
    }

    void playEffect(ParticleEffectType type, Location location) {
        EnumParticle particle;
        switch (type) {
            case HEART:
                particle = EnumParticle.HEART;
                break;
            case FLAME:
                particle = EnumParticle.FLAME;
                break;
            case MUSIC_NOTE:
                particle = EnumParticle.NOTE;
                break;
            case WATER_DRIP:
                particle = EnumParticle.DRIP_WATER;
                break;
            case LAVA_DRIP:
                particle = EnumParticle.DRIP_LAVA;
                break;
            case WITCH:
                particle = EnumParticle.SPELL_WITCH;
                break;
            case SMOKE:
                particle = EnumParticle.SMOKE_NORMAL;
                break;
            case ANGRY_VILLAGER:
                particle = EnumParticle.VILLAGER_ANGRY;
                break;
            case HAPPY_VILLAGER:
                particle = EnumParticle.VILLAGER_HAPPY;
                break;
            case CRITICAL:
                particle = EnumParticle.CRIT;
                break;
            case ENCHANTMENT:
                particle = EnumParticle.ENCHANTMENT_TABLE;
                break;
            case ENDERMAN:
                particle = EnumParticle.PORTAL;
                break;
            default:
                particle = EnumParticle.HEART;
                break;
        }
        int maxRadius = 30;
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, false,
                (float)location.getX(), (float)location.getY(), (float)location.getZ(), 0, 0, 0, 0f, 1);
        for (Player player: getServer().getOnlinePlayers()) {
            if (player.getWorld() == location.getWorld() &&
                    player.getLocation().distanceSquared(location) < maxRadius * maxRadius) {
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdName, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("particle")) {
            getLogger().info("Unknown command sent to EasyEffects!");
            return false;
        }
        else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.GREEN + "Opening particle effect menu...");
                player.openInventory(generateMenu(ChosenEffect.containsKey(player.getUniqueId())));
            }
            else {
                sender.sendMessage("Console can't execute /particle currently!");
            }
        }
        return true;
    }

    public void setEffectType(ParticleEffectType type, Player player, String effectName) {
        ChosenEffect.put(player.getUniqueId(), type);
        CurrentAngles.put(player.getUniqueId(), 0);
        player.sendMessage(Prefix + "Set effect to " + effectName + "!");
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(Title)) {
            event.setCancelled(true);
        }
        else {
            return;
        }
        if (event.getClick() == ClickType.LEFT) {
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            boolean success = true;
            if (item.getType() == Material.REDSTONE) {
                setEffectType(ParticleEffectType.HEART, player, "Heart");
            }
            else if (item.getType() == Material.NOTE_BLOCK) {
                setEffectType(ParticleEffectType.MUSIC_NOTE, player, "Music Note");
            }
            else if (item.getType() == Material.BLAZE_POWDER) {
                setEffectType(ParticleEffectType.FLAME, player, "Flame");
            }
            else if (item.getType() == Material.WATER_BUCKET) {
                setEffectType(ParticleEffectType.WATER_DRIP, player, "Water Drip");
            }
            else if (item.getType() == Material.LAVA_BUCKET) {
                setEffectType(ParticleEffectType.LAVA_DRIP, player, "Lava Drip");
            }
            else if (item.getType() == Material.POTION) {
                setEffectType(ParticleEffectType.WITCH, player, "Witch");
            }
            else if (item.getType() == Material.INK_SACK) {
                setEffectType(ParticleEffectType.SMOKE, player, "Smoke");
            }
            else if (item.getType() == Material.STAINED_GLASS_PANE
                    && item.getDurability() == 14) {
                setEffectType(ParticleEffectType.ANGRY_VILLAGER, player, "Angry Villager");
            }
            else if (item.getType() == Material.STAINED_GLASS_PANE
                    && item.getDurability() == 5) {
                setEffectType(ParticleEffectType.HAPPY_VILLAGER, player, "Happy Villager");
            }
            else if (item.getType() == Material.WEB) {
                setEffectType(ParticleEffectType.CRITICAL, player, "Critical");
            }
            else if (item.getType() == Material.BOOK) {
                setEffectType(ParticleEffectType.ENCHANTMENT, player, "Enchantment");
            }
            else if (item.getType() == Material.EYE_OF_ENDER) {
                setEffectType(ParticleEffectType.ENDERMAN, player, "Enderman");
            }
            else if (item.getType() == Material.STAINED_CLAY
                    && item.getDurability() == 14) {
                if (ChosenEffect.containsKey(player.getUniqueId())) {
                    ChosenEffect.remove(player.getUniqueId());
                    CurrentAngles.remove(player.getUniqueId());
                    player.sendMessage(Prefix + "Disabled your current effect!");
                }
            }
            else {
                success = false;
            }
            if (success) {
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(Title)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (ChosenEffect.containsKey(event.getPlayer().getUniqueId())) {
            ChosenEffect.remove(event.getPlayer().getUniqueId());
            CurrentAngles.remove(event.getPlayer().getUniqueId());
        }
    }
}
