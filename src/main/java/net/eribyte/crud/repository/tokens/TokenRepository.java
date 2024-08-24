package net.eribyte.crud.repository.tokens;

import net.eribyte.crud.entity.tokens.StreamerRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<StreamerRefreshToken, Integer> {


    StreamerRefreshToken findByTwitchIdEquals(int streamer_id);
}
