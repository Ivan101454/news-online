package ru.clevertec.newsonline.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import java.util.UUID;

@StaticMetamodel(Author.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Author_ {

	public static final String LAST_NAME = "lastName";
	public static final String NAME_AUTHOR = "nameAuthor";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String WRITE_NEWS = "writeNews";
	public static final String DATE_OF_REGISTRATION = "dateOfRegistration";
	public static final String AUTHOR_ID = "authorId";
	public static final String EMAIL = "email";

	
	/**
	 * @see ru.clevertec.newsonline.entity.Author#lastName
	 **/
	public static volatile SingularAttribute<Author, String> lastName;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Author#nameAuthor
	 **/
	public static volatile SingularAttribute<Author, String> nameAuthor;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Author#phoneNumber
	 **/
	public static volatile SingularAttribute<Author, String> phoneNumber;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Author#writeNews
	 **/
	public static volatile ListAttribute<Author, News> writeNews;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Author#dateOfRegistration
	 **/
	public static volatile SingularAttribute<Author, LocalDate> dateOfRegistration;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Author#authorId
	 **/
	public static volatile SingularAttribute<Author, UUID> authorId;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Author
	 **/
	public static volatile EntityType<Author> class_;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Author#email
	 **/
	public static volatile SingularAttribute<Author, String> email;

}

