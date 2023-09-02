package com.lrb.smediafr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please fill the title")
    @Length(max=255, message = "Title is too long (more than 255)")
    private String title;
    @NotBlank(message = "Please fill the text")
    @Length(max = 2048, message = "Text is too long (more than 2kB)")
    private String content;
    private String filename;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Publication(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }


}
