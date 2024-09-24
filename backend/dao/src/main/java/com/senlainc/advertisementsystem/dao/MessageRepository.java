package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.message.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends BaseRepository<Message, Long> {

    List<Message> getBySenderUserId(Long senderId, Sort sort);
    List<Message> getByRecipientUserId(Long recipientId, Sort sort);
    List<Message> getBySenderUserIdAndUploadDateBetween(Long senderId, LocalDateTime from, LocalDateTime to, Sort sort);
    List<Message> getByRecipientUserIdAndUploadDateBetween(Long recipientId, LocalDateTime from, LocalDateTime to, Sort sort);
    @Query(value = "select u.id from Message m join m.sender s join s.user u where m.id = ?1")
    Long getSenderUserIdById(Long id);
    @Query(value = "select u.id from Message m join m.recipient s join s.user u where m.id = ?1")
    Long getRecipientUserIdById(Long id);
}
