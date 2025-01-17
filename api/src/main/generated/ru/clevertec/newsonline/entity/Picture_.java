package ru.clevertec.newsonline.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

@StaticMetamodel(Picture.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Picture_ {

	public static final String NEWS = "news";
	public static final String PICTURE_ID = "pictureId";
	public static final String LINK_ON_PICTURE = "linkOnPicture";
	public static final String NAME_OF_PICTURE = "nameOfPicture";

	
	/**
	 * @see ru.clevertec.newsonline.entity.Picture#news
	 **/
	public static volatile ListAttribute<Picture, News> news;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Picture#pictureId
	 **/
	public static volatile SingularAttribute<Picture, UUID> pictureId;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Picture#linkOnPicture
	 **/
	public static volatile SingularAttribute<Picture, String> linkOnPicture;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Picture#nameOfPicture
	 **/
	public static volatile SingularAttribute<Picture, String> nameOfPicture;
	
	/**
	 * @see ru.clevertec.newsonline.entity.Picture
	 **/
	public static volatile EntityType<Picture> class_;

}

