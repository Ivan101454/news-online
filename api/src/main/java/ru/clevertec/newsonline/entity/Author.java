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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ToString(exclude = {"writeNews"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "author", schema = "news_online")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID author_id;
    @Column(name = "name_author")
    private String nameAuthor;
    @Column(name = "last_name_author")
    private String lastName;
    @Column(name = "date_of_registration", nullable = false, updatable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDate dateOfRegistration;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @Column(name = "write_news")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<News> writeNews;

    public void addNews(News news) {
        news.setAuthor(this);
        writeNews.add(news);
    }

}
