package com.odoyevsky.service;

import com.odoyevsky.exception.JokeNotFoundException;
import com.odoyevsky.exception.UserNotFoundException;
import com.odoyevsky.model.entity.Joke;
import com.odoyevsky.model.entity.User;
import com.odoyevsky.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class UserService {
    private UserRepository userRepository;
    private JokeService jokeService;

    public User getUserByChatId(long chatId) throws UserNotFoundException {
        return userRepository.findByChatId(chatId)
                .orElseThrow(() -> new UserNotFoundException("User with chatId: " + chatId + " was not found"));

    }

    public void addFavouriteJoke(long chatId, String jokeText) throws UserNotFoundException, JokeNotFoundException {
        User user = getUserByChatId(chatId);
        Joke joke = jokeService.getJokeByText(jokeText);

        user.getFavouriteJokes().add(joke);
        userRepository.save(user);
    }

    public void removeFavouriteJoke(long chatId, String jokeText) throws UserNotFoundException, JokeNotFoundException{
        User user = getUserByChatId(chatId);
        Joke joke = jokeService.getJokeByText(jokeText);

        user.getFavouriteJokes().remove(joke);
    }
}
