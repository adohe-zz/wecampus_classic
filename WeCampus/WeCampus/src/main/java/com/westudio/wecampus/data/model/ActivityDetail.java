package com.westudio.wecampus.data.model;

/**
 * Created by nankonami on 13-11-15.
 */
public class ActivityDetail extends ActivityList {

    //Have ticket
    private boolean have_ticket;
    //Ticket service
    private String ticket_service;
    //Have sponsor
    private boolean have_sponsor;
    //Sponsor name
    private String sponsor_name;
    //Sponsor url
    private String sponsor_url;
    //Can join
    private boolean can_join;
    //Can like
    private boolean can_like;
    //Activity url
    private String url;
    //Activity organization
    private Organization organization;

    public boolean isHave_ticket() {
        return have_ticket;
    }

    public void setHave_ticket(boolean have_ticket) {
        this.have_ticket = have_ticket;
    }

    public boolean isHave_sponsor() {
        return have_sponsor;
    }

    public void setHave_sponsor(boolean have_sponsor) {
        this.have_sponsor = have_sponsor;
    }

    public String getSponsor_name() {
        return sponsor_name;
    }

    public void setSponsor_name(String sponsor_name) {
        this.sponsor_name = sponsor_name;
    }

    public String getTicket_service() {
        return ticket_service;
    }

    public void setTicket_service(String ticket_service) {
        this.ticket_service = ticket_service;
    }

    public String getSponsor_url() {
        return sponsor_url;
    }

    public void setSponsor_url(String sponsor_url) {
        this.sponsor_url = sponsor_url;
    }

    public boolean isCan_join() {
        return can_join;
    }

    public void setCan_join(boolean can_join) {
        this.can_join = can_join;
    }

    public boolean isCan_like() {
        return can_like;
    }

    public void setCan_like(boolean can_like) {
        this.can_like = can_like;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
