package org.testinfra.apiserver;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DemoApiController {

    @PostMapping("/string-mirror")
    public String stringMirror(@Valid @RequestBody TextRequest request) {
        return "Your text: %s".formatted(request.getText());
    }

    @PostMapping("/string-count")
    public Integer stringCount(@Valid @RequestBody TextRequest request) {
        return request.getText().length();
    }

    @PostMapping("/string-contains")
    public Boolean stringContains(@Valid @RequestBody TextRequest request) {
        if (null == request.getSubtext()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Subtext field must not be empty for this endpoint"
            );
        }
        return request.getText().contains(request.getSubtext());
    }
}
