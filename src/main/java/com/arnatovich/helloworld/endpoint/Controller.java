package com.arnatovich.helloworld.endpoint;

import com.arnatovich.helloworld.services.EventStoreService;
import com.arnatovich.helloworld.valueobject.StatusEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/rots")
public class Controller {
    private final String sharedKey = "SHARED_KEY";

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;
    private static final int AUTH_FAILURE = 102;

    private final EventStoreService service = new EventStoreService();

    @GetMapping()
    public BaseResponse showStatus() {
        return new BaseResponse(SUCCESS_STATUS, 1);
    }

    @GetMapping("/status")
    public String sendStatus() {
        return "<html><body><h1>Current status: -> " + service.getRoomStatus().getRoomStatus() + "</h1><br /><h3>" + service.getRoomStatus().getLastActivity() + "</h3></body></html>";
    }

    @GetMapping("/occupy")
    public String occupyRoomGet() {
        return "Room is occupied successfully";
    }

    @PostMapping("/occupy")
    public String occupyRoom(@RequestBody StatusEntity entity) {
        service.addEvent(entity);
        return "Room is occupied successfully";
    }

    @PostMapping("/pay")
    public BaseResponse pay(@RequestParam(value = "key") String key, @RequestBody Request request) {

        final BaseResponse response;

        if (sharedKey.equalsIgnoreCase(key)) {
            int userId = request.getUserId();
            String itemId = request.getItemId();
            double discount = request.getDiscount();
            // Process the request
            // ....
            // Return success response to the client.
            response = new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
        } else {
            response = new BaseResponse(ERROR_STATUS, AUTH_FAILURE);
        }
        return response;
    }

    @GetMapping(value = "/gif/{image}", produces = MediaType.IMAGE_GIF_VALUE)
    public ResponseEntity<byte[]> getGifImage(@PathVariable String image) throws IOException {
        ClassPathResource resource = new ClassPathResource("images/gif/" + image);
        byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_GIF).body(bytes);
    }

    @GetMapping(value = "/png/{image}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPNGImage(@PathVariable String image) throws IOException {
        ClassPathResource resource = new ClassPathResource("images/png/" + image);
        byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }

    @GetMapping("/info")
    public String getInfo() throws IOException {
        ClassPathResource resource;
        if (true) { // Busy?
            //busy
            resource = new ClassPathResource("html/busy.html");
        } else {
            //not busy
            resource = new ClassPathResource("html/free.html");
        }

        String result = "error";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            result = br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        result = result.replace("${from_time}", "15:45");

        return result;
    }
}
