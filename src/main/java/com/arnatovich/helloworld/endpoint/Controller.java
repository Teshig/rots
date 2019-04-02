package com.arnatovich.helloworld.endpoint;

import com.arnatovich.helloworld.services.EventStoreService;
import com.arnatovich.helloworld.services.ViewModel;
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
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/rots")
public class Controller {

    private final EventStoreService service = new EventStoreService();

    @GetMapping("/status")
    public String sendStatus() {
        final ViewModel viewModel = service.getViewModel();
        return "<html><body><h1>Current status: -> " + viewModel.isOccupied() + "</h1><br><h3>" + viewModel.getFromTime() + "</h3></body></html>";
    }

    @GetMapping("/occupy")
    public String occupyRoomGet() {
        return "Room has NOT been occupied! Use POST request.";
    }

    @PostMapping("/occupy")
    public String occupyRoom(@RequestBody StatusEntity entity) {
        service.addStatus(entity);
        return "Room is occupied successfully";
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
        ViewModel model = service.getViewModel();
        if (model.isOccupied()) {
            resource = new ClassPathResource("html/busy.html");
        } else {
            resource = new ClassPathResource("html/free.html");
        }

        String result = "error";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            result = br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        final Date fromTime = model.getFromTime();
        result = result.replace("${from_time}", fromTime == null ? "" : fromTime.toString());

        return result;
    }
}
