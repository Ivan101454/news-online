package ru.clevertec.newsonline.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import java.util.UUID;

@StaticMetamodel(News.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class News_ {

	public static final String NEWS_ID = "newsId";
	public static final String COMMENTS = "comments";
	public static final String DATE_OF_NEWS = "dateOfNews";
	public static final String AUTHOR = "author";
	public static final String IS_PUBLISHED = "isPublished";
	public static final String HEADER_NEWS = "headerNews";
	public static final String SHORT_DESCRIPTION = "shortDescription";
	public static final String CATEGORY = "category";
	public static final String BODY_NEWS = "bodyNews";
	public static final String PICTURES = "pictures";

	
	/**
	 * @see ru.clevertec.newsonline.entity.News#newsId
	 **/
	public static volatile SingularAttribute<News, UUID> newsId;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#comments
	 **/
	public static volatile ListAttribute<News, Comment> comments;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#dateOfNews
	 **/
	public static volatile SingularAttribute<News, LocalDateTime> dateOfNews;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#author
	 **/
	public static volatile SingularAttribute<News, Author> author;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#isPublished
	 **/
	public static volatile SingularAttribute<News, Boolean> isPublished;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#headerNews
	 **/
	public static volatile SingularAttribute<News, String> headerNews;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#shortDescription
	 **/
	public static volatile SingularAttribute<News, String> shortDescription;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#category
	 **/
	public static volatile SingularAttribute<News, Category> category;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#bodyNews
	 **/
	public static volatile SingularAttribute<News, String> bodyNews;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News
	 **/
	public static volatile EntityType<News> class_;
	
	/**
	 * @see ru.clevertec.newsonline.entity.News#pictures
	 **/
	public static volatile ListAttribute<News, Picture> pictures;

}

