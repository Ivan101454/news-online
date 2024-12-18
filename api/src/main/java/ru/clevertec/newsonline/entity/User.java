package ru.clevertec.newsonline.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.UUID;

@ToString(exclude = {"comments"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", schema = "news_online")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id;
    @Column(name = "username")
    private String username;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "authorComment", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> comments;

}
