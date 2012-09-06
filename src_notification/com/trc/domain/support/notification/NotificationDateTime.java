package com.trc.domain.support.notification;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.joda.time.DateTime;


@Entity
@Table(name="notification_date_time")
public class NotificationDateTime implements Serializable{

	private static final long serialVersionUID = 1495544695293668738L;
	
	@Id
	@Column(name="date_time_id", unique=true, nullable=false, updatable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long dateTimeId =0L;
	
	@Column(name="start_second")
	Integer startSecond;                //(0-59)
	@Column(name="start_minute")
	Integer startMinute;                //(0-59)
	@Column(name="start_hour")
	private Integer startHour;          //(0-23)
	@Column(name="start_day_of_month")
	private Integer startDayOfMonth;    //(1-31)
	@Column(name="start_day_of_week")
	private Integer startDayOfWeek;     //(1-7 or SUN-SAT
	@Column(name="start_month")
	private Integer startMonth;         //(1-12 or JAN-DEC)
	@Column(name="start_year")   
	private Integer startYear;          //(1970-2099)	
	
	@Column(name="end_second")
	Integer endSecond;                  //(0-59)
	@Column(name="end_minute")
	Integer endMinute;                  //(0-59)
	@Column(name="end_hour")
	private Integer endHour;            //(0-23)
	@Column(name="end_day_of_month")
	private Integer endDayOfMonth;      //(1-31)
	@Column(name="end_day_of_week")
	private Integer endDayOfWeek;       //(1-7 or SUN-SAT
	@Column(name="end_month")
	private Integer endMonth;           //(1-12 or JAN-DEC)
	@Column(name="end_year")   
	private Integer endYear;            //(1970-2099)	
	
	@Column(name="repeatable")
	private String repeatable;
		
	@OneToMany(fetch = FetchType.EAGER, mappedBy="notificationDateTime", cascade=CascadeType.ALL)
	private Set<NotificationSchedule> notificationSchedule;
	
	@Transient
	private DateTime startDateTime;
	
	@Transient
	private DateTime endDateTime;
	
	public NotificationDateTime(){
	  // scheduledDateTime = new DateTime();//year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour);
	}		
	
	public NotificationDateTime(Integer startSecond,
			Integer startMinute, Integer startHour,
			Integer startDayOfMonth, Integer startDayOfWeek,
			Integer startMonth, Integer startYear,
			Integer endSecond, Integer endMinute,
			Integer endHour, Integer endDayOfMonth, Integer endDayOfWeek,
			Integer endMonthOfYear, Integer endYear, String repeatable,
			Set<NotificationSchedule> notificationSchedule) {
		super();
		this.startSecond = startSecond;
		this.startMinute = startMinute;
		this.startHour = startHour;
		this.startDayOfMonth = startDayOfMonth;
		this.startDayOfWeek = startDayOfWeek;
		this.startMonth = startMonth;
		this.startYear = startYear;
		this.endSecond = endSecond;
		this.endMinute = endMinute;
		this.endHour = endHour;
		this.endDayOfMonth = endDayOfMonth;
		this.endDayOfWeek = endDayOfWeek;
		this.endMonth = endMonth;
		this.endYear = endYear;
		this.repeatable = repeatable;
		this.notificationSchedule = notificationSchedule;
	}
	
	public void initDateTime(){
		startDateTime = new DateTime(startYear, startMonth, startDayOfMonth, startHour, startMinute);
		endDateTime = new DateTime(endYear, endMonth, endDayOfMonth, endHour, endMinute);
	}
	
	public Long getDateTimeId() {
		return dateTimeId;
	}
	
	public void setDateTimeId(Long intervalId) {
		this.dateTimeId = dateTimeId;
	}


	public Integer getStartSecond() {
		return startSecond;
	}


	public void setStartSecond(Integer startSecond) {
		this.startSecond = startSecond;
	}


	public Integer getStartMinute() {
		return startMinute;
	}


	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}


	public Integer getStartHour() {
		return startHour;
	}


	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}


	public Integer getStartDayOfMonth() {
		return startDayOfMonth;
	}


	public void setStartDayOfMonth(Integer startDayOfMonth) {
		this.startDayOfMonth = startDayOfMonth;
	}


	public Integer getStartDayOfWeek() {
		return startDayOfWeek;
	}


	public void setStartDayOfWeek(Integer startDayOfWeek) {
		this.startDayOfWeek = startDayOfWeek;
	}


	public Integer getStartMonth() {
		return startMonth;
	}


	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}


	public Integer getStartYear() {
		return startYear;
	}


	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}


	public Integer getEndSecond() {
		return endSecond;
	}


	public void setEndSecond(Integer endSecond) {
		this.endSecond = endSecond;
	}


	public Integer getEndMinute() {
		return endMinute;
	}


	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}


	public Integer getEndHour() {
		return endHour;
	}


	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}


	public Integer getEndDayOfMonth() {
		return endDayOfMonth;
	}


	public void setEndDayOfMonth(Integer endDayOfMonth) {
		this.endDayOfMonth = endDayOfMonth;
	}


	public Integer getEndDayOfWeek() {
		return endDayOfWeek;
	}


	public void setEndDayOfWeek(Integer endDayOfWeek) {
		this.endDayOfWeek = endDayOfWeek;
	}


	public Integer getEndMonth() {
		return endMonth;
	}


	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}


	public Integer getEndYear() {
		return endYear;
	}


	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}


	public String getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(String repeatable) {
		this.repeatable = repeatable;
	}

	public Set<NotificationSchedule> getNotificationSchedule() {
		return notificationSchedule;
	}

	public void setNotificationSchedule(
			Set<NotificationSchedule> notificationSchedule) {
		this.notificationSchedule = notificationSchedule;
	}

	public DateTime getStartDateTime() {
		//if(startDateTime == null)
			//startDateTime = new DateTime(startYear, startMonth, startDayOfMonth, startHour, startMinute); 	
		return startDateTime;
	}

	public void setStartDateTime(DateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public DateTime getEndDateTime() {
		//if(endDateTime == null)
		  // endDateTime = new DateTime(endYear, endMonth, endDayOfMonth, endHour, endMinute);	
		return endDateTime;	
	}	

	public void setEndDateTime(DateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateTimeId == null) ? 0 : dateTimeId.hashCode());
		result = prime * result
				+ ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result
				+ ((endDayOfMonth == null) ? 0 : endDayOfMonth.hashCode());
		result = prime * result
				+ ((endDayOfWeek == null) ? 0 : endDayOfWeek.hashCode());
		result = prime * result + ((endHour == null) ? 0 : endHour.hashCode());
		result = prime * result
				+ ((endMinute == null) ? 0 : endMinute.hashCode());
		result = prime * result
				+ ((endMonth == null) ? 0 : endMonth.hashCode());
		result = prime * result
				+ ((endSecond == null) ? 0 : endSecond.hashCode());
		result = prime * result + ((endYear == null) ? 0 : endYear.hashCode());
		result = prime * result
				+ ((repeatable == null) ? 0 : repeatable.hashCode());
		result = prime * result
				+ ((startDateTime == null) ? 0 : startDateTime.hashCode());
		result = prime * result
				+ ((startDayOfMonth == null) ? 0 : startDayOfMonth.hashCode());
		result = prime * result
				+ ((startDayOfWeek == null) ? 0 : startDayOfWeek.hashCode());
		result = prime * result
				+ ((startHour == null) ? 0 : startHour.hashCode());
		result = prime * result
				+ ((startMinute == null) ? 0 : startMinute.hashCode());
		result = prime * result
				+ ((startMonth == null) ? 0 : startMonth.hashCode());
		result = prime * result
				+ ((startSecond == null) ? 0 : startSecond.hashCode());
		result = prime * result
				+ ((startYear == null) ? 0 : startYear.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationDateTime other = (NotificationDateTime) obj;
		if (dateTimeId == null) {
			if (other.dateTimeId != null)
				return false;
		} else if (!dateTimeId.equals(other.dateTimeId))
			return false;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
			return false;
		if (endDayOfMonth == null) {
			if (other.endDayOfMonth != null)
				return false;
		} else if (!endDayOfMonth.equals(other.endDayOfMonth))
			return false;
		if (endDayOfWeek == null) {
			if (other.endDayOfWeek != null)
				return false;
		} else if (!endDayOfWeek.equals(other.endDayOfWeek))
			return false;
		if (endHour == null) {
			if (other.endHour != null)
				return false;
		} else if (!endHour.equals(other.endHour))
			return false;
		if (endMinute == null) {
			if (other.endMinute != null)
				return false;
		} else if (!endMinute.equals(other.endMinute))
			return false;
		if (endMonth == null) {
			if (other.endMonth != null)
				return false;
		} else if (!endMonth.equals(other.endMonth))
			return false;
		if (endSecond == null) {
			if (other.endSecond != null)
				return false;
		} else if (!endSecond.equals(other.endSecond))
			return false;
		if (endYear == null) {
			if (other.endYear != null)
				return false;
		} else if (!endYear.equals(other.endYear))
			return false;
		if (repeatable == null) {
			if (other.repeatable != null)
				return false;
		} else if (!repeatable.equals(other.repeatable))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		if (startDayOfMonth == null) {
			if (other.startDayOfMonth != null)
				return false;
		} else if (!startDayOfMonth.equals(other.startDayOfMonth))
			return false;
		if (startDayOfWeek == null) {
			if (other.startDayOfWeek != null)
				return false;
		} else if (!startDayOfWeek.equals(other.startDayOfWeek))
			return false;
		if (startHour == null) {
			if (other.startHour != null)
				return false;
		} else if (!startHour.equals(other.startHour))
			return false;
		if (startMinute == null) {
			if (other.startMinute != null)
				return false;
		} else if (!startMinute.equals(other.startMinute))
			return false;
		if (startMonth == null) {
			if (other.startMonth != null)
				return false;
		} else if (!startMonth.equals(other.startMonth))
			return false;
		if (startSecond == null) {
			if (other.startSecond != null)
				return false;
		} else if (!startSecond.equals(other.startSecond))
			return false;
		if (startYear == null) {
			if (other.startYear != null)
				return false;
		} else if (!startYear.equals(other.startYear))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotificationDateTime [dateTimeId=" + dateTimeId
				+ ", startSecond=" + startSecond + ", startMinute="
				+ startMinute + ", startHour=" + startHour
				+ ", startDayOfMonth=" + startDayOfMonth + ", startDayOfWeek="
				+ startDayOfWeek + ", startMonth=" + startMonth
				+ ", startYear=" + startYear + ", endSecond=" + endSecond
				+ ", endMinute=" + endMinute + ", endHour=" + endHour
				+ ", endDayOfMonth=" + endDayOfMonth + ", endDayOfWeek="
				+ endDayOfWeek + ", endMonth=" + endMonth + ", endYear="
				+ endYear + ", repeatable=" + repeatable + ", startDateTime="
				+ startDateTime + ", endDateTime=" + endDateTime + "]";
	}	
	
}
