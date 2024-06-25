package it.luminari.UniMuiscBackend.security;


import it.luminari.UniMuiscBackend.user.Request;
import it.luminari.UniMuiscBackend.user.Response;
import it.luminari.UniMuiscBackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody Request request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Request request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
