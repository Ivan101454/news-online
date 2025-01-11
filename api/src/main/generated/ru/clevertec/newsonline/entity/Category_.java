package ru.clevertec.newsonline.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import ru.clevertec.newsonline.enums.Section;

@StaticMetamodel(Category.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Category_ {

	public static final String NEWS_LIST = "newsList";
	public static final String SECTION = "section";
	public static final String CATEGORY_ID = "categoryId";

	
	/**
	 * @see ru.clevertec.newsonline.entity.Category#newsList
	 **/
	public static volatile ListAttribute<Category, News> newsList;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Category#section
	 **/
	public static volatile SingularAttribute<Category, Section> section;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Category
	 **/
	public static volatile EntityType<Category> class_;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Category#categoryId
	 **/
	public static volatile SingularAttribute<Category, UUID> categoryId;

}

