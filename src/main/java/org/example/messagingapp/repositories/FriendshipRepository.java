package org.example.messagingapp.repositories;

import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.entities.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f FROM Friendship f WHERE f.user1 = ?1 OR f.user2 = ?1")
    List<Friendship> findAllByUser1OrUser2(Contact contact);

}

