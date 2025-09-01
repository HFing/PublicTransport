package com.hfing.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "traffic_jam_reports")
public class TrafficJamReport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "report_time")
    private Timestamp reportTime;

    public TrafficJamReport() {
    }

    public TrafficJamReport(Integer id, User user, String location, String description, String imageUrl, Timestamp reportTime) {
        this.id = id;
        this.user = user;
        this.location = location;
        this.description = description;
        this.imageUrl = imageUrl;
        this.reportTime = reportTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }
}