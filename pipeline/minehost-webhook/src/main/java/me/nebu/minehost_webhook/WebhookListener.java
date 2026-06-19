package me.nebu.minehost_webhook;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookListener {

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(
            @RequestHeader("X-GitHub-Event") String event,
            @RequestHeader("X-Hub-Signature-256") String signature,
            @RequestBody byte[] payload
    ) {
        if (!Signature.isValid(signature.replace("sha256=", ""), payload)) {
            return ResponseEntity.status(403).build();
        }

        System.out.println("Event: " + event);

        return ResponseEntity.ok().build();
    }

}
