    package com.example.bookshop.service;


    import com.example.bookshop.model.User;
    import com.example.bookshop.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class UserService {

        UserRepository userRepository;

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

        public User getById(Long id) {
            return userRepository.findById(id).orElse(null);
        }

        public User update(Long id, User userDetails) {
            User user = userRepository.findById(id).orElse(null);
            if(user != null) {
                user.setName(userDetails.getName());
                return userRepository.save(user);
            }
            return null;
        }

        public void delete(Long id) {
            userRepository.deleteById(id);
        }

    }
