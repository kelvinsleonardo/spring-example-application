package br.com.kelvinsantiago.example.controller;

import br.com.kelvinsantiago.example.dto.ResultListDTO;
import br.com.kelvinsantiago.example.dto.user.CreateUserDTO;
import br.com.kelvinsantiago.example.dto.user.DetailUserDTO;
import br.com.kelvinsantiago.example.entity.User;
import br.com.kelvinsantiago.example.service.ModelMapperService;
import br.com.kelvinsantiago.example.dto.user.EditUserDTO;
import br.com.kelvinsantiago.example.service.TokenAuthenticationService;
import br.com.kelvinsantiago.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapperService modelMapperService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResultListDTO getAll(@RequestParam(required = false, defaultValue = "0") int page) {
        return new ResultListDTO(userService.getAll(page), modelMapperService, DetailUserDTO.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public DetailUserDTO get(@PathVariable long id) {
        return modelMapperService.toObject(DetailUserDTO.class, userService.get(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    public DetailUserDTO create(@RequestBody @Validated CreateUserDTO createUserDTO) {
        return modelMapperService.toObject(DetailUserDTO.class,
                userService.save(modelMapperService.toObject(
                        User.class, createUserDTO), false, TokenAuthenticationService.getUserId()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public DetailUserDTO edit(@RequestBody @Validated EditUserDTO editUserDTO) {
        return modelMapperService.toObject(DetailUserDTO.class,
                userService.save(modelMapperService.toObject(
                        User.class, editUserDTO), false, TokenAuthenticationService.getUserId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit-another-user")
    public DetailUserDTO editFromAmin(@RequestBody @Validated EditUserDTO editUserDTO) {
        return modelMapperService.toObject(DetailUserDTO.class,
                userService.save(modelMapperService.toObject(
                        User.class, editUserDTO), true, TokenAuthenticationService.getUserId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/change-situation")
    public HttpStatus changeSituation(@PathVariable long id) {
        userService.changeSituation(id);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/administrator")
    public HttpStatus addRemovePaperAdm(@PathVariable long id) {
        userService.changeAdministratorPaper(id);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/generate-new-password")
    public String generateNewPassword(@PathVariable long id) {
        return userService.generateNewPassword(id);
    }
}