package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/status")
    public ResponseEntity<String> getHealthStatus() {
        try {
            Object randomKey = redisTemplate.randomKey();
            
            JsonObject response = Json.createObjectBuilder()
                    .add("status", "Application is healthy")
                    .build();

            return ResponseEntity.ok(response.toString());
        } catch (Exception e) {

            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("status", "Application is unhealthy")
                    .add("error", e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse.toString());
        }

        // GET /status - Returns the health status of the application.
        // Redis CLI command equivalent to `randomKey()`:
        // redis-cli RANDOMKEY
    }
}


