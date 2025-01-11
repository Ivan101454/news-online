package ru.clevertec.newsonline.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import java.util.UUID;

@StaticMetamodel(Comment.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Comment_ {

	public static final String NEWS = "news";
	public static final String DATE_OF_COMMENT = "dateOfComment";
	public static final String TEXT_COMMENT = "textComment";
	public static final String COMMENT_ID = "commentId";
	public static final String AUTHOR_COMMENT = "authorComment";

	
	/**
	 * @see ru.clevertec.newsonline.entity.Comment#news
	 **/
	public static volatile SingularAttribute<Comment, News> news;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Comment#dateOfComment
	 **/
	public static volatile SingularAttribute<Comment, LocalDateTime> dateOfComment;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Comment#textComment
	 **/
	public static volatile SingularAttribute<Comment, String> textComment;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Comment#commentId
	 **/
	public static volatile SingularAttribute<Comment, UUID> commentId;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Comment#authorComment
	 **/
	public static volatile SingularAttribute<Comment, User> authorComment;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Comment
	 **/
	public static volatile EntityType<Comment> class_;

}

