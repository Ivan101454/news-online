package ru.clevertec.newsonline.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import ru.clevertec.newsonline.enums.Role;

@StaticMetamodel(User.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class User_ {

	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String COMMENTS = "comments";
	public static final String USER_ID = "user_id";
	public static final String LOGIN = "login";
	public static final String USERNAME = "username";

	
	/**
	 * @see ru.clevertec.newsonline.entity.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see ru.clevertec.newsonline.entity.User#role
	 **/
	public static volatile SingularAttribute<User, Role> role;
	
	/**
	 * @see ru.clevertec.newsonline.entity.User#comments
	 **/
	public static volatile ListAttribute<User, Comment> comments;
	
	/**
	 * @see ru.clevertec.newsonline.entity.User#user_id
	 **/
	public static volatile SingularAttribute<User, UUID> user_id;
	
	/**
	 * @see ru.clevertec.newsonline.entity.User#login
	 **/
	public static volatile SingularAttribute<User, String> login;
	
	/**
	 * @see ru.clevertec.newsonline.entity.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see ru.clevertec.newsonline.entity.User#username
	 **/
	public static volatile SingularAttribute<User, String> username;

}

