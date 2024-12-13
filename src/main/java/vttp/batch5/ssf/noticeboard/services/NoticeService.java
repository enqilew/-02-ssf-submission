package vttp.batch5.ssf.noticeboard.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepo;

    // Save a notice and return its ID
    public String save(Notice notice) {
        String noticeId = UUID.randomUUID().toString().substring(0, 26);
        noticeRepo.save(noticeId, notice);
        return noticeId;
    }

    // Retrieve all notices
    public List<Notice> getAllNotices() {
        return noticeRepo.findAll();
    }

    // Save notice without ID for form-based submission
    public void saveNotice(Notice notice) {
        save(notice);
    }

	
}


