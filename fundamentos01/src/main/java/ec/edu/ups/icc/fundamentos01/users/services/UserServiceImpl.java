package ec.edu.ups.icc.fundamentos01.users.services;

import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.users.dto.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dto.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dto.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dto.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;
import ec.edu.ups.icc.fundamentos01.users.models.UserModel;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .filter(entity -> !entity.isDeleted())
                .map(UserMapper::toModelFromEntity)
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDto findOne(Long id) {
        UserEntity entity = findActiveUser(id);
        UserModel model = UserMapper.toModelFromEntity(entity);
        return UserMapper.toResponse(model);
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        ensureEmailIsAvailable(dto.getEmail(), null);

        UserModel model = UserMapper.toModelFromDTO(dto);
        UserEntity entity = UserMapper.toEntityFromModel(model);
        UserEntity savedEntity = userRepository.save(entity);
        UserModel savedModel = UserMapper.toModelFromEntity(savedEntity);
        return UserMapper.toResponse(savedModel);
    }

    @Override
    public UserResponseDto update(Long id, UpdateUserDto dto) {
        UserEntity entity = findActiveUser(id);

        ensureEmailIsAvailable(dto.getEmail(), id);

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

        UserEntity savedEntity = userRepository.save(entity);
        UserModel model = UserMapper.toModelFromEntity(savedEntity);
        return UserMapper.toResponse(model);
    }

    @Override
    public UserResponseDto partialUpdate(Long id, PartialUpdateUserDto dto) {
        UserEntity entity = findActiveUser(id);

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            ensureEmailIsAvailable(dto.getEmail(), id);
            entity.setEmail(dto.getEmail());
        }

        UserEntity savedEntity = userRepository.save(entity);
        UserModel model = UserMapper.toModelFromEntity(savedEntity);
        return UserMapper.toResponse(model);
    }

    @Override
    public void delete(Long id) {
        UserEntity entity = findActiveUser(id);

        entity.setDeleted(true);
        userRepository.save(entity);
    }

    private UserEntity findActiveUser(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (entity.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        return entity;
    }

    private void ensureEmailIsAvailable(String email, Long currentUserId) {
        userRepository.findByEmail(email)
                .filter(entity -> currentUserId == null || !entity.getId().equals(currentUserId))
                .ifPresent(entity -> {
                    throw new ConflictException("Email already registered");
                });
    }
}
