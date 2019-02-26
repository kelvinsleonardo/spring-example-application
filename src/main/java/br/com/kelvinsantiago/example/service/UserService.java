package br.com.kelvinsantiago.example.service;

import br.com.kelvinsantiago.example.dao.repository.UserRepository;
import br.com.kelvinsantiago.example.entity.User;
import br.com.kelvinsantiago.example.enums.Situation;
import br.com.kelvinsantiago.example.exception.ServiceValidationException;
import br.com.kelvinsantiago.example.utilities.GenerateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User get(Long id) {
        return getUserById(id);
    }

    public Page<User> getAll(int page) {
        return userRepository.findAll(PageRequest.of(page, 10, Sort.by("name").ascending()));
    }

    public boolean validMail(String mail,long idUser){
        return !userRepository.existsUserByMailAndIdNotLike(mail, idUser);
    }

    public boolean validCpf(String cpf,long idUser){
        return !userRepository.existsUserByMailAndIdNotLike(cpf, idUser);
    }

    public void validate(User user, boolean anotherUser, long idUserAuthenticated){
        //if is edition check if user can be execute the operation
        //only request coming from edit another user can be change user with id different of authenticated user
        if(user.getId() > 0 && user.getId() != idUserAuthenticated && !anotherUser){
            throw new ServiceValidationException("Firstname is invalid.", "Firstname is null or empty.");
        }

        //only user by mail
        if(!validMail(user.getMail(),user.getId())){
            throw new ServiceValidationException("Firstname is invalid.", "Firstname is null or empty.");
        }

        //only user by cpf
        if(!validCpf(user.getCpf(),user.getId())){
            throw new ServiceValidationException("Firstname is invalid.", "Firstname is null or empty.");
        }
    }

    public User save(User user, boolean anotherUser, long idUserAuthenticated) {
        validate(user,anotherUser,idUserAuthenticated);

        if(user.getId() > 0){
            // in edition i get the user by id, if user dont exist, an exception will be thrown
            User userBD = getUserById(user.getId());
            if(user.getPassword() == null){
                user.setPassword(userBD.getPassword());
            }else{
                user.setPassword(GenerateUtils.encodeBCrypt(user.getPassword()));
            }
        }else{
            user.setPassword(GenerateUtils.encodeBCrypt(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User getUserById(long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }else{
            // User with this id not exists
            throw new ServiceValidationException("Firstname is invalid.", "Firstname is null or empty.");
        }
    }

    public void changeSituation(Long id) {
        User user = getUserById(id);
        if(user.getSituation() == Situation.ACTIVE){
            userRepository.changeSituationFromUser(id,Situation.INACTIVE);
        }else{
            userRepository.changeSituationFromUser(id,Situation.ACTIVE);
        }
    }

    public void changeAdministratorPaper(long idUser){
        userRepository.changeRoleAdmFromUser(idUser);
    }
    public String generateNewPassword(long idUser){
        String newPassword = GenerateUtils.generateToken();
        userRepository.changePasswordFromUser(idUser, GenerateUtils.encodeBCrypt(newPassword));
        return newPassword;
    }


    public User getByMail(String username) {
        return userRepository.findByMail(username);
    }
}