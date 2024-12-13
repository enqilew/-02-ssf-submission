package vttp.batch5.ssf.noticeboard.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.ssf.noticeboard.models.Notice;

@Repository
public class NoticeRepository {

    @Autowired
    @Qualifier("notice")
    private RedisTemplate<String, Object> template;

    private static final String HASH_KEY = "Notice";

    // Save a notice with ID
    public void save(String noticeId, Notice notice) {
        template.opsForHash().put(HASH_KEY, noticeId, notice);
    }

    // Retrieve all notices
    public List<Notice> findAll() {
        return template.opsForHash().values(HASH_KEY)
                .stream()
                .map(obj -> (Notice) obj)
                .collect(Collectors.toList());
    }
}
