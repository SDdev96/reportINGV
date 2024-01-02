package reportINGV;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;

@SuppressWarnings("all")

public class INGEvent implements Comparable<INGEvent> {
    private String eventID;
    private LocalDateTime time;
    private Double latitude;
    private Double longitude;
    private Float depthKm;
    private String author;
    private String catalog;
    private String contributor;
    private String contributorID;
    private String magType;
    private Float magnitude;
    private String magAuthor;
    private String eventLocationName;
    private String eventType;

    // GETTERS
    public String getEventID() {
        return eventID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Float getDepthKm() {
        return depthKm;
    }

    public String getAuthor() {
        return author;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getContributor() {
        return contributor;
    }

    public String getContributorID() {
        return contributorID;
    }

    public String getMagType() {
        return magType;
    }

    public Float getMagnitude() {
        return magnitude;
    }

    public String getMagAuthor() {
        return magAuthor;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public String getEventType() {
        return eventType;
    }

    // SETTERS
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDepthKm(Float depthKm) {
        this.depthKm = depthKm;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public void setContributorID(String contributorID) {
        this.contributorID = contributorID;
    }

    public void setMagType(String magType) {
        this.magType = magType;
    }

    public void setMagnitude(Float magnitude) {
        this.magnitude = magnitude;
    }

    public void setMagAuthor(String magAuthor) {
        this.magAuthor = magAuthor;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ((eventID == null) ? 0 : eventID.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final INGEvent other = (INGEvent) obj;
        return this.eventID.equals(other.eventID);
    }

    @Override
    public int compareTo(INGEvent o) {
        return eventID.compareTo(o.getEventID());
    }

    @Override
    public String toString() {
        return "EQEvent [eventID=" + eventID + ", time=" + time + ", latitude=" + latitude + ", longitude=" + longitude
                + ", depthKm=" + depthKm + ", author=" + author + ", catalog=" + catalog + ", contributor="
                + contributor + ", contributorID=" + contributorID + ", magType=" + magType + ", magnitude=" + magnitude
                + ", magAuthor=" + magAuthor + ", eventLocationName=" + eventLocationName + "]";
    }
}
