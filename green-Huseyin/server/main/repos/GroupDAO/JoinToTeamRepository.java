package app.repositories.GroupDAO;

import java.util.List;

public interface JoinToTeamRepository<G> {

    List<G> findByQuery(String jpqlName, Object... params);

}
