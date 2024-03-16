package com.app.global.config.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    // 현재 요청에 대한 URI 정보
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        String modifiedBy = httpServletRequest.getRequestURI();
        if(!StringUtils.hasText(modifiedBy)) { // URI 정보가 빈 값일 경우
            modifiedBy = "unknown";
        }
        return Optional.of(modifiedBy);
    }
}
