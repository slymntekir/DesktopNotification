package pojos;

import java.util.Date;

public class users {
    private int id;
    private String user_name;
    private int user_id;
    private int is_notify;
    private String notify_text;
    private String notify_header;
    private String notify_url;
    private Date create_date;

    public users() {
    }

    public users(int id, String user_name, int user_id, int is_notify, String notify_text, String notify_header, String notify_url, Date create_date) {
        this.id = id;
        this.user_name = user_name;
        this.user_id = user_id;
        this.is_notify = is_notify;
        this.notify_text = notify_text;
        this.notify_header = notify_header;
        this.notify_url = notify_url;
        this.create_date = create_date;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIs_notify() {
        return is_notify;
    }

    public void setIs_notify(int is_notify) {
        this.is_notify = is_notify;
    }

    public String getNotify_text() {
        return notify_text;
    }

    public void setNotify_text(String notify_text) {
        this.notify_text = notify_text;
    }

    public String getNotify_header() {
        return notify_header;
    }

    public void setNotify_header(String notify_header) {
        this.notify_header = notify_header;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return "users{" + "id=" + id + 
                ", user_name=" + user_name + 
                ", user_id=" + user_id + 
                ", is_notify=" + is_notify + 
                ", notify_text=" + notify_text + 
                ", notify_header=" + notify_header + 
                ", notify_url=" + notify_url + 
                ", create_date=" + create_date + '}';
    }
    
}