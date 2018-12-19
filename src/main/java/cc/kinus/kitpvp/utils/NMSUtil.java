package cc.kinus.kitpvp.utils;

import com.google.common.base.Charsets;
import org.bukkit.*;
import org.json.simple.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class NMSUtil {
	private static Class<?> nmsChatSerializer;
	private static Class<?> nmsIChatBaseComponent;
	private static Class<?> packetType;
	private static Class<?> packetActions;
	private static Class<?> packetTitle;

	private static Method getHandle;

	private static String version;
	private static Field playerConnection;
	private static Method sendPacket;
	static {
		try {
			version = getNMSVersion();
			boolean newversion = Integer.parseInt(version.split("_")[1]) > 7;
			nmsChatSerializer = Class.forName(a(newversion ? "IChatBaseComponent$ChatSerializer" : "ChatSerializer"));
			nmsIChatBaseComponent = Class.forName(a("IChatBaseComponent"));
			packetType = Class.forName(a("PacketPlayOutChat"));
			packetActions = Class.forName(a(newversion ? "PacketPlayOutTitle$EnumTitleAction" : "EnumTitleAction"));
			packetTitle = Class.forName(a("PacketPlayOutTitle"));
			Class<?> typeCraftPlayer = Class.forName(b("entity.CraftPlayer"));
			Class<?> typeNMSPlayer = Class.forName(a("EntityPlayer"));
			Class<?> typePlayerConnection = Class.forName(a("PlayerConnection"));
			getHandle = typeCraftPlayer.getMethod("getHandle");
			playerConnection = typeNMSPlayer.getField("playerConnection");
			sendPacket = typePlayerConnection.getMethod("sendPacket", Class.forName(a("Packet")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private NMSUtil() {
	}

	public static String a(String str) {
		return "net.minecraft.server." + version + "." + str;
	}

	public static String b(String str) {
		return "org.bukkit.craftbukkit." + version + "." + str;
	}

	/**
	 * ���NMS�汾��
	 *
	 * @return NMS�汾��
	 */
	public static String getNMSVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}

	public static class ActionBar {
		private ActionBar() {
		}

		/**
		 * ���淢��ActionBar
		 *
		 * @param message
		 *            ��Ҫ���͵���Ϣ
		 */
		public static void broadcast(String message) {
			for (org.bukkit.entity.Player player : Player.getOnlinePlayers()) {
				send(player, message);
			}
		}

		/**
		 * ���淢��ActionBar
		 *
		 * @param message
		 *            ��Ҫ���͵���Ϣ
		 * @param times
		 *            ��Ҫ��ʾ��ʱ��
		 */
		public static void broadcast(final String message, final int times) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int time = times;
					do {
						for (org.bukkit.entity.Player player : Player.getOnlinePlayers()) {
							send(player, message);
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// Ignore
						}
						time--;
					} while (time > 0);
				}
			}).start();
		}

		/**
		 * ���淢��ActionBar(������)
		 *
		 * @param world
		 *            ��Ҫ���͵�����
		 * @param message
		 *            ��Ҫ���͵���Ϣ
		 * @param times
		 *            ��Ҫ��ʾ��ʱ��
		 */
		public static void broadcast(final World world, final String message, final int times) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int time = times;
					do {
						for (org.bukkit.entity.Player player : Player.getOnlinePlayers()) {
							if (player.getWorld().getName().equalsIgnoreCase(world.getName())) {
								send(player, message);
							}
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// Ignore
						}
						time--;
					} while (time > 0);

				}
			}).start();
		}

		/**
		 * ����ҷ���ActionBar��Ϣ
		 *
		 * @param receivingPacket
		 *            ������Ϣ�����
		 * @param msg
		 *            ActionBar��Ϣ
		 */
		public static void send(org.bukkit.entity.Player receivingPacket, String msg) {
			Object packet = null;
			try {
				Object serialized = nmsChatSerializer.getMethod("a", String.class).invoke(null,
						"{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', JSONObject.escape(msg)) + "\"}");
				if (!version.contains("1_7")) {
					packet = packetType.getConstructor(nmsIChatBaseComponent, byte.class).newInstance(serialized,
							(byte) 2);
				} else {
					packet = packetType.getConstructor(nmsIChatBaseComponent, int.class).newInstance(serialized, 2);
				}
				Object player = getHandle.invoke(receivingPacket);
				Object connection = playerConnection.get(player);
				sendPacket.invoke(connection, packet);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * ����ҷ���ActionBar��Ϣ
		 *
		 * @param receivingPacket
		 *            ������Ϣ�����
		 * @param msg
		 *            ��Ҫ���͵���Ϣ
		 * @param times
		 *            ��Ҫ��ʾ��ʱ��
		 */
		public static void send(final org.bukkit.entity.Player receivingPacket, final String msg, final int times) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int time = times;
					do {
						send(receivingPacket, msg);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// Ignore
						}
						time--;
					} while (time > 0);
				}
			}).start();
		}
	}

	/**
	 * Bukkit Player������
	 *
	 * @since 2016��7��23�� ����4:33:40
	 * @author ������
	 */
	public static class Player {
		private static Class<?> gameProfileClass;
		private static Constructor<?> gameProfileConstructor;
		private static Constructor<?> craftOfflinePlayerConstructor;
		private static Method getOnlinePlayers;
		static {
			try {
				// getOnlinePlayers start
				getOnlinePlayers = Bukkit.class.getDeclaredMethod("getOnlinePlayers");
				if (getOnlinePlayers.getReturnType() != org.bukkit.entity.Player[].class) {
					for (Method method : Bukkit.class.getDeclaredMethods()) {
						if (method.getReturnType() == org.bukkit.entity.Player[].class
								&& method.getName().endsWith("getOnlinePlayers")) {
							getOnlinePlayers = method;
						}
					}
				}
				// getOnlinePlayers end
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				// getOfflinePlayer start
				try {
					gameProfileClass = Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
				} catch (Exception e) {
					try {
						gameProfileClass = Class.forName("com.mojang.authlib.GameProfile");
					} catch (Exception e1) {
					}
				}
				gameProfileConstructor = gameProfileClass
						.getDeclaredConstructor(UUID.class, String.class);
				gameProfileConstructor.setAccessible(true);
				Class<? extends Server> craftServer = Bukkit.getServer().getClass();
				Class<?> craftOfflinePlayer = Class
						.forName(craftServer.getName().replace("CraftServer", "CraftOfflinePlayer"));
				craftOfflinePlayerConstructor = craftOfflinePlayer
						.getDeclaredConstructor(craftServer, gameProfileClass);
				craftOfflinePlayerConstructor.setAccessible(true);
				// getOfflinePlayer end
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private Player() {
		}

		/**
		 * ��ȡ�������(���������ȡ)
		 *
		 * @param playerName
		 *            �������
		 * @return {@link OfflinePlayer}
		 */
		@SuppressWarnings("deprecation")
		public static OfflinePlayer getOfflinePlayer(String playerName) {
			try {
				Object gameProfile = gameProfileConstructor.newInstance(UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8)), playerName);
				Object offlinePlayer = craftOfflinePlayerConstructor
						.newInstance(Bukkit.getServer(), gameProfile);
				return (OfflinePlayer) offlinePlayer;
			} catch (Exception e) {
				return Bukkit.getOfflinePlayer(playerName);
			}
		}

		/**
		 * ��ȡ�������
		 *
		 * @return �������
		 */
		public static Collection<? extends org.bukkit.entity.Player> getOnlinePlayers() {
			try {
				return Arrays.asList((org.bukkit.entity.Player[]) getOnlinePlayers.invoke(null));
			} catch (Exception e) {
				return Bukkit.getOnlinePlayers();
			}
		}
	}

	public static class Title {
		private Title() {
		}

		/**
		 * ����Title����
		 *
		 * @param title
		 *            ����
		 * @param subtitle
		 *            �ӱ���
		 */
		public static void broadcast(String title, String subtitle) {
			for (org.bukkit.entity.Player player : Player.getOnlinePlayers()) {
				send(player, title, subtitle);
			}
		}

		/**
		 * ����Title����
		 *
		 * @param title
		 *            ����
		 * @param subtitle
		 *            �ӱ���
		 * @param fadeInTime
		 *            ����ʱ��
		 * @param stayTime
		 *            ����ʱ��
		 * @param fadeOutTime
		 *            ����ʱ��
		 */
		public static void broadcast(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
			for (org.bukkit.entity.Player player : Player.getOnlinePlayers()) {
				send(player, title, subtitle, fadeInTime, stayTime, fadeOutTime);
			}
		}

		/**
		 * ����Title����
		 *
		 * @param world
		 *            ����
		 * @param title
		 *            ����
		 * @param subtitle
		 *            �ӱ���
		 */
		public static void broadcast(World world, String title, String subtitle) {
			for (org.bukkit.entity.Player player : Player.getOnlinePlayers()) {
				if (player.getWorld().getName().equalsIgnoreCase(world.getName())) {
					send(player, title, subtitle);
				}
			}
		}

		/**
		 * ������ҵ�Title
		 *
		 * @param recoverPlayer
		 *            ���ܵ����
		 * @throws Exception
		 *             �쳣
		 */
		public static void reset(org.bukkit.entity.Player recoverPlayer) throws Exception {
			// Send timings first
			Object player = getHandle.invoke(recoverPlayer);
			Object connection = playerConnection.get(player);
			Object[] actions = packetActions.getEnumConstants();
			Object packet = packetTitle.getConstructor(packetActions, nmsIChatBaseComponent).newInstance(actions[4],
					null);
			sendPacket.invoke(connection, packet);
		}

		/**
		 * ����Titile(Ĭ��ʱ�� 1 2 1)
		 *
		 * @param receivingPacket
		 *            ������Ϣ�����
		 * @param title
		 *            ����
		 * @param subtitle
		 *            �ӱ���
		 */
		public static void send(org.bukkit.entity.Player receivingPacket, String title, String subtitle) {
			send(receivingPacket, title, subtitle, 1, 2, 1);
		}

		/**
		 * ����Titile
		 *
		 * @param receivingPacket
		 *            ������Ϣ�����
		 * @param title
		 *            ����
		 * @param subtitle
		 *            �ӱ���
		 * @param fadeInTime
		 *            ����ʱ��
		 * @param stayTime
		 *            ����ʱ��
		 * @param fadeOutTime
		 *            ����ʱ��
		 */
		public static void send(org.bukkit.entity.Player receivingPacket, String title, String subtitle, int fadeInTime,
				int stayTime, int fadeOutTime) {
			if (packetTitle != null) {
				try {
					// First reset previous settings
					reset(receivingPacket);
					// Send timings first
					Object player = getHandle.invoke(receivingPacket);
					Object connection = playerConnection.get(player);
					Object[] actions = packetActions.getEnumConstants();
					Object packet = null;
					// Send if set
					if ((fadeInTime != -1) && (fadeOutTime != -1) && (stayTime != -1)) {
						packet = packetTitle
								.getConstructor(packetActions, nmsIChatBaseComponent, Integer.TYPE, Integer.TYPE,
										Integer.TYPE)
								.newInstance(actions[2], null, fadeInTime * 20, stayTime * 20, fadeOutTime * 20);
						sendPacket.invoke(connection, packet);
					}
					// Send title
					Object serialized = nmsChatSerializer.getMethod("a", String.class).invoke(null,
							"{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}");
					packet = packetTitle.getConstructor(packetActions, nmsIChatBaseComponent).newInstance(actions[0],
							serialized);
					sendPacket.invoke(connection, packet);
					if (!"".equals(subtitle)) {
						// Send subtitle if present
						serialized = nmsChatSerializer.getMethod("a", String.class).invoke(null,
								"{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', subtitle) + "\"}");
						packet = packetTitle.getConstructor(packetActions, nmsIChatBaseComponent)
								.newInstance(actions[1], serialized);
						sendPacket.invoke(connection, packet);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
