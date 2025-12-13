package me.seyrek.library_management_system.book.mapper;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "authors", ignore = true)
@Mapping(target = "category", ignore = true)
public @interface ToBookEntity {
}