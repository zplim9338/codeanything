package com.anything.codeanything.contoller;

import com.anything.codeanything.model.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAttributesControllerAdvice {
//    @ModelAttribute("userId")
//    public String getUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return (authentication != null) ? (String) authentication.getPrincipal() : null;
//    }
//
//    @ModelAttribute("ipAddress")
//    public String getIpAddress(HttpServletRequest request) {
//        return (request != null) ? request.getRemoteAddr() : null;
//    }
//
//    @ModelAttribute("ipAddressV2")
//    public String getIpAddressV2(HttpServletRequest request) {
//        String ipAddress = request.getHeader("X-Forwarded-For");
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("X-Real-IP");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getRemoteAddr();
//        }
//        return ipAddress;
//    }

    @ModelAttribute("userContext")
    public UserContext addUserContextToModel(HttpServletRequest request) {
        return (UserContext) request.getAttribute("userContext");
    }
}
