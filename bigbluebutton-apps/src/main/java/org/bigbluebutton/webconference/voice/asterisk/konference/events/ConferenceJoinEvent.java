package org.bigbluebutton.webconference.voice.asterisk.konference.events;

public class ConferenceJoinEvent extends AbstractConferenceEvent {

	private static final long serialVersionUID = 1926565708226475330L;
	
	private String type;
	private String uniqueID;
	private Integer member;
	private String flags;
	private String callerID;
	private String callerIDName;
	private Integer moderators;
	private Integer count;
	    
	public ConferenceJoinEvent(Object source) {
        super(source);
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getCallerID() {
		return callerID;
	}

	public void setCallerID(String callerID) {
		this.callerID = callerID;
	}

	public String getCallerIDName() {
		return callerIDName;
	}

	public void setCallerIDName(String callerIDName) {
		this.callerIDName = callerIDName;
	}

	public Integer getModerators() {
		return moderators;
	}

	public void setModerators(Integer moderators) {
		this.moderators = moderators;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}