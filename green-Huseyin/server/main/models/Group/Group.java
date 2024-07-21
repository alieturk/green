package app.models.Group;

import app.models.Event.Event;
import app.models.ResearchItem.ResearchItem;
import app.models.User.type.Participant;
import app.models.chat.ChatMessage;
import app.views.CustomJsonMapper;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NamedQueries(
        @NamedQuery(name = "Find_if_group_code_exists", query = "select g from Group g where g.groupCode = ?1")

)
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@JsonView(CustomJsonMapper.Minimized.class)
public class Group {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "groupCode",length = 5)
    private String groupCode;
    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "created")
    @CreationTimestamp
    private LocalDateTime timeCreated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    @JsonView(CustomJsonMapper.Extensive.class)
    @JsonSerialize(using = CustomJsonMapper.MinimizedSerializer.class)
    private final List<Participant> participants = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    @JsonView(CustomJsonMapper.Extensive.class)
    @JsonSerialize(using = CustomJsonMapper.MinimizedSerializer.class)
    private final List<ResearchItem> researchItems = new ArrayList<>();

    protected Group(){}

    @OneToMany(mappedBy = "group")
    @JsonView(CustomJsonMapper.Extensive.class)
    @JsonSerialize(using = CustomJsonMapper.MinimizedSerializer.class)
    private final List<ChatMessage> chatMessages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group(String name) {
        this.name = name;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public List<ResearchItem> getResearchItems() {
        return researchItems;
    }

    /**
     * Adds a participant to this
     * group's list of associated participants.
     *
     * @param participant participant
     */
    public void associateParticipant(Participant participant) {
        this.participants.add(participant);
    }

    /**
     * Adds a research item to this
     * group's list of associated research items.
     *
     * @param researchItem research item
     */
    public void addAssociatedResearchItem(ResearchItem researchItem) {
        this.researchItems.add(researchItem);
    }

    public void associateChatMessage(ChatMessage message){this.chatMessages.add(message);}

    public String getGroupCode() {return groupCode;}

    public void setGroupCode(String groupCode) {this.groupCode = groupCode;}

    public List<ChatMessage> getChatMessages() {return chatMessages;}

    @Override
    public String toString() {
        return "Group{" +
                "groupCode=" + groupCode +
                ", name='" + name + '\'' +
                ", timeCreated=" + timeCreated +
                ", participant=" + participants +
                '}';
    }

    public void setParticipant(Participant participant) {
    }
}