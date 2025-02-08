package ru.clevertec.newsonline.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.clevertec.newsonline.enums.Section;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ToString(exclude = {"newsList"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "category", schema = "news_online")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private UUID categoryId;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "section")
    private Section section;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<News> newsList;
}
