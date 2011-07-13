package org.bigbluebutton.core
{
	import org.bigbluebutton.core.managers.ConfigManager;
	import org.bigbluebutton.core.managers.ConnectionManager;
	import org.bigbluebutton.core.managers.StreamManager;
	import org.bigbluebutton.core.managers.UserManager;
	
	public class BBB
	{
		private static var userManager:UserManager = null;
		private static var configManager:ConfigManager = null;
		private static var streamManager:StreamManager = null;
		private static var connectionManager:ConnectionManager = null;
		private static var session:Session = null;
		
		public static function initUserManager():UserManager {
			if (userManager == null) {
				userManager = new UserManager();
			}
			return userManager;
		}
		
		public static function initConfigManager():ConfigManager {
			if (configManager == null) {
				configManager = new ConfigManager();
				configManager.loadConfig();
			}
			return configManager;
		}

		public static function initStreamManager():StreamManager {
			if (streamManager == null) {
				streamManager = new StreamManager();
			}
			return streamManager;
		}
		
		public static function initConnectionManager():ConnectionManager {
			if (connectionManager == null) {
				connectionManager = new ConnectionManager();
			}
			return connectionManager;
		}		

		public static function initSession():Session {
			if (session == null) {
				session = new Session();
			}
			return session;
		}		
	}
}
