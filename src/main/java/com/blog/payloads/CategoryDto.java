package com.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;

	@NotEmpty
	@Size(min = 5, max = 16, message = "Title must be min of 5 chars and 16 chars.")
	private String categoryTitle;

	@NotEmpty
	@Size(min = 10, max = 200, message = "Description must be min of 10 chars and 200 chars.")
	private String categoryDescription;
}
