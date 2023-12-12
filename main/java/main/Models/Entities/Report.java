package main.Models.Entities;




import java.io.Serializable;

public class Report implements Serializable {


    private int report_id;

    private String type;

    private String description;

    private User user;




    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Report{" +
                "report_id=" + report_id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
