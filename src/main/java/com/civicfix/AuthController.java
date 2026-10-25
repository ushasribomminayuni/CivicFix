
package com.civicfix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Only the administrator has a login page
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin-login";
    }
}
