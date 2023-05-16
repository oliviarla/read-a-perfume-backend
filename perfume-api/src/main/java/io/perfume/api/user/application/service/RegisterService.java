package io.perfume.api.user.application.service;

import io.perfume.api.user.application.dto.UserResult;
import io.perfume.api.user.application.exception.FailedRegisterException;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import io.perfume.api.user.infrastructure.api.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterService {
    private final UserRepository userRepository;

    @Transactional
    public UserResult signUpGeneralUserByEmail(RegisterDto registerDto) {
        User user = User.generalUserJoin(
                registerDto.username(),
                registerDto.email(),
                registerDto.password(), // TODO : Password encoding 추가하기
                registerDto.name(),
                registerDto.marketingConsent(),
                registerDto.promotionConsent());

        return toDto(userRepository.save(user).orElseThrow(FailedRegisterException::new));
    }

    public boolean validDuplicateUsername(String username) {
        try {
            return userRepository.findByUsername(username).isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    private UserResult toDto(User user) {
        return new UserResult(user.getUsername(), user.getEmail(), user.getName(), user.getCreatedAt());
    }
}
