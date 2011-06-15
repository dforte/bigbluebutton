/* BigBlueButton - http://www.bigbluebutton.org
 * 
 * 
 * Copyright (c) 2008-2009 by respective authors (see below). All rights reserved.
 * 
 * BigBlueButton is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License as published by the Free Software 
 * Foundation; either version 3 of the License, or (at your option) any later 
 * version. 
 * 
 * BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along 
 * with BigBlueButton; if not, If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Jeremy Thomerson <jthomerson@genericconf.com>
 * @version $Id: $
 */
package org.bigbluebutton.api.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Meeting {
	private String name;
	private String extMeetingId;
	private String intMeetingId;	
	private int duration;	 
	private long createdTime;
	private long startTime = 0;
	private long endTime = 0;
	private boolean forciblyEnded = false;
	private String telVoice;
	private String webVoice;
	private String moderatorPass;
	private String viewerPass;
	private String welcomeMsg;
	private String logoutUrl;
	private int maxUsers;
	private boolean record;
	private String dialNumber;

	private Map<String, String> metadata;	
	private final ConcurrentMap<String, User> users; 
	
	public Meeting(Builder builder) {
		name = builder.name;
		extMeetingId = builder.externalId;
		intMeetingId = builder.internalId;
		viewerPass = builder.viewerPass;
		moderatorPass = builder.moderatorPass;
		maxUsers = builder.maxUsers;
		logoutUrl = builder.logoutUrl;
		record = builder.record;
    	duration = builder.duration;
    	webVoice = builder.webVoice;
    	telVoice = builder.telVoice;
    	welcomeMsg = builder.welcomeMsg;
    	dialNumber = builder.dialNumber;
    	metadata = builder.metadata;
    	
		createdTime = System.currentTimeMillis();
		users = new ConcurrentHashMap<String, User>();
				
		metadata.put("meetingId", extMeetingId);
	}

	public Collection<String> getMetadata() {
		return metadata.isEmpty() ? Collections.<String>emptySet() : Collections.unmodifiableCollection(metadata.values());
	}
	
	public Collection<User> getUsers() {
		return users.isEmpty() ? Collections.<User>emptySet() : Collections.unmodifiableCollection(users.values());
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(long t) {
		startTime = t;
	}
	
	public long getCreatedTime() {
		return createdTime;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public long getEndTime() {
		return endTime;
	}
	
	public void setEndTime(long t) {
		endTime = t;
	}
	
	public boolean isRunning() {
		boolean running = startTime != 0 && endTime == 0;
		return running;
	}

	public String getName() {
		return name;
	}

	public boolean isForciblyEnded() {
		return forciblyEnded;
	}

	public void setForciblyEnded(boolean forciblyEnded) {
		this.forciblyEnded = forciblyEnded;
	}

	/**
	 * Get the external meeting id.
	 * @return external meeting id.
	 */
	public String getExternalId() {
		return extMeetingId;
	}
	
	/**
	 * Get the internal meeting id;
	 */
	public String getInternalId() {
		return intMeetingId;
	}

	public String getWebVoice() {
		return webVoice;
	}

	public String getTelVoice() {
		return telVoice;
	}

	public String getModeratorPassword() {
		return moderatorPass;
	}

	public String getViewerPassword() {
		return viewerPass;
	}

	public String getWelcomeMessage() {
		return welcomeMsg;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public int getMaxUsers() {
		return maxUsers;
	}

	public boolean isRecord() {
		return record;
	}
	
	public void userJoined(User user){
		this.users.put(user.getUserid(), user);
	}
	
	public User userLeft(String userid){
		return users.remove(userid);		
	}
	
	public User getUserById(String id){
		return this.users.get(id);
	}
	
	public int getNumUsers(){
		return this.users.size();
	}
	
	public int getNumModerators(){
		int sum = 0;
		for (String key : users.keySet()) {
		    User u =  (User) users.get(key);
		    if (u.isModerator()) sum++;
		}
		return sum;
	}
	
	public String getDialNumber() {
		return dialNumber;
	}
			
	/***
	 * Meeting Builder
	 *
	 */
	public static class Builder {
    	private String name;
    	private String externalId;
    	private String internalId;   	
    	private int maxUsers;
    	private boolean record;
    	private String moderatorPass;
    	private String viewerPass;
    	private int duration;
    	private String webVoice;
    	private String telVoice;
    	private String welcomeMsg;
    	private String logoutUrl;
    	private Map<String, String> metadata;
    	private String dialNumber;
    	
    	public Builder(String externalId, String internalId) {
    		this.externalId = externalId;
    		this.internalId = internalId;
    	}
    	
    	public Builder withName(String name) {
    		this.name = name;
    		return this;
    	}
    	    	
    	public Builder withDuration(int minutes) {
    		duration = minutes;
    		return this;
    	}
    	
    	public Builder withMaxUsers(int n) {
    		maxUsers = n;
    		return this;
    	}
    	
    	public Builder withRecording(boolean record) {
    		this.record = record;
    		return this;
    	}

    	public Builder withWebVoice(String w) {
    		this.webVoice = w;
    		return this;
    	}
    	
    	public Builder withTelVoice(String t) {
    		this.telVoice = t;
    		return this;
    	}
    	
    	public Builder withDialNumber(String d) {
    		this.dialNumber = d;
    		return this;
    	}
    	
    	public Builder withModeratorPass(String p) {
    		this.moderatorPass = p;
    		return this;
    	}
    	
    	public Builder withViewerPass(String p) {  		
	    	this.viewerPass = p;
	    	return this;
	    }
    	
    	public Builder withWelcomeMessage(String w) {
    		welcomeMsg = w;
    		return this;
    	}
    	
    	public Builder withLogoutUrl(String l) {
    		logoutUrl = l;
    		return this;
    	}
    	
    	public Builder withMetadata(Map<String, String> m) {
    		metadata = m;
    		return this;
    	}
    
    	public Meeting build() {
    		return new Meeting(this);
    	}
    }
}
