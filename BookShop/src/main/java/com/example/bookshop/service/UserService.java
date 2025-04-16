    package com.example.bookshop.service;


    import com.example.bookshop.model.User;
    import com.example.bookshop.repository.UserRepository;
    import com.example.bookshop.util.CustomUserDetails;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class UserService implements UserDetailsService    {


        private UserRepository userRepository;

        @Autowired
        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public User create(User user) {
            return userRepository.save(user);
        }

        public List<User> getAll() {
            return userRepository.findAll();
        }

        public User getByEmail(String email) {
            return userRepository.findByEmail(email).orElse(null);
        }

        public User getById(Integer id) {
            return userRepository.findById(id).orElse(null);
        }

        public User update(Integer id, User userDetails) {
            User user = userRepository.findById(id).orElse(null);
            if(user != null) {
                user.setName(userDetails.getName());
                return userRepository.save(user);
            }
            return null;
        }

        public void delete(Integer id) {
            userRepository.deleteById(id);
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Поиск пользователя в базе по email или username (пример для email)
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));

            // Возвращаем объект CustomUserDetails, реализующий UserDetails
            return new CustomUserDetails(user);
        }

    }
