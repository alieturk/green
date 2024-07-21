package app.rest.admin;

import app.models.ComminityModel.CommunityPost;
import app.models.User.User;
import app.models.User.type.Participant;
import app.repositories.CommunityRepository;
import app.repositories.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * There are two endpoints and one method in this REST Controller .
 * @author Huseyin Altunbas
 *
 */

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    UserRepository repository;


    /**
     * This post mapping helps admin to create new post
     *
     * @param communityPost
     * @param file
     * @return
     * @throws IOException
     * @throws MessagingException
     */
    @PostMapping("/postPosts")
    public ResponseEntity<CommunityPost> postPosts(@RequestPart("content") ObjectNode communityPost,
                                                   @RequestPart("file") MultipartFile file) throws IOException, MessagingException {

        //Creating a Community Post object
        CommunityPost communityPost1 = new CommunityPost();
        // getting the bytes of the Multipart file
        byte[] byteArr = file.getBytes();
        // Saving Multipart file in
        communityPost1.setFile(byteArr);
        // Saving the rest of the post into the object that just is created
        communityPost1.setTitle(communityPost.get("title").textValue());
        communityPost1.setText(communityPost.get("text").textValue());
        // Saving the post to Database
        CommunityPost posts = communityRepository.save(communityPost1);

        //Getting the list of users that are participant
        List<User> users = repository.findAll().stream().filter(user -> user.getClass() == Participant.class).toList();

        // Calling the method that sends a copy of post to all user
        for (int i = 0; i < users.size(); i++) {
            sendPostWithImage(communityPost1, file, users.get(i).getEmailAddress());

        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(posts.getId()).toUri();

        return ResponseEntity.created(location).body(posts);
    }


    /**
     * This mapping returns all posts.
     * @return All posts
     */
    @GetMapping(value = "/posts")
    public List<CommunityPost> getAllPost() {
        return communityRepository.findAll();
    }


    /**
     * This method helps with sending image
     * @param post
     * @param image
     * @param user
     * @throws MessagingException
     * @throws IOException
     */
    private void sendPostWithImage(CommunityPost post, MultipartFile image, String user) throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String from = "greenologyteam@outlook.com";
        String senderName = "The Greenology Team";
        String subject = "New Post is ready for you to read!";
        String emailContent = "<h1>[[title]]</h1>\n"
                +"<html><body><img style=\"width: 50%;height:50%\" src='cid:image'></body></html>"
                + "<p style: font-size:46px;>[[text]]</p>";


        emailContent = emailContent.replace("[[title]]", post.getTitle());
        emailContent = emailContent.replace("[[text]]", post.getText());

        helper.setFrom(from, senderName);
        helper.setTo((user));
        helper.setSubject(subject);

        //First part
        MimeBodyPart imagePart = new MimeBodyPart();

        imagePart.setContent(image.getBytes(), "image/png");
        imagePart.setFileName(image.getOriginalFilename());
        imagePart.setDisposition(MimeBodyPart.INLINE);

        // Set the content ID of the image
        imagePart.setHeader("Content-ID", "<image>");

        // Create a MimeMultipart object and add the MimeBodyPart object to it
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(imagePart);

        //Second part
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(emailContent, "text/html");
        // adding into multipart object
        multipart.addBodyPart(messageBodyPart);

        imagePart.setHeader("Content-Type", "image/png");

        message.setContent(multipart);
        helper.setText(emailContent, true);

        mailSender.send(message);
    }
}


























