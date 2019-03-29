package com.arnatovich.helloworld.endpoint;

import com.arnatovich.helloworld.services.EventStoreService;
import com.arnatovich.helloworld.valueobject.StatusEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
}
