package com.example.police;

public class incidents {
    private String IncidentCity;
    private String IncidentDetails;
    private String IncidentType;
    private String LocationofIncident;
    private String ImageUrl;

    private incidents() {
    }

    private incidents(String IncidentCity, String IncidentDetails, String IncidentType, String LocationofIncident, String ImageUrl) {
        IncidentCity = this.IncidentCity;
        IncidentDetails = this.IncidentDetails;
        IncidentType = this.IncidentType;
        LocationofIncident = this.LocationofIncident;
        ImageUrl = this.ImageUrl;
    }

    public String getIncidentCity() {
        return IncidentCity;
    }

    public void setIncidentCity(String incidentCity) {
        IncidentCity = incidentCity;
    }

    public String getIncidentDetails() {
        return IncidentDetails;
    }

    public void setIncidentDetails(String incidentDetails) {
        IncidentDetails = incidentDetails;
    }

    public String getIncidentType() {
        return IncidentType;
    }

    public void setIncidentType(String incidentType) {
        IncidentType = incidentType;
    }

    public String getLocationofIncident() {
        return LocationofIncident;
    }

    public void setLocationofIncident(String locationofIncident) {
        LocationofIncident = locationofIncident;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }


}
