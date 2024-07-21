package app.models.ComminityModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "community_post")
public class CommunityPost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    @Lob
    private String text;

    @Lob
    private byte[] file;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created")
    @CreationTimestamp
    private LocalDateTime timeCreated;

    public CommunityPost() {
    }

    public CommunityPost(String title, String text, byte[] file, LocalDateTime timeCreated) {
        this.title = title;
        this.text = text;
        this.file = file;
        this.timeCreated = timeCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
