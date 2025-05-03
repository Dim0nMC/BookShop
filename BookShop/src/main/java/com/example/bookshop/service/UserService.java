    package com.example.bookshop.service;


    import com.example.bookshop.model.User;
    import com.example.bookshop.repository.UserRepository;
    import com.example.bookshop.util.CustomUserDetails;
    import com.example.bookshop.util.exception.UserDeleteViolationException;
    import com.example.bookshop.util.exception.UserUpdateViolationException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.util.Assert;

    import java.util.List;

    import static com.example.bookshop.util.ValidationUtil.checkNotFoundWithId;


    @Service
    public class UserService implements UserDetailsService    {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        private final String CANT_DELETE_USER_MSG = "User cannot be deleted";
        private final String CANT_UPDATE_USER_MSG = "User cannot be updated";
        private final int MAX_PREDEFINED_USER_ID = 2;

        @Autowired
        public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        public User create(User user) {
            Assert.notNull(user, "User must not be null");
            return userRepository.save(user);
        }

        public List<User> getAll() {
            return userRepository.findAll();
        }

        public User getByEmail(String email) {
            return userRepository.findByEmail(email).orElse(null);
        }

        public User getById(Integer id) {
            return checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
        }

        public User update(Integer id, User userDetails) {
            Assert.notNull(userDetails, "User must not be null");
            Assert.notNull(userDetails.getId(), "User id must not be null");
            User user = getById(id);
            checkUpdateRestrictions(id);
            return userRepository.save(user);

//            User user = userRepository.findById(id).orElse(null);
//            if(user != null) {
//                //user.setName(userDetails.getName());
//                return userRepository.save(user);
//            }
//            return null;
        }

        public boolean changePassword(String username, String oldPassword, String newPassword) {
            User user = userRepository.findByEmail(username).orElse(null);
            if (user == null) return false;

            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return false;
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }

//        public void delete(Integer id) {
//            userRepository.deleteById(id);
//        }

        public void delete(int id) {
            checkDeleteRestrictions(id);
            checkNotFoundWithId(userRepository.delete(id) != 0, id);
        }

        public void addToCart(User user) {
            userRepository.save(user);

        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Поиск пользователя в базе по email или username (пример для email)
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));

            // Возвращаем объект CustomUserDetails, реализующий UserDetails
            return new CustomUserDetails(user);
        }


        private void checkDeleteRestrictions(int userId) {
            if (userId == MAX_PREDEFINED_USER_ID) {
                throw new UserDeleteViolationException(CANT_DELETE_USER_MSG);
            }
        }

        private void checkUpdateRestrictions(int userId) {
            if (userId == MAX_PREDEFINED_USER_ID) {
                throw new UserUpdateViolationException(CANT_UPDATE_USER_MSG);
            }
        }

    }
