package app.repositories.GroupDAO;

import app.models.Group.Group;
import app.models.User.type.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface ParticipantJPARepository extends JpaRepository<Participant, String> {

    boolean existsByGroup(Group group);
}
