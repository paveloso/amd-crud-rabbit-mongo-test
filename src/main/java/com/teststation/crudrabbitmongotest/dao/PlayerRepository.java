package com.teststation.crudrabbitmongotest.dao;

import com.teststation.crudrabbitmongotest.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PlayerRepository extends MongoRepository<Player, Long> {

    @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
    Player findByName(final String playerName);
}
