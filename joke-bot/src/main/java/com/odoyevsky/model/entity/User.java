package com.odoyevsky.model.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@ToString
@Getter
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO      )
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "chat_id", unique = true)
    private Long chatId;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "favourite_jokes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "joke_id")
    )
    private Set<Joke> favouriteJokes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != getClass()) return false;

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
