package app.repositories.GroupDAO;

import app.models.Group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupJPARepository extends JpaRepository<Group, Long> {


    @Query("select case when count(g)> 0 then true else false end from Group g where lower(g.groupCode) like lower(:groupCode)")
    boolean existsByGroupCode(@Param("groupCode")String groupCode);

    Group findByGroupCode(String code);
}