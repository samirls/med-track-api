package com.api.medtrack.configs.security;

import com.api.medtrack.models.UserModel;
import com.api.medtrack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImplement implements UserDetailsService {

    final UserRepository userRepository;

    public UserDetailsServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com: " + username));
        return new User(userModel.getUsername(), userModel.getPassword(),
                true,
                true,
                true,
                true,
                userModel.getAuthorities());
    }

}


//Os dados do formulário que o usuario escreve passam através do DTO, vem para o controller, o controller chama
//o service e o service chama o Repository(que tem acesso ao banco de dados);