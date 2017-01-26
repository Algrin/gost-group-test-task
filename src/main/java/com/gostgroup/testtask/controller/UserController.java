package com.gostgroup.testtask.controller;

import com.gostgroup.testtask.model.Role;
import com.gostgroup.testtask.dto.MessageDTO;
import com.gostgroup.testtask.model.User;
import com.gostgroup.testtask.repository.RoleRepository;
import com.gostgroup.testtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 */
@RestController
@RequestMapping(value = "/")
public class UserController {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  @Autowired
  public UserController(RoleRepository roleRepository, UserRepository userRepository) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
  }

  /**
   * Create user
   * @param user User
   * @param result Represents binding results
   */
  @PostMapping(value = "/add")
  public ResponseEntity add(@Valid @RequestBody User user, BindingResult result) {
    validateRoles(user, result);
    if (user.getId() != null && userRepository.findOne(user.getId()) != null) {
      result.rejectValue("id", "user.exist", "This user exist");
    }
    MessageDTO messageDTO = new MessageDTO();
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(initMessageWithErrors(messageDTO, result));
    }
    userRepository.save(user);
    messageDTO.setSuccess(true);
    return ResponseEntity.ok().body(messageDTO);
  }

  /**
   * Edit user
   * @param user User
   * @param result Represents binding results
   */
  @PutMapping(value = "/edit")
  public ResponseEntity edit(@Valid  @RequestBody User user, BindingResult result) {
    validateRoles(user, result);
    if (user.getId() == null || userRepository.findOne(user.getId()) == null) {
      result.rejectValue("id", "user.exist", "This user is not exist");
    }
    MessageDTO messageDTO = new MessageDTO();
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(initMessageWithErrors(messageDTO, result));
    }
    userRepository.save(user);
    messageDTO.setSuccess(true);
    return ResponseEntity.ok().body(messageDTO);
  }

  /**
   * Get user
   * @param user User
   */
  @GetMapping(value = "/get")
  public ResponseEntity<User> get(@RequestBody User user) {
    if (user.getId() != null)  {
      return ResponseEntity.ok(userRepository.findOne(user.getId()));
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Delete user
   * @param user User
   */
  @DeleteMapping(value = "/delete")
  public ResponseEntity<User> delete(@RequestBody User user) {
    if (user.getId() != null && userRepository.findOne(user.getId()) != null)  {
      userRepository.delete(user.getId());
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Gets all users
   */
  @GetMapping(value = "/list")
  public List<User> findAll() {
    final List<User> resultList = new ArrayList<>();
    final Iterable<User> all = userRepository.findAll();
    all.forEach(resultList::add);
    return resultList;
  }

  private void validateRoles(User user, BindingResult result) {
    for (Role role : user.getRoles())  {
      if (!roleRepository.exists(role.getId()))  {
        result.rejectValue("roles", "role.not.exist", "Role with id " + role.getId() + " does not exist");
      }
    }
  }

  private MessageDTO initMessageWithErrors(MessageDTO messageDTO, BindingResult result)  {
    messageDTO.setSuccess(false);
    for (ObjectError error : result.getAllErrors())  {
      messageDTO.getErrors().add(((FieldError)error).getField() + ": " + error.getDefaultMessage());
    }
    return messageDTO;
  }
}
