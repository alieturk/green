package app.rest;

import app.exceptions.GroupNotFound;
import app.exceptions.GroupNotFoundException;
import app.exceptions.UserHasAlreadyATeamException;
import app.exceptions.UserNotFoundException;
import app.models.Group.EmailRecipents;
import app.models.Group.Group;

import app.models.User.User;
import app.models.User.type.Participant;
import app.models.chat.ChatMessage;
import app.repositories.GroupDAO.GroupJPARepository;
import app.repositories.GroupDAO.ParticipantJPARepository;
import app.repositories.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequestMapping("/group")
@RestController
public class GroupController {
    @Autowired
    private final GroupJPARepository groupJPARepository;

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantJPARepository jpaRepository;

    public GroupController(GroupJPARepository groupJPARepository) {
        this.groupJPARepository = groupJPARepository;
    }

    /**
     * This Post mapping allows the new user to create a team. He can also invite anyone he wants to his group.
     * @param id
     * @param data
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @throws UserHasAlreadyATeamException
     */
    @Transactional
    @PostMapping("{id}")
    public ResponseEntity<Group> createTeam(@PathVariable String id, @RequestBody ObjectNode data) throws MessagingException, UnsupportedEncodingException, UserHasAlreadyATeamException {
        //Finding user via id
        Optional<User> userOptional = userRepository.findById(id);
        //Checking if user exist
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("Id- " + id);
        }
        //Getting the user
        Participant participant = (Participant) userOptional.get();
        //Checking if participant has group
        if (participant.getGroup() != null){
            throw new UserHasAlreadyATeamException("This user has already a team that he/she has been assigned " + participant.getFullName());
        }

        //Getting the name of the group
        Group group = new Group(data.get("group").textValue());

        //Associating the participant with group
        group.associateParticipant(participant);

        //Creating the group code
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 5; i++){
            sb.append((int)Math.floor(Math.random() * 10));
        }

        //Setting the group code
        group.setGroupCode(sb.toString());

        //Associating the group with participant
        participant.setGroup(group);

        //Saving the group
        Group savedTeam = groupJPARepository.save(group);

        String[] emails;
        EmailRecipents emailRecipents;
        if(data.get("emails").isArray()){
            emails = new String[data.get("emails").size()];
            // If there is no email address, team will be created without sending any invitation
            if (emails.length != 0){
                for (int i = 0; i < emails.length; i++) {
                    emails[i] = data.get("emails").get(i).textValue();
                }
                emailRecipents = new EmailRecipents(emails);
                sendEmailsWithCode(participant, group, emailRecipents);
            }
        }
        //Saving the user
        userRepository.save(participant);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedTeam.getId()).toUri();

        return ResponseEntity.created(location).body(savedTeam);
    }

    /**
     * That post mapping lets the user sends more people to his/her team
     * @param id
     * @param data
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */

    @PostMapping("{id}/sendInvites")
    @ResponseBody
    public void sendInvites(@PathVariable String id, @RequestBody ObjectNode data) throws MessagingException, UnsupportedEncodingException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("Id- " + id);
        }
        Participant participant = (Participant) userOptional.get();

        Group group = participant.getGroup();
        String[] emails;
        EmailRecipents emailRecipents;
        if(data.get("emails").isArray()){
            emails = new String[data.get("emails").size()];
                for (int i = 0; i < emails.length; i++) {
                    emails[i] = data.get("emails").get(i).textValue();
                }
                emailRecipents = new EmailRecipents(emails);
                sendEmailsWithCode(participant, group, emailRecipents);
            }
        }

    /**
     * This post mapping allows you to register to the group with the invitation code received by mail.
     * @param id
     * @param data
     * @return participant with group.
     */
    @Transactional
    @PostMapping("{id}/addMember")
    public ResponseEntity<Participant> assignToATeam(@PathVariable String id, @RequestBody ObjectNode data) {
        System.out.println("Data"  + data);
        //Finding user
        Optional<User> userOptional = userRepository.findById(id);
        //Checking if user exist
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("Id- " + id);
        }
        //Getting the input of user
        String groupNumber = String.valueOf(data.get("code").textValue());

        //Finding the group code that user entered in database
        boolean group = this.groupJPARepository.existsByGroupCode(groupNumber);
        //Checking if code exists in the database
        if (!group){
            throw new GroupNotFound("This group is not found" + groupNumber);
        }
        // Getting the group
        Group groupCode = groupJPARepository.findByGroupCode(groupNumber);

        //Getting the user
        Participant participant = (Participant) userOptional.get();

        //Checking if user has already assigned to a group
        if(participant.getGroup() != null){
            throw new UserHasAlreadyATeamException("This user has already a team" + participant.getFullName());
        }
        //Assigning user to group
        participant.setGroup(groupCode);
        //Saving the updated user
        jpaRepository.save(participant);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(participant).toUri();
            return ResponseEntity.created(location).body(participant);
    }

    /**
     * This method sends invitation to invited persons.
     * @param participant
     * @param group
     * @param emailRecipents
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private void sendEmailsWithCode(Participant participant, Group group, EmailRecipents emailRecipents) throws MessagingException, UnsupportedEncodingException {

        String fromAddress = "greenologyteam@outlook.com";
        String senderName = "The Greenology Team";
        String subject = "You are invited to a team!  ";
        String emailContent = "[[sender]] is invited you.\n"
                + "With this code you can apply to [[sender]]'s team!<br>"
                + "<h3>[[code]]</h3>"
                + "Thank you,<br>"
                + "Greenology.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(emailRecipents.getRecipents());
        helper.setSubject(subject);

        emailContent = emailContent.replace("[[sender]]", participant.getFullName());
        emailContent = emailContent.replace("[[code]]", group.getGroupCode());
        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    /**
     * Retrieves all the chatmessages of this group
     *
     * @param groupId id of the group
     * @return list of messages
     */
    @GetMapping("{groupId}/chatMessages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable long groupId){

        Optional<Group> groupOptional = groupJPARepository.findById(groupId);
        if(groupOptional.isEmpty())
            throw new GroupNotFoundException("Could not find group with ID " + groupId);
        Group group = groupOptional.get();

        return ResponseEntity.ok().body(group.getChatMessages().stream()
                .sorted(Comparator.comparing(ChatMessage::getTime))
                .toList());
    }
}
