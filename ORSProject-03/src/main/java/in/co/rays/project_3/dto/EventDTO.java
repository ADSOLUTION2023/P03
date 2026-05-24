package in.co.rays.project_3.dto;

import java.util.Date;

public class EventDTO extends BaseDTO {
	
	public static final String ACTIVE = "Active";
	public static final String INACTIVE = "Inactive";
	
	private String participantName;
	private String eventName;
	private String email;
	private Date registrationDate;
	
	public String getParticipantName() {
		return participantName;
	}
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public static String getActive() {
		return ACTIVE;
	}
	public static String getInactive() {
		return INACTIVE;
	}

	public String getKey() {

		return id + "";
	}

	public String getValue() {

		return eventName + "" + participantName;
	}

}
