package vttp.batch5.ssf.noticeboard.controllers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;


@Controller
@RequestMapping
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping(path = "/notice")
    public String showLandingPage(Model model) {
        List<Notice> notices = noticeService.getAllNotices();
        model.addAttribute("notice", notices);
        return "notice";
    }

    @PostMapping("/notice")
    public String submitNotice(
            @Valid @ModelAttribute("notice") Notice notice,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "Error";
        }

        noticeService.saveNotice(notice);
        model.addAttribute("successMessage", "Notice posted successfully!");
        return "Success";
    }

    @PostMapping(path = "/notice", 
             produces = MediaType.APPLICATION_JSON_VALUE, 
             consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postNotice(@RequestBody String payload) {
        System.out.printf(">>> payload: %s\n", payload);

        try {
            // Convert JSON payload to Notice object
            Notice notice = Notice.toNotice(payload);

            // Save the notice and generate an ID
            String noticeId = noticeService.save(notice);

            // Build JSON response
            JsonObject response = Json.createObjectBuilder()
                    .add("noticeId", noticeId)
                    .build();

            return ResponseEntity.ok(response.toString());
        } catch (Exception e) {
            e.printStackTrace();

            JsonObject error = Json.createObjectBuilder()
                    .add("error", "Failed to process the request")
                    .add("message", e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.toString());
        }
    }

        public static Notice toNotice(String json) {
        Notice notice = new Notice();

        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject jsonObj = reader.readObject();

            notice.setTitle(jsonObj.getString("title"));

            notice.setPoster(jsonObj.getString("poster"));

            long postDateMillis = jsonObj.getJsonNumber("postDate").longValue();
            Date postDate = new Date(postDateMillis);
            notice.setPostDate(postDate);

            JsonArray categoriesJsonArray = jsonObj.getJsonArray("categories");
            List<String> categories = new ArrayList<>();
            for (int i = 0; i < categoriesJsonArray.size(); i++) {
                categories.add(categoriesJsonArray.getString(i));
            }
            notice.setCategory(categories);

            notice.setText(jsonObj.getString("text"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid JSON payload: " + e.getMessage());
        }

        return notice;
    }



}
